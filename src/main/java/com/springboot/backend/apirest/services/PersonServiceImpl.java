package com.springboot.backend.apirest.services;

import com.springboot.backend.apirest.dto.RespuestaDto;
import com.springboot.backend.apirest.entities.ChildrenByParents;
import com.springboot.backend.apirest.entities.Person;
import com.springboot.backend.apirest.repositories.ChildrenByParentDao;
import com.springboot.backend.apirest.repositories.PersonDao;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class PersonServiceImpl implements PersonService{

    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    PersonDao personDao;

    @Autowired
    ChildrenByParentDao childrenByParentDao;

    public ResponseEntity<Object> listAllPerson(){
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        try {
            ArrayList<Person> listPeople=(ArrayList<Person>) personDao.findAll();

            RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.OK,"Exito listando las personas");
            respuestaDto.setObjetoRespuesta(listPeople);
            return response=new ResponseEntity(respuestaDto,HttpStatus.OK);

        }catch (DataAccessException e){
            RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.INTERNAL_SERVER_ERROR,"Error listando las personas");
            return response=new ResponseEntity(respuestaDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Object> createPerson(Person newPerson) {
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        try {
            if (!personDao.existsByFullName(newPerson.getFullName())){
                Person person=personDao.save(newPerson);

                RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.OK,"Exito creando la persona");
                respuestaDto.setObjetoRespuesta(person);
                return response=new ResponseEntity(respuestaDto,HttpStatus.OK);
            }
            RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.BAD_REQUEST,"Error. El nombre de la persona ya existe");
            return response=new ResponseEntity(respuestaDto,HttpStatus.BAD_REQUEST);

        }catch (DataAccessException e){
            RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.INTERNAL_SERVER_ERROR,"Error creando las personas");
            return response=new ResponseEntity(respuestaDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updatePerson(Person newPerson) {
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        try {
            Person previewPerson=personDao.findById(newPerson.getId()).get();
            if (newPerson.getFullName().equals(previewPerson.getFullName())){
                Person person=personDao.save(newPerson);

                RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.OK,"Exito creando la persona");
                respuestaDto.setObjetoRespuesta(person);
                return response=new ResponseEntity(respuestaDto,HttpStatus.OK);
            }else {
                if (!personDao.existsByFullName(newPerson.getFullName())){
                    Person person=personDao.save(newPerson);

                    RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.OK,"Exito actualizando la persona");
                    respuestaDto.setObjetoRespuesta(person);
                    return response=new ResponseEntity(respuestaDto,HttpStatus.OK);
                }
                RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.BAD_REQUEST,"Error. El nombre de la persona ya esta siendo usado");
                return response=new ResponseEntity(respuestaDto,HttpStatus.BAD_REQUEST);
            }
        }catch (DataAccessException e){
            RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.INTERNAL_SERVER_ERROR,"Error actualizando las personas");
            return response=new ResponseEntity(respuestaDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> deletePerson(Long id) {
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        try {
            if (personDao.obtainRegister(id)==null){
                personDao.deleteById(id);

                RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.OK,"Exito borrando la persona");
                return response=new ResponseEntity(respuestaDto,HttpStatus.OK);
            }else {
                RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.INTERNAL_SERVER_ERROR,"La persona tiene hijos vinculados");
                return response=new ResponseEntity(respuestaDto,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (Exception e){
            Throwable t = e.getCause();
            if (t instanceof ConstraintViolationException) {
                RespuestaDto respuestaDto = new RespuestaDto(HttpStatus.BAD_REQUEST, "La persona se encuentra asociada al sistema");
                return response = new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
            }
            else {
                RespuestaDto respuestaDto = new RespuestaDto(HttpStatus.INTERNAL_SERVER_ERROR, "Error en el borrado la persona");
                return response = new ResponseEntity<>(respuestaDto, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public ResponseEntity<Object> childWhithoutFather(Long person) {
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        try {
            ArrayList<Long> listPeople= personDao.withoutFather(person);
            ArrayList<Long> parents= myParents(person);
            for (Long parent:
                 parents) {
                if (listPeople.contains(parent)) listPeople.remove(listPeople.indexOf(parent));
            }

            ArrayList<Person> childs=new ArrayList<>();


            for (Long child:
            listPeople) {
                childs.add(personDao.findById(child).get());
            }

            RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.OK,"Exito listando los hijos sin padre");
            respuestaDto.setObjetoRespuesta(childs);
            return response=new ResponseEntity(respuestaDto,HttpStatus.OK);

        }catch (DataAccessException e){
            RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.INTERNAL_SERVER_ERROR,"Error listando los hijos que no tienen padre");
            return response=new ResponseEntity(respuestaDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> childWhithoutMother(Long person) {
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        try {
            ArrayList<Long> listPeople= personDao.withoutMother(person);
            ArrayList<Long> parents= myParents(person);
            for (Long parent:
                    parents) {
                if (listPeople.contains(parent)) listPeople.remove(listPeople.indexOf(parent));
            }

            ArrayList<Person> childs=new ArrayList<>();

            for (Long child:
                    listPeople) {
                childs.add(personDao.findById(child).get());
            }

            RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.OK,"Exito listando los hijos sin madres");
            respuestaDto.setObjetoRespuesta(childs);
            return response=new ResponseEntity(respuestaDto,HttpStatus.OK);

        }catch (DataAccessException e){
            RespuestaDto respuestaDto  = new RespuestaDto(HttpStatus.INTERNAL_SERVER_ERROR,"Error listando los hijos sin madres");
            return response=new ResponseEntity(respuestaDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> listByPerson(Long persona){
        ResponseEntity<Object> response=new ResponseEntity<>(HttpStatus.OK);
        try {
            Person person=personDao.findById(persona).get();

            ArrayList<Long> childByPerson=new ArrayList<>();
            ArrayList<Person> people=new ArrayList<>();

            if (person.getGender().equals("femenino")) childByPerson=personDao.childByMother(person.getId());
            else childByPerson=personDao.childByFather(person.getId());;

            for (Long id:
                 childByPerson) {
                people.add(personDao.findById(id).get());
            }

            RespuestaDto respuestaDto=new RespuestaDto(HttpStatus.OK,"Exito listando hijos");
            respuestaDto.setObjetoRespuesta(people);
            return response=new ResponseEntity<>(respuestaDto,HttpStatus.OK);
        }catch (DataAccessException e){
            RespuestaDto respuestaDto=new RespuestaDto(HttpStatus.INTERNAL_SERVER_ERROR,"Error listando hijos");
            return response=new ResponseEntity<>(respuestaDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<Object> linkChild(Long idParent, Long idChild,String gender){
        ResponseEntity<Object> response=new ResponseEntity<>(HttpStatus.OK);
        ChildrenByParents saveLink=new ChildrenByParents();
        try {
            if (personDao.obtainRegister(idChild) != null){
                saveLink= childrenByParentDao.findById(personDao.obtainRegister(idChild)).get() ;
            }
            saveLink.setChild(personDao.findById(idChild).get());

            if (gender.equals("femenino")) saveLink.setMother(idParent);
            else saveLink.setFather(idParent);

            childrenByParentDao.save(saveLink);

            RespuestaDto respuestaDto=new RespuestaDto(HttpStatus.OK,"Exito guardando hijo");
            return response=new ResponseEntity<>(respuestaDto,HttpStatus.OK);
        }catch (DataAccessException e){
            RespuestaDto respuestaDto=new RespuestaDto(HttpStatus.INTERNAL_SERVER_ERROR,"Error guardando hijo");
            return response=new ResponseEntity<>(respuestaDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> deleteLinked(Long childId , Long parentId){
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        Long anyParent= null;
        try {

            Person child=personDao.findById(childId).get();
            ChildrenByParents listLink= childrenByParentDao.findByChild(child);
            if (parentId!=listLink.getFather() && listLink.getFather()!=null) anyParent=listLink.getFather();
            else if (parentId!=listLink.getMother() && listLink.getMother()!=null) anyParent=listLink.getMother();

            if (anyParent==null){
                childrenByParentDao.delete(listLink);
            }else {
                childrenByParentDao.delete(listLink);
                child=personDao.findById(anyParent).get();
                if (child.getGender().equals("femenino")) listLink.setMother(child.getId());
                else listLink.setFather(child.getId());

                childrenByParentDao.save(listLink);
            }
            RespuestaDto respuestaDto=new RespuestaDto(HttpStatus.OK,"Exito eliminando el vinculo");
            return response=new ResponseEntity<>(respuestaDto,HttpStatus.OK);
        }catch (Exception e){
            RespuestaDto respuestaDto=new RespuestaDto(HttpStatus.INTERNAL_SERVER_ERROR,"Error eliminando el vinculo");
            return response=new ResponseEntity<>(respuestaDto,HttpStatus.OK);
        }

    }



    //Service to delete Parents of the linklist
    public ArrayList<Long> myParents(Long child){
        ArrayList<Long> myParent=new ArrayList<>();
        try {
            Long myParents=personDao.obtainRegister(child);
            ChildrenByParents childreParents =childrenByParentDao.findById(myParents).get();
            myParent.add(childreParents.getFather());
            myParent.add(childreParents.getMother());
        }catch (Exception e){
            log.warn("No existen padres");
        }
        return myParent;
    }








}

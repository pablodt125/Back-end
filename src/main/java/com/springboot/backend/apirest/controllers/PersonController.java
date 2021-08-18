package com.springboot.backend.apirest.controllers;

import com.springboot.backend.apirest.constant.ApiConstant;
import com.springboot.backend.apirest.entities.Person;
import com.springboot.backend.apirest.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(ApiConstant.PERSON_CONTROLLER_API)
public class PersonController {
    @Autowired
    private PersonService personService;

    //Sirve para listar a todas las personas
    @GetMapping(value = ApiConstant.PERSON_CONTROLLER_API_LIST_EVERYONE)
    public ResponseEntity<Object> list(){
        return this.personService.listAllPerson();
    }

    //Lista a los hijos que no tienen padre
    @GetMapping(value = ApiConstant.PERSON_CONTROLLER_API_LIST_FATHER)
    public ResponseEntity<Object> listFather(@RequestParam Long person){
        return this.personService.childWhithoutFather(person);
    }

    //Lista a los hijos que no tienen madre
    @GetMapping(value = ApiConstant.PERSON_CONTROLLER_API_LIST_MOTHER)
    public ResponseEntity<Object> listMother(@RequestParam Long person){
        return this.personService.childWhithoutMother(person);
    }

    //Lista a los hijos que no tienen madre
    @GetMapping(value = ApiConstant.PERSON_CONTROLLER_API_LIST_CHILD_PARENT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listByPerson(@RequestParam Long person){
        return this.personService.listByPerson(person);
    }

    //Crea una nueva persona
    @RequestMapping(value = ApiConstant.PERSON_CONTROLLER_API_CREATE, method = { RequestMethod.GET, RequestMethod.POST },produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> create(@RequestBody Person newPerson) {
        return this.personService.createPerson(newPerson);
    }

    //Vincula a un padre con un hijo
    @RequestMapping(value = ApiConstant.PERSON_CONTROLLER_API_CREATE_LINK, method = { RequestMethod.GET, RequestMethod.POST },produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> createLink(@RequestParam Long idParent,@RequestParam Long idChild,@RequestParam String gender) {
        return this.personService.linkChild(idParent,idChild,gender);
    }

    //Actualizar persona
    @RequestMapping(value = ApiConstant.PERSON_CONTROLLER_API_UPDATE, method = { RequestMethod.GET, RequestMethod.PUT },produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> update(@RequestBody Person newPerson, HttpServletRequest request, HttpServletResponse response) {
        return this.personService.updatePerson(newPerson);
    }

    // Borrar personta mediante un id
    @RequestMapping(value = ApiConstant.PERSON_CONTROLLER_API_DELETE+"/{id}", method = { RequestMethod.GET, RequestMethod.DELETE },produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> delete(@PathVariable Long id) {
        return this.personService.deletePerson(id);
    }

    // Borrar personta mediante un id
    @RequestMapping(value = ApiConstant.PERSON_CONTROLLER_API_DELETE_LINKED+"/{childId}"+"/{parentId}", method = { RequestMethod.GET, RequestMethod.DELETE },produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> deleteLink(@PathVariable Long childId ,@PathVariable Long parentId ) {
        return this.personService.deleteLinked(childId,parentId);
    }

}

package com.springboot.backend.apirest.repositories;

import com.springboot.backend.apirest.entities.ChildrenByParents;
import com.springboot.backend.apirest.entities.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Transactional
public interface PersonDao extends CrudRepository<Person,Long> {

    public boolean existsByFullName(String fullName);

    @Query(value = "select id \n" +
            "from person p \n" +
            "where :idPerson != p.id and  id not in (select c.child \n" +
                                                    "from children_by_parents c\n" +
                                                    "where c.father is not null) ",nativeQuery = true)
    public ArrayList<Long> withoutFather(@Param("idPerson") Long idPerson);

    @Query(value = "select id \n" +
            "from person p \n" +
            "where :idMother != p.id and  id not in (select c.child \n" +
                                                    "from children_by_parents c\n" +
                                                    "where c.father is not null)",nativeQuery = true)
    public ArrayList<Long> withoutMother(@Param("idMother") Long idMother);


    @Query(value = "select c.child \n" +
            "from children_by_parents c\n" +
            "where c.father = :idMother",nativeQuery = true)
    public ArrayList<Long> childByMother(@Param("idMother") Long idMother);


    @Query(value = "select c.child \n" +
            "from children_by_parents c\n" +
            "where c.father = :idFather",nativeQuery = true)
    public ArrayList<Long> childByFather(@Param("idFather") Long idFather);

    @Query(value = "select c.id from children_by_parents c where c.child= :idChild",nativeQuery = true)
    public Long obtainRegister(@Param("idChild") Long idChild);

}


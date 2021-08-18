package com.springboot.backend.apirest.services;

import com.springboot.backend.apirest.entities.Person;
import org.springframework.http.ResponseEntity;


public interface PersonService {

    public ResponseEntity<Object> listAllPerson();

    public ResponseEntity<Object> createPerson(Person newPerson);

    public ResponseEntity<Object> updatePerson(Person newPerson);

    public ResponseEntity<Object> deletePerson(Long id);

    public ResponseEntity<Object> childWhithoutFather(Long person);

    public ResponseEntity<Object> childWhithoutMother(Long person);

    public ResponseEntity<Object> linkChild(Long idParent, Long idChild,String role);

    public ResponseEntity<Object> listByPerson(Long person);

    public ResponseEntity<Object> deleteLinked(Long childId , Long parentId);

}

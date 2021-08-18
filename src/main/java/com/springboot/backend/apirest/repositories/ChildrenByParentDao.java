package com.springboot.backend.apirest.repositories;

import com.springboot.backend.apirest.entities.ChildrenByParents;
import com.springboot.backend.apirest.entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface ChildrenByParentDao extends CrudRepository<ChildrenByParents,Long> {

    public ChildrenByParents findByChild(Person child);

}

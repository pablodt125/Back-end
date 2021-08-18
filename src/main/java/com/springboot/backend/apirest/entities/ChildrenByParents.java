package com.springboot.backend.apirest.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "children_by_parents")
public class ChildrenByParents implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "mother")
    private Long mother;

    @Column(name = "father")
    private Long father;

    @ManyToOne
    @JoinColumn(name = "child")
    private Person child;

    public Long getId() {
        return id;
    }
    public Long getMother() {
        return mother;
    }

    public void setMother(Long mother) {
        this.mother = mother;
    }

    public Long getFather() {
        return father;
    }

    public void setFather(Long father) {
        this.father = father;
    }

    public Person getChild() {
        return child;
    }

    public void setChild(Person child) {
        this.child = child;
    }
}

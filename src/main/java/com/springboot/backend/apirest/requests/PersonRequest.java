package com.springboot.backend.apirest.requests;

import com.springboot.backend.apirest.entities.Person;

import java.util.ArrayList;

public class PersonRequest {

    private Person mother;

    private Person father;

    private ArrayList<Person> hijos;

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public ArrayList<Person> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<Person> hijos) {
        this.hijos = hijos;
    }
}

package com.example.springproxy.dao;

import com.example.springproxy.model.Person;

/**
 * @author Taner YILDIRIM
 */
public interface PersonDao {

    Person findById(int id);

    void savePerson(Person person);
}

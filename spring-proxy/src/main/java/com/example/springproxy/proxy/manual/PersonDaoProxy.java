package com.example.springproxy.proxy.manual;

import com.example.springproxy.dao.PersonDao;
import com.example.springproxy.model.Person;

/**
 * @author Taner YILDIRIM
 */
public class PersonDaoProxy implements PersonDao {

    private final PersonDao personDao;

    public PersonDaoProxy(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public Person findById(int id) {
        System.out.println("before findById");
        Person person = personDao.findById(id);
        System.out.println("after findById");
        return person;
    }

    @Override
    public void savePerson(Person person) {
        System.out.println("before savePerson");
        personDao.savePerson(person);
        System.out.println("after savePerson");
    }
}

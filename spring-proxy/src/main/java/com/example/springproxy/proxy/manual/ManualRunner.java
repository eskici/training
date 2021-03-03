package com.example.springproxy.proxy.manual;

import com.example.springproxy.dao.PersonDaoImpl;
import com.example.springproxy.model.Person;

/**
 * @author Taner YILDIRIM
 */
public class ManualRunner {

    public static void main(String[] args) {
        PersonDaoProxy proxy = new PersonDaoProxy(new PersonDaoImpl());
        Person person = proxy.findById(3);
        proxy.savePerson(person);
    }
}

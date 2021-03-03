package com.example.springproxy.proxy.cglib;

import com.example.springproxy.dao.PersonDao;
import com.example.springproxy.dao.PersonDaoImpl;
import com.example.springproxy.model.Person;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @author Taner YILDIRIM
 */
public class CglibRunner {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new PersonDaoMethodInterceptor());
        enhancer.setSuperclass(PersonDaoImpl.class);
        PersonDaoImpl proxy  = (PersonDaoImpl) enhancer.create();

        Person person = proxy.findById(5);
        proxy.savePerson(person);
    }
}

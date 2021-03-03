package com.example.springproxy.proxy.jdk;

import com.example.springproxy.dao.PersonDao;
import com.example.springproxy.dao.PersonDaoImpl;

import java.lang.reflect.Proxy;

/**
 * @author Taner YILDIRIM
 */
public class JdkRunner {

    public static void main(String[] args) {
        PersonDao proxyInstance = (PersonDao) Proxy.newProxyInstance(PersonDao.class.getClassLoader(), PersonDaoImpl.class.getInterfaces(), new PersonDaoInvocationHandler(new PersonDaoImpl()));

        proxyInstance.findById(5);
        proxyInstance.savePerson(null);
    }
}

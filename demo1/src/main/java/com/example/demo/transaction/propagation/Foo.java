package com.example.demo.transaction.propagation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Taner YILDIRIM
 */
@Entity
public class Foo {

    @Id
    @GeneratedValue
    private Long id;
}

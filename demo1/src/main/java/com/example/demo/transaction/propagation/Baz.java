package com.example.demo.transaction.propagation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Taner YILDIRIM
 */

@Entity
public class Baz {
    @Id
    @GeneratedValue
    private Long id;

}

package com.example.jpatransaction;

import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Taner YILDIRIM
 */
@Entity
@Table(name = "ID_GEN")
@NoArgsConstructor
public class IdGeneration {

    public IdGeneration(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;
}

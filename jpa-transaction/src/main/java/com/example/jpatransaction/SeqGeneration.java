package com.example.jpatransaction;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Taner YILDIRIM
 */
@Entity
@Table(name = "SEQ_GEN")
@NoArgsConstructor
public class SeqGeneration {

    public SeqGeneration(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
    private Long id;

    @Column(nullable=false)
    private String name;
}

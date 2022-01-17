package com.rentitfurnished.mutantdetector.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "DNA")
@NoArgsConstructor
public class DNA implements Serializable {

    public DNA(String dna, DNAType type) {
        this.dna = dna;
        this.type = type;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "DNA")
    private String dna;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private DNAType type;

}

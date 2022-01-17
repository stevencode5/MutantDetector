package com.rentitfurnished.mutantdetector.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "STATISTIC")
@NoArgsConstructor
public class Statistic implements Serializable {

    public Statistic(long mutantDNACount, long humanDNACount) {
        this.mutantDNACount = mutantDNACount;
        this.humanDNACount = humanDNACount;
    }

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "count_mutant_dna")
    private long mutantDNACount;

    @Column(name = "count_human_dna")
    private long humanDNACount;

}

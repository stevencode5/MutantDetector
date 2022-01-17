package com.rentitfurnished.mutantdetector.repository;

import com.rentitfurnished.mutantdetector.domain.model.DNA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DNARepository extends JpaRepository<DNA, Long>  {

}

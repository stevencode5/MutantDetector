package com.rentitfurnished.mutantdetector.repository;

import com.rentitfurnished.mutantdetector.domain.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

}

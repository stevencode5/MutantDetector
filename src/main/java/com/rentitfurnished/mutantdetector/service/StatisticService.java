package com.rentitfurnished.mutantdetector.service;

import com.rentitfurnished.mutantdetector.domain.dto.StatsResponseDto;
import com.rentitfurnished.mutantdetector.domain.model.DNAType;

public interface StatisticService {

    StatsResponseDto calculateStatistics();

    void incrementDNACount(DNAType dnaType);

}

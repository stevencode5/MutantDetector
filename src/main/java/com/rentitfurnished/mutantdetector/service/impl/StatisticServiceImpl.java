package com.rentitfurnished.mutantdetector.service.impl;

import com.rentitfurnished.mutantdetector.domain.dto.StatsResponseDto;
import com.rentitfurnished.mutantdetector.domain.model.DNAType;
import com.rentitfurnished.mutantdetector.domain.model.Statistic;
import com.rentitfurnished.mutantdetector.repository.StatisticRepository;
import com.rentitfurnished.mutantdetector.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class StatisticServiceImpl implements StatisticService {

    private static final long UNIQUE_ID = 1l;
    private final StatisticRepository statisticRepository;

    @Override
    public StatsResponseDto calculateStatistics() {
        Statistic statistic = statisticRepository.findById(UNIQUE_ID).get();
        long mutantDNACount = statistic.getMutantDNACount();
        long humanDNACount = statistic.getHumanDNACount();
        float ratio = (float) mutantDNACount / (float) humanDNACount;
        return new StatsResponseDto(mutantDNACount, humanDNACount, ratio);
    }

    @Override
    public void incrementDNACount(DNAType dnaType) {
        Statistic statistic = statisticRepository.findById(UNIQUE_ID).get();
        if (dnaType.equals(DNAType.MUTANT)) {
            statistic.setMutantDNACount(statistic.getMutantDNACount() + 1);
        } else {
            statistic.setHumanDNACount(statistic.getHumanDNACount() + 1);
        }
        statisticRepository.save(statistic);
    }

}

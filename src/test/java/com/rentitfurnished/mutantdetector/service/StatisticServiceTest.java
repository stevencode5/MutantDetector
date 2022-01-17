package com.rentitfurnished.mutantdetector.service;

import com.rentitfurnished.mutantdetector.domain.dto.StatsResponseDto;
import com.rentitfurnished.mutantdetector.domain.model.DNAType;
import com.rentitfurnished.mutantdetector.domain.model.Statistic;
import com.rentitfurnished.mutantdetector.repository.StatisticRepository;
import com.rentitfurnished.mutantdetector.service.impl.StatisticServiceImpl;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class StatisticServiceTest {

    private static final long UNIQUE_STATISTIC_ID = 1;
    private static final long MUTANT_DNA_COUNT = 40;
    private static final long HUMAN_DNA_COUNT = 100;
    private static final float RATIO = 0.4f;

    @Tested
    private StatisticServiceImpl statisticService;

    @Injectable
    private StatisticRepository statisticRepository;

    @Test
    public void shouldCalculateValidStatistics() {
        new Expectations() {
            {
                statisticRepository.findById(UNIQUE_STATISTIC_ID);
                result = Optional.of(new Statistic(MUTANT_DNA_COUNT, HUMAN_DNA_COUNT));
            }
        };
        StatsResponseDto stats = statisticService.calculateStatistics();
        StatsResponseDto expectedStats = new StatsResponseDto(MUTANT_DNA_COUNT, HUMAN_DNA_COUNT, RATIO);
        assertEquals(stats, expectedStats);
    }

    @Test
    public void shouldIncrementHumanDNACount() {
        new Expectations() {
            {
                statisticRepository.findById(UNIQUE_STATISTIC_ID);
                result = buildStatistic();
            }
        };
        statisticService.incrementDNACount(DNAType.HUMAN);
        new Verifications() {
            {
                statisticRepository.save(new Statistic(MUTANT_DNA_COUNT, HUMAN_DNA_COUNT + 1));
                times = 1;
            }
        };
    }

    @Test
    public void shouldIncrementMutantDNACount() {
        new Expectations() {
            {
                statisticRepository.findById(UNIQUE_STATISTIC_ID);
                result = buildStatistic();
            }
        };
        statisticService.incrementDNACount(DNAType.MUTANT);
        new Verifications() {
            {
                statisticRepository.save(new Statistic(MUTANT_DNA_COUNT + 1, HUMAN_DNA_COUNT));
                times = 1;
            }
        };
    }

    private Optional<Statistic> buildStatistic() {
        return Optional.of(new Statistic(MUTANT_DNA_COUNT, HUMAN_DNA_COUNT));
    }

}

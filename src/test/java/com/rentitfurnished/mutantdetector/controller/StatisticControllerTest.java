package com.rentitfurnished.mutantdetector.controller;

import com.rentitfurnished.mutantdetector.domain.dto.StatsResponseDto;
import com.rentitfurnished.mutantdetector.service.StatisticService;
import mockit.Expectations;
import mockit.Injectable;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class StatisticControllerTest {

    private static final int MUTANT_DNA_COUNT = 40;
    private static final int HUMAN_DNA_COUNT = 100;
    private static final float RATIO = 0.4f;
    private MockMvc mvc;

    @Injectable
    private StatisticService statisticService;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(new StatisticController(statisticService)).build();
    }

    @Test
    public void shouldReturnValidStatsAndOkStatus() throws Exception {
        new Expectations() {
            {
                statisticService.calculateStatistics();
                result = new StatsResponseDto(MUTANT_DNA_COUNT, HUMAN_DNA_COUNT, RATIO);
            }
        };
        mvc.perform(MockMvcRequestBuilders.get("/stats"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(MUTANT_DNA_COUNT))
                .andExpect(jsonPath("$.count_human_dna").value(HUMAN_DNA_COUNT))
                .andExpect(jsonPath("$.ratio").value(RATIO));
    }

}

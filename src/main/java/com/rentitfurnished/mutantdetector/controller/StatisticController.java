package com.rentitfurnished.mutantdetector.controller;

import com.rentitfurnished.mutantdetector.domain.dto.StatsResponseDto;
import com.rentitfurnished.mutantdetector.service.StatisticService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stats")
@AllArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping
    ResponseEntity<StatsResponseDto> getStatistics() {
        log.info("Getting statistics");
        StatsResponseDto stats = statisticService.calculateStatistics();
        return ResponseEntity.ok().body(stats);
    }

}

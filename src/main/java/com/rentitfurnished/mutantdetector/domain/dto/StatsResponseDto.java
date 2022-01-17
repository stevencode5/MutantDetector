package com.rentitfurnished.mutantdetector.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsResponseDto {

    @JsonProperty("count_mutant_dna")
    private long mutantDNACount;

    @JsonProperty("count_human_dna")
    private long humanDNACount;

    @JsonProperty("ratio")
    private float ratio;

}

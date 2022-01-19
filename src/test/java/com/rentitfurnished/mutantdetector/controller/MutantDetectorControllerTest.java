package com.rentitfurnished.mutantdetector.controller;

import com.rentitfurnished.mutantdetector.domain.dto.DNADto;
import com.rentitfurnished.mutantdetector.service.MutantService;
import com.rentitfurnished.mutantdetector.util.TestUtils;
import org.junit.Before;
import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class MutantDetectorControllerTest {

    private MockMvc mvc;

    @Injectable
    private MutantService mutantService;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(new MutantDetectorController(mutantService)).build();
    }

    @Test
    public void shouldReturn200OkStatusWhenIsMutant() throws Exception {
        new Expectations() {
            {
                mutantService.isMutant((String[]) any);
                result = true;
            }
        };
        mvc.perform(MockMvcRequestBuilders.post("/mutant")
                        .content(TestUtils.asJsonString(buildDNAPayload()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturn403ForbiddenStatusWhenIsHuman() throws Exception {
        new Expectations() {
            {
                mutantService.isMutant((String[]) any);
                result = false;
            }
        };
        mvc.perform(MockMvcRequestBuilders.post("/mutant")
                        .content(TestUtils.asJsonString(buildDNAPayload()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void shouldReturn400BadRequestStatusWhenInvalidPayloadSize() throws Exception {
        DNADto invalidDNA = new DNADto(new String[]{"ATGC",
                                                    "CAG",
                                                    "TTAT",
                                                    "AG"});
        mvc.perform(MockMvcRequestBuilders.post("/mutant")
                        .content(TestUtils.asJsonString(invalidDNA))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldReturn400BadRequestStatusWhenInvalidPayloadContent() throws Exception {
        DNADto invalidDNA = new DNADto(new String[]{"AGGC",
                                                    "CAGC",
                                                    "TXAT",
                                                    "AGTC"});
        mvc.perform(MockMvcRequestBuilders.post("/mutant")
                        .content(TestUtils.asJsonString(invalidDNA))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private DNADto buildDNAPayload() {
        DNADto dna = new DNADto();
        dna.setDna(new String[]{"AAA", "AAA", "AAA"});
        return dna;
    }

}

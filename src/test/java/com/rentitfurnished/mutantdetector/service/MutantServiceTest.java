package com.rentitfurnished.mutantdetector.service;

import com.rentitfurnished.mutantdetector.repository.DNARepository;
import com.rentitfurnished.mutantdetector.service.impl.MutantServiceImpl;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MutantServiceTest {

    @Tested
    private MutantServiceImpl mutantService;

    @Injectable
    private DNARepository dNARepository;

    @Injectable
    private StatisticService statisticService;

    @Test
    public void shouldReturnIsMutant() {
        String[] dna = {"ATGCGA",
                        "CAGTGC",
                        "TTATGT",
                        "AGAAGG",
                        "CCCCTA",
                        "TCACTG"};
        assertTrue(mutantService.isMutant(dna));
    }

    @Test
    public void shouldReturnIsHuman() {
        String[] dna = {"ATGCGA",
                        "CCGTGC",
                        "TTATTT",
                        "AGAAGG",
                        "CTCCTA",
                        "TCACTG"};
        assertFalse(mutantService.isMutant(dna));
    }

    @Test
    public void shouldReturnIsHumanWhenOnlyOneMutation() {
        String[] dna = {"ATGCGA",
                        "CCGTGC",
                        "TTATTT",
                        "AGAAGG",
                        "CCCCTA",
                        "TCACTG"};
        assertFalse(mutantService.isMutant(dna));
    }

    @Test
    public void shouldReturnIsMutantWhenTwoHorizontalSequencesDetected() {
        String[] dna = {"ATCGAT",
                        "AAAAGT",
                        "TCGATG",
                        "ATTTTC",
                        "CTGACG",
                        "TATATA"};
        assertTrue(mutantService.isMutant(dna));
    }

    @Test
    public void shouldReturnIsMutantWhenTwoVerticalSequencesDetected() {
        String[] dna = {"GTCCAT",
                        "AAAGGT",
                        "ACGGTG",
                        "ATTGTC",
                        "ATGGCG",
                        "TATATA"};
        assertTrue(mutantService.isMutant(dna));
    }

    @Test
    public void shouldReturnIsMutantWhenTwoDiagonalRightSequencesDetected() {
        String[] dna = {"GCCGAT",
                        "GTTGGT",
                        "ACTTTG",
                        "AACGTC",
                        "ATAGCT",
                        "TATATA"};
        assertTrue(mutantService.isMutant(dna));
    }

    @Test
    public void shouldReturnIsMutantWhenTwoDiagonalLeftSequencesDetected() {
        String[] dna = {"GTCGAT",
                        "AAAGGT",
                        "ACGATC",
                        "AGTGCC",
                        "GTGCCG",
                        "TACATA"};
        assertTrue(mutantService.isMutant(dna));
    }

    @Test
    public void shouldReturnIsMutantWhenMoreThanTwoSequencesDetected() {
        String[] dna = {"ATGCGA",
                        "CAGTGC",
                        "TTATGT",
                        "AGAAGG",
                        "CCCCTA",
                        "TTTTTG"};
        assertTrue(mutantService.isMutant(dna));
    }

    @Test
    public void shouldReturnIsHumanWhenDNAIsTooShort() {
        String[] dna = {"ATGC",
                        "CAGT",
                        "TTAT"};
        assertFalse(mutantService.isMutant(dna));
    }

    @Test
    public void shouldReturnIsMutantWhenLargeDNA() {
        String[] dna = {"ATGCGAT",
                        "CTGTGCG",
                        "TTATCTA",
                        "AGAAGGA",
                        "CCACTAA",
                        "TAGTGGA",
                        "GACTTTT"};
        assertTrue(mutantService.isMutant(dna));
    }

    @Test
    public void shouldReturnIsMutantWhenSequenceInCorners() {
        String[] dna = {"ATGAAAA",
                        "CTGCGAC",
                        "TTATCTG",
                        "AGATGGA",
                        "CCACTAA",
                        "TAGTGGA",
                        "GACTTTA"};
        assertTrue(mutantService.isMutant(dna));
    }

    @Test
    public void shouldReturnIsMutantWhenDiagonalSequencesInCorners() {
        String[] dna = {"ATGATAGA",
                        "CTGCGAGC",
                        "AGAGAGGA",
                        "TTATCATG",
                        "AGAGAGGA",
                        "CCGCGAAT",
                        "TGGTTGAC",
                        "GACTGTTA"};
        assertTrue(mutantService.isMutant(dna));
    }

}

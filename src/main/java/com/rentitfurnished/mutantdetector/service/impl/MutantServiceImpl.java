package com.rentitfurnished.mutantdetector.service.impl;

import com.rentitfurnished.mutantdetector.domain.model.DNA;
import com.rentitfurnished.mutantdetector.domain.model.DNAType;
import com.rentitfurnished.mutantdetector.domain.model.Direction;
import com.rentitfurnished.mutantdetector.repository.DNARepository;
import com.rentitfurnished.mutantdetector.service.MutantService;
import com.rentitfurnished.mutantdetector.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MutantServiceImpl implements MutantService {

    // Times a letter must be repeated in a sequence
    private static final int SEQUENCE_LENGTH = 4;

    // Max mutations in DNA to be considered a human
    private static final int MAX_MUTATIONS = 1;

    private final StatisticService statisticService;

    private final DNARepository dNARepository;

    @Override
    public boolean isMutant(String[] dna) {
        if (dna.length < SEQUENCE_LENGTH) { // There must be enough letters to complete a sequence
            storeDNAVerified(dna, false);
            return false;
        }
        boolean isMutant = calculateIsMutant(dna);
        storeDNAVerified(dna, isMutant);
        return isMutant;
    }

    private boolean calculateIsMutant(String[] dna) {
        char[][] dnaMatrix = buildDNAMatrix(dna);
        int mutationCount = 0;
        // Traverses all positions looking for valid sequences
        for (int i = 0; i < dnaMatrix.length; i++) {
            for (int j = 0; j < dnaMatrix.length; j++) {
                mutationCount = searchRepeatedLetters(i, j, dnaMatrix, mutationCount);
                if (mutationCount > MAX_MUTATIONS) {
                    log.info("More than {} mutations were detected", MAX_MUTATIONS);
                    return true;
                }
            }
        }
        return false;
    }

    private int searchRepeatedLetters(int i, int j, char[][] dnaMatrix, int mutationCount) {
        for (Direction direction : Direction.values()) {
            boolean isSequence = isSequence(dnaMatrix, i, j, direction);
            mutationCount += isSequence ? 1 : 0; // Adds 1 mutation if there is a valid sequence in that direction
            if (mutationCount > MAX_MUTATIONS) { // This validation reduces the number of sequence calculations in case it is close to maximum mutations
                return mutationCount;
            }
        }
        return mutationCount;
    }

    private boolean isSequence(char[][] dnaMatrix, int i, int j, Direction direction) {
        if (isWithinRange(dnaMatrix, i, j, direction)) {
            return false;
        }
        for (int k = 1; k < SEQUENCE_LENGTH; k++) {
            char baseLetter = dnaMatrix[i][j];
            boolean isSameLetter = baseLetter == calculateNextLetter(dnaMatrix, i, j, k, direction);
            if (!isSameLetter) {
                return false;
            }
        }
        log.info("{} sequence found in [{}]" + "[{}]", direction, i, j);
        return true;
    }

    /*
        Validates if the sequence is within the range of the DNA matrix
     */
    private boolean isWithinRange(char[][] dnaMatrix, int i, int j, Direction direction) {
        switch (direction) {
            case HORIZONTAL:
                return j + SEQUENCE_LENGTH > dnaMatrix.length;
            case VERTICAL:
                return i + SEQUENCE_LENGTH > dnaMatrix.length;
            case DIAGONAL_RIGHT:
                return i + SEQUENCE_LENGTH > dnaMatrix.length || j + SEQUENCE_LENGTH > dnaMatrix.length;
            case DIAGONAL_LEFT:
                return i + SEQUENCE_LENGTH > dnaMatrix.length || j - (SEQUENCE_LENGTH - 1) < 0;
        }
        return false; // Unreachable code because there is a case for every possible direction
    }


    private char calculateNextLetter(char[][] dnaMatrix, int i, int j, int k, Direction direction) {
        switch (direction) {
            case HORIZONTAL:
                return dnaMatrix[i][j + k];
            case VERTICAL:
                return dnaMatrix[i + k][j];
            case DIAGONAL_RIGHT:
                return dnaMatrix[i + k][j + k];
            case DIAGONAL_LEFT:
                return dnaMatrix[i + k][j - k];
        }
        return '-'; // Unreachable code because there is a case for every possible direction
    }

    private char[][] buildDNAMatrix(String[] dna) {
        char[][] dnaMatrix = new char[dna.length][dna.length];
        for (int i = 0; i < dna.length; i++) {
            dnaMatrix[i] = dna[i].toCharArray();
        }
        logDNAMatrix(dnaMatrix);
        return dnaMatrix;
    }

    private void logDNAMatrix(char[][] dnaMatrix) {
        String line = "";
        for (int i = 0; i < dnaMatrix.length; i++) {
            for (int j = 0; j < dnaMatrix.length; j++) {
                line += "[" + dnaMatrix[i][j] + "]";
            }
            log.info(line);
            line = "";
        }
    }

    private void storeDNAVerified(String[] dna, boolean isMutant) {
        String dnaMatrix = String.join("-", dna);
        DNAType dnaType = isMutant ? DNAType.MUTANT : DNAType.HUMAN;
        log.info("DNA is from a {}", dnaType);
        DNA dnaAnalysed = new DNA(dnaMatrix, dnaType);
        dNARepository.save(dnaAnalysed);
        statisticService.incrementDNACount(dnaType);
    }

}

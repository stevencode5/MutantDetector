package com.rentitfurnished.mutantdetector.controller;

import com.rentitfurnished.mutantdetector.domain.dto.DNADto;
import com.rentitfurnished.mutantdetector.service.MutantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mutant")
@AllArgsConstructor
public class MutantDetectorController {

    private final MutantService mutantService;
    private final String IS_MUTANT_MESSAGE = "Is mutant !";
    private final String IS_HUMAN_MESSAGE = "Is human !";
    private final String INVALID_PAYLOAD_SIZE_MESSAGE = "Invalid payload. The DNA must have size NxN";
    private final String INVALID_PAYLOAD_CONTENT_MESSAGE = "Invalid payload. The DNA must contain only characters: A, T, C, G";

    @PostMapping
    public ResponseEntity<String> isMutant(@RequestBody DNADto dna) {
        log.info("DNA received : {}", dna);
        if (!isValidPayloadSize(dna)) {
            return ResponseEntity.badRequest().body(INVALID_PAYLOAD_SIZE_MESSAGE);
        }
        if (!isOnlyValidLettersInPayload(dna)) {
            return ResponseEntity.badRequest().body(INVALID_PAYLOAD_CONTENT_MESSAGE);
        }
        if (mutantService.isMutant(dna.getDna())) {
            return ResponseEntity.ok().body(IS_MUTANT_MESSAGE);
        } else {
            return ResponseEntity.status(403).body(IS_HUMAN_MESSAGE);
        }
    }

    private boolean isValidPayloadSize(DNADto dna) {
        int height = dna.getDna().length;
        for (String line : dna.getDna()) {
            if (line.length() != height) {
                return false;
            }
        }
        return true;
    }

    private boolean isOnlyValidLettersInPayload(DNADto dna) {
        for (String line : dna.getDna()) {
            line = line.replaceAll("([A]|[T]|[C]|[G])", "");
            if (line.length() > 0) {
                return false;
            }
        }
        return true;
    }

}

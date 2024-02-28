package br.com.rocketseat.job_oportunity_management.modules.candidate.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_oportunity_management.modules.candidate.CandidateEntity;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @PostMapping
    public void create(@Valid @RequestBody CandidateEntity candidate) {
        System.out.println("Candidate");
        System.out.println(candidate.getEmail());
    }
}

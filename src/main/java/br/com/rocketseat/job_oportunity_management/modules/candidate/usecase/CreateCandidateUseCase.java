package br.com.rocketseat.job_oportunity_management.modules.candidate.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_oportunity_management.exceptions.UserFoundException;
import br.com.rocketseat.job_oportunity_management.modules.candidate.CandidateEntity;
import br.com.rocketseat.job_oportunity_management.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    @Autowired
    CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidate) {
        candidateRepository
                .findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        return candidateRepository.save(candidate);
    }
}

package br.com.rocketseat.job_oportunity_management.modules.candidate.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_oportunity_management.exceptions.UserFoundException;
import br.com.rocketseat.job_oportunity_management.modules.candidate.CandidateEntity;
import br.com.rocketseat.job_oportunity_management.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidate) {
        candidateRepository
                .findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        var passwordEncoded = passwordEncoder.encode(candidate.getPassword());
        candidate.setPassword(passwordEncoded);

        return candidateRepository.save(candidate);
    }
}

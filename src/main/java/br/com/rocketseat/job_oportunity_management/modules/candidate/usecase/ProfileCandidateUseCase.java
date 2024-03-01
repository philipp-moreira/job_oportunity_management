package br.com.rocketseat.job_oportunity_management.modules.candidate.usecase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_oportunity_management.modules.candidate.CandidateRepository;
import br.com.rocketseat.job_oportunity_management.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID candidateId) {
        var candidate = candidateRepository
                .findById(candidateId)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("user not found");
                });

        var candidateDTO = ProfileCandidateResponseDTO
                .builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .description(candidate.getDescription())
                .build();

        return candidateDTO;
    }
}

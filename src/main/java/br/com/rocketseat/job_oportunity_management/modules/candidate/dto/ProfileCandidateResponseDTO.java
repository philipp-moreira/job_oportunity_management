package br.com.rocketseat.job_oportunity_management.modules.candidate.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String description;
}

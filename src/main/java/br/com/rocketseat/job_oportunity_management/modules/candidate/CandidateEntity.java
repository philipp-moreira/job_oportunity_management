package br.com.rocketseat.job_oportunity_management.modules.candidate;

import java.util.UUID;

import lombok.Data;

@Data
public class CandidateEntity {
    UUID id;
    String name;
    String username;
    String email;
    String password;
    String description;
    String curriculum;
}

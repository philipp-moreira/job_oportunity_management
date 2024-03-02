package br.com.rocketseat.job_oportunity_management.modules.company.dto;

import lombok.Data;

@Data
public class CreateJobDTO {
    private String level;
    private String benefits;
    private String description;
}

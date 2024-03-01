package br.com.rocketseat.job_oportunity_management.modules.company.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_oportunity_management.modules.company.dto.CreateJobDTO;
import br.com.rocketseat.job_oportunity_management.modules.company.entity.JobEntity;
import br.com.rocketseat.job_oportunity_management.modules.company.usecase.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    CreateJobUseCase createJobUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO job, HttpServletRequest request) {
        try {
            var companyId = request.getAttribute("company_id");
            var companyIdParsed = UUID.fromString(companyId.toString());

            var jobEntity = JobEntity
                    .builder()
                    .companyID(companyIdParsed)
                    .level(job.getLevel())
                    .benefits(job.getBenefits())
                    .description(job.getDescription())
                    .build();

            var result = createJobUseCase.execute(jobEntity);

            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

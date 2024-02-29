package br.com.rocketseat.job_oportunity_management.modules.company.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_oportunity_management.modules.company.entity.JobEntity;
import br.com.rocketseat.job_oportunity_management.modules.company.repository.JobRepository;

@Service
public class CreateJobUseCase {

    @Autowired
    JobRepository jobRepository;

    public JobEntity execute(JobEntity job) {
        return jobRepository.save(job);
    }
}

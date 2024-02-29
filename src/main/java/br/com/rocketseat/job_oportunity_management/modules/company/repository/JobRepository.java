package br.com.rocketseat.job_oportunity_management.modules.company.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rocketseat.job_oportunity_management.modules.company.entity.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

}

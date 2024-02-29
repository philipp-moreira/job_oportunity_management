package br.com.rocketseat.job_oportunity_management.modules.company.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "job")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String level;
    String benefits;
    String description;

    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "company_id")
    UUID companyID;

    @ManyToOne
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    CompanyEntity company;
}

package br.com.rocketseat.job_oportunity_management.modules.company.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "company")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String name;

    @Pattern(regexp = "^\\S*$", message = "must don't have blank spaces.")
    @NotBlank
    String username;

    @Email(message = "The attribute must be have a valid e-mail.")
    String email;

    @Length(min = 10, max = 100)
    String password;

    String webSite;
    String description;

    @CreationTimestamp
    LocalDateTime createdAt;
}

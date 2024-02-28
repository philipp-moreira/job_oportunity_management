package br.com.rocketseat.job_oportunity_management.modules.candidate;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CandidateEntity {
    UUID id;
    String name;

    @Pattern(regexp = "^\\S*$", message = "must don't have blank spaces.")
    @NotBlank
    String username;

    @Email(message = "The attribute must be have a valid e-mail.")
    String email;

    @Length(min = 10, max = 100)
    String password;
    String description;
    String curriculum;
}

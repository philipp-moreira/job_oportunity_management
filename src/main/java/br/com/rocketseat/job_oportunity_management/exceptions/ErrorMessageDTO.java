package br.com.rocketseat.job_oportunity_management.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageDTO {
    String field;
    String message;
}

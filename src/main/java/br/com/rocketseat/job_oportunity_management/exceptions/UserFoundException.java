package br.com.rocketseat.job_oportunity_management.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException() {
        super("user already exists.");
    }
}

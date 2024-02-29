package br.com.rocketseat.job_oportunity_management.modules.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_oportunity_management.modules.company.dto.AuthCompanyDTO;
import br.com.rocketseat.job_oportunity_management.modules.company.usecase.AuthCompanyUseCase;

@RestController
@RequestMapping("/auth")
public class AuthCompanyController {

    @Autowired
    AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("/company")
    public ResponseEntity<Object> auth(@RequestBody AuthCompanyDTO company) {
        try {
            var result = authCompanyUseCase.execute(company);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

package br.com.rocketseat.job_oportunity_management.modules.company.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_oportunity_management.exceptions.UserFoundException;
import br.com.rocketseat.job_oportunity_management.modules.company.entity.CompanyEntity;
import br.com.rocketseat.job_oportunity_management.modules.company.repository.CompanyRepository;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity company) {
        companyRepository
                .findByUsernameOrEmail(company.getUsername(), company.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        var passwordEncoded = passwordEncoder.encode(company.getPassword());
        company.setPassword(passwordEncoded);

        return companyRepository.save(company);
    }

}

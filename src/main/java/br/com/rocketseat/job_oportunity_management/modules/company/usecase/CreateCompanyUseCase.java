package br.com.rocketseat.job_oportunity_management.modules.company.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_oportunity_management.exceptions.UserFoundException;
import br.com.rocketseat.job_oportunity_management.modules.company.entity.CompanyEntity;
import br.com.rocketseat.job_oportunity_management.modules.company.repository.CompanyRepository;

@Service
public class CreateCompanyUseCase {

    @Autowired
    CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity company) {
        companyRepository
                .findByUsernameOrEmail(company.getUsername(), company.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        return companyRepository.save(company);
    }

}

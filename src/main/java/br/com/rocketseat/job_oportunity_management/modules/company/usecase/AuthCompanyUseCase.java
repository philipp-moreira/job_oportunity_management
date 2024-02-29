package br.com.rocketseat.job_oportunity_management.modules.company.usecase;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_oportunity_management.modules.company.dto.AuthCompanyDTO;
import br.com.rocketseat.job_oportunity_management.modules.company.repository.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void execute(AuthCompanyDTO userCompany) throws AuthenticationException {
        var exceptionPhrase = "username/password incorrect";
        var company = companyRepository
                .findByUsername(userCompany.getUsername())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(exceptionPhrase);
                });

        var passwordMatches = passwordEncoder.matches(userCompany.getPassword(), company.getPassword());
        if (!passwordMatches) {
            throw new AuthenticationException(exceptionPhrase);
        }

    }
}

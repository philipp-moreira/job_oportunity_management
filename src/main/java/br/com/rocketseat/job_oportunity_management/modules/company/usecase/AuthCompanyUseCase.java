package br.com.rocketseat.job_oportunity_management.modules.company.usecase;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.rocketseat.job_oportunity_management.modules.company.dto.AuthCompanyDTO;
import br.com.rocketseat.job_oportunity_management.modules.company.dto.AuthCompanyResponseDTO;
import br.com.rocketseat.job_oportunity_management.modules.company.repository.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${security.token.secret}")
    String secretKey;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO userCompany) throws AuthenticationException {
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

        var algorithm = Algorithm.HMAC256(secretKey);
        var momentToExpire = Instant.now().plus(Duration.ofHours(2L));

        var jwt = JWT.create()
                .withIssuer("javagas")
                .withSubject(company.getId().toString())
                .withExpiresAt(momentToExpire)
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        var response = AuthCompanyResponseDTO
                .builder()
                .access_token(jwt)
                .expires_in(momentToExpire.toEpochMilli())
                .build();

        return response;
    }
}

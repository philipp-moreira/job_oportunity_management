package br.com.rocketseat.job_oportunity_management.modules.candidate.usecase;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.rocketseat.job_oportunity_management.modules.candidate.CandidateRepository;
import br.com.rocketseat.job_oportunity_management.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.rocketseat.job_oportunity_management.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    String secretKey;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO candidateRequest) throws AuthenticationException {
        var phraseException = "username/password incorrect";

        var candidate = candidateRepository
                .findByUsername(candidateRequest.username())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(phraseException);
                });

        var passwordMatches = passwordEncoder.matches(candidateRequest.password(), candidate.getPassword());
        if (!passwordMatches) {
            throw new AuthenticationException(phraseException);
        }

        var algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(2L));

        var token = JWT.create()
                .withIssuer("javagas")
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("CANDIDATE"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        var response = AuthCandidateResponseDTO
                .builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return response;

    }
}

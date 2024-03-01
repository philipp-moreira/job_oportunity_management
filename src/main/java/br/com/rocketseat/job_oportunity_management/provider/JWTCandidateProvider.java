package br.com.rocketseat.job_oportunity_management.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTCandidateProvider {

    @Value("${security.token.secret.candidate}")
    String secretKey;

    public DecodedJWT validateToken(String tokenJWTRaw) {
        try {
            var tokenJWT = tokenJWTRaw.replace("Bearer ", "");

            var algorithm = Algorithm.HMAC256(secretKey);

            return JWT
                    .require(algorithm)
                    .build()
                    .verify(tokenJWT);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }
}

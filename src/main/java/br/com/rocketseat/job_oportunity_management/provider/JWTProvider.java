package br.com.rocketseat.job_oportunity_management.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTProvider {

    @Value("${security.token.secret}")
    String secretKey;

    public DecodedJWT validateToken(String contentFromAuthorization) {
        var token = contentFromAuthorization.replace("Bearer ", "");
        var algorithm = Algorithm.HMAC256(secretKey);
        try {
            var subject = JWT
                    .require(algorithm)
                    .build()
                    .verify(token);

            return subject;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }
}

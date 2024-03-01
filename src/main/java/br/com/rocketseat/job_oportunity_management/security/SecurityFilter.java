package br.com.rocketseat.job_oportunity_management.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.rocketseat.job_oportunity_management.provider.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/company")) {

            var authorizationFromHeaderRequest = request.getHeader("Authorization");
            if (authorizationFromHeaderRequest != null) {

                var subjectJWT = jwtProvider.validateToken(authorizationFromHeaderRequest);
                if (subjectJWT.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                request.setAttribute("company_id", subjectJWT);

                var contextAuth = new UsernamePasswordAuthenticationToken(subjectJWT, null, Collections.emptyList());

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(contextAuth);
            }
        }

        filterChain.doFilter(request, response);
    }

}

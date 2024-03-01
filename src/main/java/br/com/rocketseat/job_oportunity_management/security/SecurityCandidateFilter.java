package br.com.rocketseat.job_oportunity_management.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.rocketseat.job_oportunity_management.provider.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {

    @Autowired
    JWTCandidateProvider jwtCandidateProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/candidate")) {

            var authenticationToken = request.getHeader("Authorization");
            if (authenticationToken != null) {

                var decodedJWT = jwtCandidateProvider.validateToken(authenticationToken);
                if (decodedJWT == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                request.setAttribute("candidate_id", decodedJWT.getSubject());

                var roles = decodedJWT
                        .getClaim("roles")
                        .asList(Object.class);

                var grants = roles
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString()))
                        .toList();

                var contextAuth = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, grants);

                SecurityContextHolder.getContext().setAuthentication(contextAuth);
            }
        }
        filterChain.doFilter(request, response);
    }
}

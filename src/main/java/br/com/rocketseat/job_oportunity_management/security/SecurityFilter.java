package br.com.rocketseat.job_oportunity_management.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/company")) {

            var authorizationFromHeaderRequest = request.getHeader("Authorization");
            if (authorizationFromHeaderRequest != null) {

                var decodedJWT = jwtProvider.validateToken(authorizationFromHeaderRequest);
                if (decodedJWT == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                request.setAttribute("company_id", decodedJWT.getSubject());

                var roles = decodedJWT.getClaim("roles").asList(Object.class);

                var grants = roles
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                        .toList();

                var contextAuth = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null,
                        grants);

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(contextAuth);
            }
        }

        filterChain.doFilter(request, response);
    }

}

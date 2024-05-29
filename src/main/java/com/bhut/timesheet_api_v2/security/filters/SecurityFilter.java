package com.bhut.timesheet_api_v2.security.filters;

import com.bhut.timesheet_api_v2.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;

    public SecurityFilter(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if (request.getRequestURI().startsWith("/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (header != null) {
            final var token = jwtProvider.validateToken(header);
            if (token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            final var tokenSubject = token.getSubject();
            request.setAttribute("user_id", tokenSubject);
            final var roles = token.getClaim("roles").asList(Object.class);
            final var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString())).toList();
            final var auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
package com.bhut.timesheet_api_v2.security;

import com.bhut.timesheet_api_v2.security.filters.SecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final SecurityFilter securityUserFilter;

    private static final String[] PERMITTED_ROUTES_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger/resources/**",
            "/actuator/**",
            "/v1/auth/**"
    };

    public SecurityConfig(final SecurityFilter securityFilter) {
        this.securityUserFilter = securityFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/v1/user/createUser").permitAll()
                            .requestMatchers(PERMITTED_ROUTES_LIST).permitAll();
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(securityUserFilter, BasicAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

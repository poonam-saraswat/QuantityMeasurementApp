package com.app.quantitymeasurement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Disables Spring Security's default login page and permits all requests.
 *
 * This is appropriate for a development/internal REST API.
 * All endpoints — including /api/measurements/**, /swagger-ui.html,
 * /h2-console, /actuator — are open without authentication.
 *
 * To re-enable security in production, replace permitAll() with
 * proper role-based rules and configure a real UserDetailsService.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF — not needed for stateless REST APIs
            .csrf(AbstractHttpConfigurer::disable)

            // Allow H2 console to render in iframes (uses frames internally)
            .headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            )

            // Permit ALL requests — no login required
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )

            // Disable the default form login page
            .formLogin(AbstractHttpConfigurer::disable)

            // Disable HTTP Basic auth popup
            .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }
}

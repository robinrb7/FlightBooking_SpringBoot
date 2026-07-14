package com.robin.flightbooking.config;

import com.robin.flightbooking.filter.JwtAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@AllArgsConstructor
@Configuration
public class SecurityConfig {
    private final AppConfig appConfig;
    private final JwtAuthFilter jwtAuthFilter;
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception{

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(appConfig.authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/auth/signup",
                        "/auth/login",
                        "/auth/refresh"
                ).permitAll()
                .anyRequest().authenticated()

                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}

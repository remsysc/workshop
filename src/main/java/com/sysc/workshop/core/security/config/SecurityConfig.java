package com.sysc.workshop.core.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysc.workshop.core.security.jwt.AuthTokenFilter;
import com.sysc.workshop.core.security.jwt.JwtAuthEntryPoint;
import com.sysc.workshop.core.security.user.SecurityUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(
        @NonNull HttpSecurity http,
        DaoAuthenticationProvider daoAuthenticationProvider,
        JwtAuthEntryPoint authEntryPoint,
        AuthTokenFilter authTokenFilter,
        AccessDeniedHandler accessDeniedHandler
    ) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception ->
                exception
                    .authenticationEntryPoint(authEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
            )
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth ->
                auth
                    .requestMatchers(apiPrefix + "/auth/**")
                    .permitAll()
                    .requestMatchers(apiPrefix + "/admin/**")
                    .hasRole("ADMIN")
                    .requestMatchers(apiPrefix + "/user/**")
                    .hasRole("USER")
                    .requestMatchers(apiPrefix + "/**")
                    .authenticated()
                    .anyRequest()
                    .denyAll()
            )
            .authenticationProvider(daoAuthenticationProvider)
            .addFilterBefore(
                authTokenFilter,
                UsernamePasswordAuthenticationFilter.class
            );
        return http.build();
    }

    // @Bean
    // public RoleHierarchy roleHierarchy() {
    //     RoleHierarchy roleHierarchy = new RoleHierarchy(
    //         "ROLE_ADMIN",
    //         "ROLE_USER",
    //         "ROLE_VIEWER"
    //     );
    // }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
        SecurityUserDetailsService userDetailsService,
        PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(
            userDetailsService
        );
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
        @NonNull AuthenticationConfiguration authConfig
    ) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(ObjectMapper mapper) {
        return (request, response, ex) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", Instant.now());
            body.put("status", HttpServletResponse.SC_FORBIDDEN);
            body.put("error", "Forbidden");
            body.put("message", ex.getMessage());
            body.put("path", request.getServletPath());
            mapper.writeValue(response.getOutputStream(), body);
        };
    }
}

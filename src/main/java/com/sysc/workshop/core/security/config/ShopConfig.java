package com.sysc.workshop.core.security.config;

import com.sysc.workshop.core.security.jwt.AuthTokenFilter;
import com.sysc.workshop.core.security.jwt.JwtAuthEntryPoint;
import com.sysc.workshop.core.security.user.ShopUserDetailsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class ShopConfig {

    private final ShopUserDetailsService userDetailsService;
    private final JwtAuthEntryPoint authEntryPoint;
    private static final List<String> SECURED_URLS = List.of(
        "/api/v1/products/**"
    );

    @Bean
    public SecurityFilterChain securityFilterChain(@NonNull HttpSecurity http)
        throws Exception {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider(
            userDetailsService
        );
        daoProvider.setPasswordEncoder(passwordEncoder());
        http
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception ->
                exception.authenticationEntryPoint(authEntryPoint)
            )
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth ->
                auth
                    .requestMatchers(SECURED_URLS.toArray(String[]::new))
                    .authenticated()
                    .anyRequest()
                    .permitAll()
            );
        http.authenticationProvider(daoProvider);
        http.addFilterBefore(
            authTokenFilter(),
            UsernamePasswordAuthenticationFilter.class
        );
        return http.build();
    }

    //   @Bean
    //    public DaoAuthenticationProvider daoAuthenticationProvider(){
    //        var authProvider = new DaoAuthenticationProvider(userDetailsService);
    //        authProvider.setPasswordEncoder(passwordEncoder());
    //        return authProvider;
    //    }

    @Bean
    public AuthenticationManager authenticationManager(
        @NonNull AuthenticationConfiguration authConfig
    ) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

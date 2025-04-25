package com.cse5382.PhoneBook_Starter_Java_SpringBoot.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Allow Swagger/OpenAPI docs without authentication
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/swagger-ui/index.html",
                    "/v3/api-docs/**",
                    "/favicon.ico"
                ).permitAll()

                // ROLE-BASED PROTECTION
                .requestMatchers("/phonebook/list")
                    .hasAnyRole("READER", "WRITER")
                .requestMatchers(
                    "/phonebook/add",
                    "/phonebook/delete-by-name",
                    "/phonebook/delete-by-number"
                ).hasRole("WRITER")

                .anyRequest().authenticated()
            )
            .httpBasic(); // Basic auth for simplicity
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder pwEncoder) {
        // Reader account: can only LIST
        UserDetails reader = User.withUsername("reader")
            .password(pwEncoder.encode("reader123"))
            .roles("READER")
            .build();

        // Writer account: can LIST, ADD, DELETE
        UserDetails writer = User.withUsername("writer")
            .password(pwEncoder.encode("writer123"))
            .roles("WRITER")
            .build();

        return new InMemoryUserDetailsManager(reader, writer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

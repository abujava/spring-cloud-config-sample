package com.abujava.configserver;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is not documented :(
 *
 * @author Muhammad Muminov
 * @since 5/9/2023
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final ClientProperties clientProperties;

    @Bean
    public static BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetailsList = clientProperties.getClients()
                .stream()
                .map(client ->
                        User.builder()
                                .username(client.getUsername())
                                .password(encoder().encode(client.getPassword()))
                                .roles(client.getName())
                                .build()
                )
                .collect(Collectors.toList());
        return new InMemoryUserDetailsManager(userDetailsList);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(HttpMethod.POST, "/encrypt")
                            .permitAll();

                    clientProperties.getClients()
                            .forEach(client ->
                                    auth.requestMatchers(HttpMethod.GET, client.getPathWithStartsWith()).access(new WebExpressionAuthorizationManager(client.getRole()))
                            );

                    auth
                            .anyRequest()
                            .denyAll();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}

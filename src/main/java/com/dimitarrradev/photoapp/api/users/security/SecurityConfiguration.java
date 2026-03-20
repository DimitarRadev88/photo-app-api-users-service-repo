package com.dimitarrradev.photoapp.api.users.security;

import com.dimitarrradev.photoapp.api.users.service.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final PasswordEncoder passwordEncoder;
    private final UsersService usersService;
    @Value("${gateway.ip}")
    private String ipAddress;
    @Value("${login.url.path}")
    private String usersLoginUrl;
    @Value("${token.expiration_time}")
    private Long tokenExpirationTime;
    @Value("${token.secret}")
    private String tokenSecret;

    public SecurityConfiguration(PasswordEncoder passwordEncoder, UsersService usersService) {
        this.passwordEncoder = passwordEncoder;
        this.usersService = usersService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(usersService)
                .passwordEncoder(passwordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, usersService, tokenSecret, tokenExpirationTime);
        authenticationFilter.setFilterProcessesUrl(usersLoginUrl);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(HttpMethod.GET, "/users/status/check").permitAll()
                        .requestMatchers(HttpMethod.POST, "/actuator/**")
                        .access(new WebExpressionAuthorizationManager(String.format("hasIpAddress('%s')", ipAddress)))
                        .requestMatchers(HttpMethod.POST, "/users")
                        .access(new WebExpressionAuthorizationManager(String.format("hasIpAddress('%s')", ipAddress)))
                        .requestMatchers(HttpMethod.GET, "/h2-console/**")
                        .permitAll())

                .addFilter(authenticationFilter)

                .authenticationManager(authenticationManager)

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }

}

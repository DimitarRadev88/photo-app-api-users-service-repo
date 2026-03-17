package com.dimitarrradev.photoapp.api.users.security;

import com.dimitarrradev.photoapp.api.users.model.LoginRequestModel;
import com.dimitarrradev.photoapp.api.users.model.UserDto;
import com.dimitarrradev.photoapp.api.users.service.UsersService;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UsersService usersService;
    private Long tokenExpirationTime;
    private String tokenSecret;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UsersService usersService, String tokenSecret, Long tokenExpirationTime) {
        super(authenticationManager);
        this.usersService = usersService;
        this.tokenSecret = tokenSecret;
        this.tokenExpirationTime = tokenExpirationTime;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestModel credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestModel.class);

            return getAuthenticationManager()
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    credentials.getEmail(),
                                    credentials.getPassword(),
                                    new ArrayList<>()
                            )
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();

        String username = user.getUsername();

        UserDto userDetails = usersService.getUserDetails(username);

        Instant now = Instant.now();

        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());

        SecretKey secretKey = hmacShaKeyFor(secretKeyBytes);

        String token = Jwts.builder()
                .subject(userDetails.getUserId())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(tokenExpirationTime)))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDetails.getUserId());
    }

}

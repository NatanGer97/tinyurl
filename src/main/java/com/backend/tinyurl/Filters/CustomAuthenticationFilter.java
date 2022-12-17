package com.backend.tinyurl.Filters;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.*;
import com.backend.tinyurl.Modles.*;
import com.fasterxml.jackson.databind.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("Username {}", username);
        logger.info("password {}", password);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        /*super.successfulAuthentication(request, response, chain, authResult);*/
        User user = (User) authResult.getPrincipal();
        logger.info("User {}", user);

        // TODO:   move secret to properties file
        /**
         * secret is used to sign the token
         */
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                // 10 min expiry
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString()).sign(algorithm);

        // here will come the refresh token

        response.setHeader("access_token", accessToken);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("token", accessToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), responseData);


    }

  /*  @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }*/
}

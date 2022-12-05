package com.backend.tinyurl.Filters;

import com.auth0.jwt.*;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.*;
import com.auth0.jwt.interfaces.*;
import com.fasterxml.jackson.databind.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.web.filter.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Logger logger = LoggerFactory.getLogger(CustomAuthorizationFilter.class);

        if (httpServletRequest.getServletPath().equals("/api/login")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username,
                                    null, null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                } catch (Exception exception) {
                    httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                    logger.error("error login in : {}", exception.getMessage());
                    httpServletResponse.setHeader("error", exception.getMessage());
                    httpServletResponse.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();

                    error.put("error_message", exception.getMessage());
                    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(httpServletResponse.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }
    }
}

package com.sweaterbank.leasing.car.config;

import com.sweaterbank.leasing.car.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    JwtService jwtService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response); //pushes the request and response down the chain
            return;
        }

        jwtToken = authHeader.substring(7);
        userEmail = jwtService.extractEmail(jwtToken);
        if(
                userEmail != null &&
                SecurityContextHolder.getContext().getAuthentication() == null){
            //TODO: use UserDetailsService to get an user
        }
}

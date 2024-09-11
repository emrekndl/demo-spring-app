/**
 * A Spring Security filter that authenticates requests using a JWT token.
 * 
 * This filter checks the "Authorization" header of incoming requests for a valid JWT token.
 * If a valid token is found, it creates an authenticated `UsernamePasswordAuthenticationToken`
 * and sets it in the `SecurityContextHolder`.
 */
package com.example.demospringapp.security.jwt;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain filterChain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        String token = null;

        // header: Authorization: Bearer [jwt]
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        
        if (token != null && JwtUtil.isTokenValid(token)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                JwtUtil.getClaimsFromToken(token).getSubject(),
                null, // verified credentials when calling isTokenValid()
                Collections.emptyList() // roles and authorities

            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
        
    }

}

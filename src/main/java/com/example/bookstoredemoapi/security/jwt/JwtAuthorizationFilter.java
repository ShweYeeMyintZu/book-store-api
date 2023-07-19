package com.example.bookstoredemoapi.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private JwtProvider jwtProvider;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,JwtProvider jwtProvider) {
        super(authenticationManager);
        this.jwtProvider=jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication=jwtProvider.getAuthentication(request);
        if(authentication!=null && jwtProvider.validateToken(request)){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
         chain.doFilter(request,response);
    }
}

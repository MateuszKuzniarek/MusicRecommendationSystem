package com.borsuki.app.musicrecommendationsystem.security;

import com.borsuki.app.musicrecommendationsystem.services.ApplicationUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final ApplicationUserServiceImpl applicationUserService;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtRequestFilter(ApplicationUserServiceImpl applicationUserService,
            JwtTokenUtil jwtTokenUtil) {
        this.applicationUserService = applicationUserService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        String username = null;
        String requestTokenHeader = httpServletRequest.getHeader("Authorization");
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.substring(7);
            username = jwtTokenUtil.getUsernameFromToken(token);
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = applicationUserService.loadUserByUsername(username);
            if(jwtTokenUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

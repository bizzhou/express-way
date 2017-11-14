package com.expressway.jwt;


import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final String filterPattern;
    private final PathMatcher pathMatcher = new AntPathMatcher();

    public JwtAuthFilter(String filterPattern) {
        this.filterPattern = filterPattern;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {

            if (pathMatcher.match(filterPattern, httpServletRequest.getServletPath())) {
                httpServletRequest = JwtUtil.validateTokenAndAddUserIdToHeader(httpServletRequest);
            }

        } catch (Exception e) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}

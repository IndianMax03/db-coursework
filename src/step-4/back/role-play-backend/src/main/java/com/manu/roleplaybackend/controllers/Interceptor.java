package com.manu.roleplaybackend.controllers;

import java.util.Set;
import java.util.HashSet;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Interceptor implements HandlerInterceptor {

    private final static Set<String> tokens = new HashSet<>();
    private final static String registerURI = "/enter/register";
    private final static String loginURI = "/enter/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (tokens.isEmpty()) {
            tokens.add("123");
        }
        if (
            request.getHeader("token") != null &&
            tokens.contains(request.getHeader("token").toString()) ||
            request.getRequestURI().equals(registerURI) && request.getMethod().equals("POST") ||
            request.getRequestURI().equals(loginURI) && request.getMethod().equals("POST")
            ) {
            return true;
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        if (
            request.getRequestURI().equals(registerURI) && request.getMethod().equals("POST") ||
            request.getRequestURI().equals(loginURI) && request.getMethod().equals("POST")
            ) {
                if (response.containsHeader("token")) {
                    tokens.add(response.getHeader("token"));
                }
            }
    }
    
    

}

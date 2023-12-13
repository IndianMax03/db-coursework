package com.manu.roleplaybackend.controllers;

import java.util.Set;
import java.util.HashSet;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Interceptor implements HandlerInterceptor {

    private final static Set<String> tokens = new HashSet<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (tokens.isEmpty()) {
            tokens.add("123");
        }
        if (request.getHeader("token") != null && tokens.contains(request.getHeader("token").toString())) {
            return true;
        }
        response.setStatus(403);
        response.getWriter().write("Permission denied");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
    
    

}

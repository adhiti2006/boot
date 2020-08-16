package com.ss.demo.filter;

import com.ss.demo.exception.NotAuthorizedException;
import com.ss.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AuthenticationFilter implements HandlerInterceptor {

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (authenticationService.isNonSecureURI(request.getRequestURI())) {
            return true;
        }

        Map<String, List<String>> headerMap = fetchHeaderValues(request);
        validateHeaderAttributes(headerMap);
        return true;
    }

    private void validateHeaderAttributes(Map<String, List<String>> headerMap) throws NotAuthorizedException {
        if (!StringUtils.isEmpty(headerMap)) {
            if (!authenticationService.isValidHeaders(headerMap)) {
                throw new NotAuthorizedException("Required client headers not set");
            } else if (!authenticationService.isValidClient(headerMap)) {
                throw new NotAuthorizedException("User does not have access to this resource");
            } else if (!authenticationService.authenticate(headerMap)) {
                throw new NotAuthorizedException("User does not have permission to access this resource");
            }
        }
    }

    private Map<String, List<String>> fetchHeaderValues(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(Function.identity(), it -> Collections.list(request.getHeaders(it))));
    }
}

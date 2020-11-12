package com.edu.base;

import com.edu.model.Auth;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class WxContext {

    private static final String AUTH_KEY = "AUTH_KEY";
    private static final String TOKEN_KEY = "TOKEN_KEY";

    public void setAuthInContext(Auth auth) {
        RequestContextHolder.currentRequestAttributes().setAttribute(AUTH_KEY, auth, RequestAttributes.SCOPE_REQUEST);
    }

    public void setTokenInContext(String token) {
        RequestContextHolder.currentRequestAttributes().setAttribute(TOKEN_KEY, token, RequestAttributes.SCOPE_REQUEST);
    }

    public Auth getCurrentAuth() {
        return (Auth) RequestContextHolder.currentRequestAttributes().getAttribute(AUTH_KEY, RequestAttributes.SCOPE_REQUEST);
    }

    public String getUserToken() {
        return (String) RequestContextHolder.currentRequestAttributes().getAttribute(TOKEN_KEY, RequestAttributes.SCOPE_REQUEST);
    }

}
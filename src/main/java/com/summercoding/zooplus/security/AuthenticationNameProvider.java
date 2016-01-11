package com.summercoding.zooplus.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationNameProvider {
    public String authenticationName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

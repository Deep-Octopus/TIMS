package com.example.tims.service;

import com.example.tims.dto.RestBean;

import java.util.Map;

public interface AuthService {
    RestBean<Map<String, String>> login(String username, String password);

    RestBean<Object> logout(String username, String password);

    RestBean<Object> changePassword(String username, String password);
}

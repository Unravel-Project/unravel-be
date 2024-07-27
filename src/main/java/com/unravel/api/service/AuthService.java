package com.unravel.api.service;

import com.unravel.api.model.auth.AdminResponse;
import com.unravel.api.model.auth.LoginRequest;

public interface AuthService {

    String adminLogin(LoginRequest loginRequest);
    AdminResponse getProfile(String email);

}

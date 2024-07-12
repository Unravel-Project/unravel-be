package com.unravel.api.service;

import com.unravel.api.model.auth.BusinessUserResponse;
import com.unravel.api.model.auth.LoginRequest;

public interface AuthService {

    String businessUserLogin(LoginRequest loginRequest);
    BusinessUserResponse getProfile(String email);

}

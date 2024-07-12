package com.unravel.api.controller;

import com.unravel.api.controller.api.AuthApi;
import com.unravel.api.entity.BusinessUser;
import com.unravel.api.model.WebResponse;
import com.unravel.api.model.auth.BusinessUserResponse;
import com.unravel.api.model.auth.LoginRequest;
import com.unravel.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    @Autowired
    private AuthService authService;

    public WebResponse<String> loginBusinessUser(LoginRequest loginRequest) {
        String token = authService.businessUserLogin(loginRequest);
        return WebResponse.<String>builder().data(token).build();
    }

    public WebResponse<BusinessUserResponse> getBusinessUserProfile(BusinessUser businessUser) {
        BusinessUserResponse businessUserResponse = authService.getProfile(businessUser.getEmail());
        return WebResponse.<BusinessUserResponse>builder().data(businessUserResponse).build();
    }
}

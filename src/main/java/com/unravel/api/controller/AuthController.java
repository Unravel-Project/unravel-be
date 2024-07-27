package com.unravel.api.controller;

import com.unravel.api.controller.api.AuthApi;
import com.unravel.api.entity.Admin;
import com.unravel.api.model.WebResponse;
import com.unravel.api.model.auth.AdminResponse;
import com.unravel.api.model.auth.LoginRequest;
import com.unravel.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    @Autowired
    private AuthService authService;

    public WebResponse<String> loginAdmin(LoginRequest loginRequest) {
        String token = authService.adminLogin(loginRequest);
        return WebResponse.<String>builder().data(token).build();
    }

    public WebResponse<AdminResponse> getAdminProfile(Admin admin) {
        AdminResponse adminResponse = authService.getProfile(admin.getEmail());
        return WebResponse.<AdminResponse>builder().data(adminResponse).build();
    }
}

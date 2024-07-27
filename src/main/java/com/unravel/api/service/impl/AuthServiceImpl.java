package com.unravel.api.service.impl;

import com.unravel.api.entity.Admin;
import com.unravel.api.model.auth.AdminResponse;
import com.unravel.api.model.auth.LoginRequest;
import com.unravel.api.repository.AdminRepository;
import com.unravel.api.security.BCrypt;
import com.unravel.api.service.AuthService;
import com.unravel.api.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private TokenUtil tokenUtil;

    public String adminLogin(LoginRequest loginRequest) {
        Admin admin = adminRepository.findFirstByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "EMAIL_DOES_NOT_EXIST"));

        if (!BCrypt.checkpw(loginRequest.getPassword(), admin.getPasswd())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "INVALID_PASSWORD");
        }
        return tokenUtil.getAdminToken(admin.getId(), admin.getEmail());
    }

    public AdminResponse getProfile(String email) {
        Admin admin = adminRepository.findFirstByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.toString()));

        return AdminResponse.builder().id(admin.getId()).name(admin.getName()).email(admin.getEmail()).build();
    }
}

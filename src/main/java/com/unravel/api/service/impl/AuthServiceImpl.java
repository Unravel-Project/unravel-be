package com.unravel.api.service.impl;

import com.unravel.api.entity.BusinessUser;
import com.unravel.api.model.auth.BusinessUserResponse;
import com.unravel.api.model.auth.LoginRequest;
import com.unravel.api.repository.BusinessUserRepository;
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
    private BusinessUserRepository businessUserRepository;
    @Autowired
    private TokenUtil tokenUtil;

    public String businessUserLogin(LoginRequest loginRequest) {
        BusinessUser businessUser = businessUserRepository.findFirstByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "EMAIL_DOES_NOT_EXIST"));

        if (!BCrypt.checkpw(loginRequest.getPassword(), businessUser.getPasswd())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "INVALID_PASSWORD");
        }
        return tokenUtil.getBusinessUserToken(businessUser.getId(), businessUser.getEmail());
    }

    public BusinessUserResponse getProfile(String email) {
        BusinessUser businessUser = businessUserRepository.findFirstByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.toString()));

        return BusinessUserResponse.builder().id(businessUser.getId()).email(businessUser.getEmail()).build();
    }
}

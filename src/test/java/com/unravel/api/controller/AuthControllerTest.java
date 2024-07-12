package com.unravel.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unravel.api.model.WebResponse;
import com.unravel.api.model.auth.LoginRequest;
import com.unravel.api.util.ApiStaticData;
import com.unravel.api.util.TokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Test
    void testLoginBusinessUser_OK() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test");
        loginRequest.setPassword("test");

        mockMvc.perform(
                MockMvcRequestBuilders.post(ApiStaticData.API_BUSINESS_USER_PREFIX + "/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
        ).andExpect(status().isOk());
    }

    @Test
    void testLoginBusinessUser_Unauthorized() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test");
        loginRequest.setPassword("test1");

        mockMvc.perform(
                MockMvcRequestBuilders.post(ApiStaticData.API_BUSINESS_USER_PREFIX + "/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
        ).andExpect(status().isUnauthorized()).andDo((result) -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            Assertions.assertEquals(response.getErrors(), "INVALID_PASSWORD");
        });
    }

    @Test
    void testGetBusinessUserProfile_OK() throws Exception {
        String token = tokenUtil.getBusinessUserToken(1L, "test");

        mockMvc.perform(
                MockMvcRequestBuilders.get(ApiStaticData.API_BUSINESS_USER_PREFIX + "/auth/me")
                        .header("X-API-TOKEN", token)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testGetBusinessUserProfile_WrongEmail() throws Exception {
        String token = tokenUtil.getBusinessUserToken(1L, "dev");

        mockMvc.perform(
                MockMvcRequestBuilders.get(ApiStaticData.API_BUSINESS_USER_PREFIX + "/auth/me")
                        .header("X-API-TOKEN", token)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void testGetBusinessUserProfile_EmptyToken() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(ApiStaticData.API_BUSINESS_USER_PREFIX + "/auth/me")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void testGetBusinessUserProfile_InvalidToken() throws Exception {
        String token = "invalid_token";

        mockMvc.perform(
                MockMvcRequestBuilders.get(ApiStaticData.API_BUSINESS_USER_PREFIX + "/auth/me")
                        .header("X-API-TOKEN", token)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized());
    }

}

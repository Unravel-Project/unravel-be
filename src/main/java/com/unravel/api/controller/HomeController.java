package com.unravel.api.controller;

import com.unravel.api.controller.api.HomeApi;
import com.unravel.api.model.WebResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class HomeController implements HomeApi {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${spring.application.name}")
    private String name;

    public WebResponse<String> index() {
        String responseValue = String.format("REST API Unravel Malang. [%s] [%s]", activeProfile.toUpperCase(), name.toUpperCase());

        return WebResponse.<String>builder().data(responseValue).build();
    }

}

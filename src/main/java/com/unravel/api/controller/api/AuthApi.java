package com.unravel.api.controller.api;

import com.unravel.api.entity.BusinessUser;
import com.unravel.api.model.WebResponse;
import com.unravel.api.model.auth.BusinessUserResponse;
import com.unravel.api.model.auth.LoginRequest;
import com.unravel.api.util.ApiStaticData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthApi {

    @Operation(description = "This API will generate token for business user. You will need the token for X-API-TOKEN header.")
    @PostMapping(
            path = ApiStaticData.API_BUSINESS_USER_PREFIX + "/auth/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<String> loginBusinessUser(@RequestBody LoginRequest loginRequest);

    @Operation(description = "This API will display data of the business user who is currently logged in.")
    @Parameter(
            name = "businessUser",
            hidden = true)
    @Parameter(
            name = "X-API-TOKEN",
            required = true,
            in = ParameterIn.HEADER,
            schema = @Schema(type = "string"))
    @GetMapping(
            path = ApiStaticData.API_BUSINESS_USER_PREFIX + "/auth/me",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<BusinessUserResponse> getBusinessUserProfile(BusinessUser businessUser);

}

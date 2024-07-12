package com.unravel.api.model.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    @NotBlank
    @Size(max=100)
    private String email;

    @NotBlank
    @Size(max=100)
    private String password;

}

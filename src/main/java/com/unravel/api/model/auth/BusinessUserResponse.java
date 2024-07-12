package com.unravel.api.model.auth;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class BusinessUserResponse {

    private Long id;

    private String email;

}

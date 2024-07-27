package com.unravel.api.model.auth;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class AdminResponse {

    private Long id;

    private String name;

    private String email;

}

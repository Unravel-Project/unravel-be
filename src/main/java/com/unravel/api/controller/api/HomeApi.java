package com.unravel.api.controller.api;


import com.unravel.api.model.WebResponse;
import org.springframework.web.bind.annotation.GetMapping;

public interface HomeApi {

    @GetMapping(
            path = "/"
    )
    WebResponse<String> index();

}

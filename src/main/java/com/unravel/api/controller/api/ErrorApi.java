package com.unravel.api.controller.api;

import com.unravel.api.model.WebResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

public interface ErrorApi {

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<WebResponse<String>> apiException(ResponseStatusException exception);

}

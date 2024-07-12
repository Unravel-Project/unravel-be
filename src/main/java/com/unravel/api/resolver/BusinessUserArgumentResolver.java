package com.unravel.api.resolver;

import com.unravel.api.entity.BusinessUser;
import com.unravel.api.repository.BusinessUserRepository;
import com.unravel.api.util.TokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

@Component
public class BusinessUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private BusinessUserRepository businessUserRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return BusinessUser.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String token = httpServletRequest.getHeader("X-API-TOKEN");

        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "TOKEN_IS_REQUIRED");
        }

        String email = "";

        try {
            Claims claims = tokenUtil.verifyToken(token);
            email = (String) claims.get("email");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN");
        }

        return businessUserRepository.findFirstByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.toString()));
    }
}

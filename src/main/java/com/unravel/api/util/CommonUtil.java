package com.unravel.api.util;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    public String slugify(String str) {
        return str.replaceAll(" ", "-").toLowerCase();
    }

}

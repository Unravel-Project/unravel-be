package com.unravel.api.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApiStaticDataTest {

    @Test
    void testEquals() {
        Assertions.assertEquals("/api/business-user", ApiStaticData.API_BUSINESS_USER_PREFIX);
    }

}

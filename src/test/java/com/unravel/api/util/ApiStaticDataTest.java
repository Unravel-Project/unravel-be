package com.unravel.api.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApiStaticDataTest {

    @Test
    void testEquals() {
        Assertions.assertEquals("/api/admin", ApiStaticData.API_ADMIN_PREFIX);
    }

}

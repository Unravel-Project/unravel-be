package com.unravel.api.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(
        value = {
                CommonUtil.class
        }
)
public class CommonUtilTest {

    @Autowired
    CommonUtil commonUtil;

    @Test
    void testSlugify() {
        String slug = commonUtil.slugify("Test Category Slug");

        Assertions.assertEquals(slug, "test-category-slug");
    }
}

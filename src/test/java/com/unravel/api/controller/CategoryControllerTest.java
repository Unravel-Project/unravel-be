package com.unravel.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unravel.api.entity.Category;
import com.unravel.api.model.WebResponse;
import com.unravel.api.model.category.CategoryResponse;
import com.unravel.api.model.category.CreateCategoryRequest;
import com.unravel.api.model.category.UpdateCategoryRequest;
import com.unravel.api.repository.CategoryRepository;
import com.unravel.api.util.ApiStaticData;
import com.unravel.api.util.TokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TokenUtil tokenUtil;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    void testListCategories_OK() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(ApiStaticData.API_PREFIX + "/categories?page=0&size=10")
        ).andExpect(status().isOk()).andDo(result -> {
            WebResponse<List<CategoryResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
            Assertions.assertEquals(0, response.getData().size());
            Assertions.assertEquals(10, response.getPaging().getSize());
        });
    }

    @Test
    void testCreateCategory_OK() throws Exception {
        String token = tokenUtil.getAdminToken(1L, "test");

        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("Unit Test Create Category");

        mockMvc.perform(
                MockMvcRequestBuilders.post(ApiStaticData.API_PREFIX + "/categories")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk()).andDo(result -> {
            List<Category> categories = categoryRepository.findAll();
            Category category = categories.getFirst();

            Assertions.assertEquals(1, categories.size());
            Assertions.assertEquals("unit-test-create-category", category.getSlug());
            Assertions.assertEquals(1, category.getCreatedBy());
            Assertions.assertEquals(1, category.getUpdatedBy());
        });
    }

    @Test
    void testUpdateCategory_OK() throws Exception {
        String token = tokenUtil.getAdminToken(1L, "test");

        Category newCategory = new Category();
        newCategory.setName("Unit Test Update Category");
        newCategory.setSlug("unit-test-update-category");
        newCategory.setCreatedBy(1L);
        newCategory.setUpdatedBy(1L);
        Category createdCategory = categoryRepository.save(newCategory);

        Long createdCategoryId = createdCategory.getId();
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setId(createdCategoryId);
        request.setName("Unit Test Update Category");

        mockMvc.perform(
                MockMvcRequestBuilders.put(ApiStaticData.API_PREFIX + "/categories")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk()).andDo(result -> {
            List<Category> categories = categoryRepository.findAll();
            Category category = categories.getFirst();

            Assertions.assertEquals(1, categories.size());
            Assertions.assertEquals("unit-test-update-category", category.getSlug());
            Assertions.assertEquals(1, category.getCreatedBy());
            Assertions.assertEquals(1, category.getUpdatedBy());
            Assertions.assertEquals(createdCategory.getCreatedAt(), category.getCreatedAt());
            Assertions.assertNotEquals(createdCategory.getUpdatedAt(), category.getUpdatedAt());
        });
    }

    @Test
    void testDeleteCategory_OK() throws Exception {
        String token = tokenUtil.getAdminToken(1L, "test");

        Category newCategory = new Category();
        newCategory.setName("Unit Test Create Category");
        newCategory.setSlug("unit-test-create-category");
        newCategory.setCreatedBy(1L);
        newCategory.setUpdatedBy(1L);
        Category createdCategory = categoryRepository.save(newCategory);

        Assertions.assertEquals(1, categoryRepository.findAll().size());

        mockMvc.perform(
                MockMvcRequestBuilders.delete(ApiStaticData.API_PREFIX + "/categories/" + createdCategory.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", token)
        ).andExpect(status().isOk()).andDo(result -> {
            List<Category> categories = categoryRepository.findAll();
            Assertions.assertEquals(0, categories.size());
        });
    }

    @Test
    void testCreateCategory_Unauthorized() throws Exception {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("Unit Test Create Category");

        mockMvc.perform(
                MockMvcRequestBuilders.post(ApiStaticData.API_PREFIX + "/categories")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void testUpdateCategory_Unauthorized() throws Exception {
        Category newCategory = new Category();
        newCategory.setName("Unit Test Update Category");
        newCategory.setSlug("unit-test-update-category");
        newCategory.setCreatedBy(1L);
        newCategory.setUpdatedBy(1L);
        Category createdCategory = categoryRepository.save(newCategory);

        Long createdCategoryId = createdCategory.getId();
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setId(createdCategoryId);
        request.setName("Unit Test Update Category");

        mockMvc.perform(
                MockMvcRequestBuilders.put(ApiStaticData.API_PREFIX + "/categories")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void testDeleteCategory_Unauthorized() throws Exception {
        Category newCategory = new Category();
        newCategory.setName("Unit Test Create Category");
        newCategory.setSlug("unit-test-create-category");
        newCategory.setCreatedBy(1L);
        newCategory.setUpdatedBy(1L);
        Category createdCategory = categoryRepository.save(newCategory);

        Assertions.assertEquals(1, categoryRepository.findAll().size());

        mockMvc.perform(
                MockMvcRequestBuilders.delete(ApiStaticData.API_PREFIX + "/categories/" + createdCategory.getId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized()).andDo(result -> {
            Assertions.assertEquals(1, categoryRepository.findAll().size());
        });
    }
}

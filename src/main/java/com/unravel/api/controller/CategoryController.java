package com.unravel.api.controller;

import com.unravel.api.controller.api.CategoryApi;
import com.unravel.api.entity.Admin;
import com.unravel.api.model.ListRequest;
import com.unravel.api.model.PagingResponse;
import com.unravel.api.model.WebResponse;
import com.unravel.api.model.auth.LoginRequest;
import com.unravel.api.model.category.CategoryResponse;
import com.unravel.api.model.category.CreateCategoryRequest;
import com.unravel.api.model.category.UpdateCategoryRequest;
import com.unravel.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController implements CategoryApi {

    @Autowired
    private CategoryService categoryService;

    @Override
    public WebResponse<List<CategoryResponse>> list(Integer page, Integer size) {
        ListRequest request = ListRequest.builder()
                .page(page)
                .size(size)
                .build();

        Page<CategoryResponse> categoryResponses = categoryService.list(request);
        return WebResponse.<List<CategoryResponse>>builder()
                .data(categoryResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(categoryResponses.getNumber())
                        .totalPage(categoryResponses.getTotalPages())
                        .size(categoryResponses.getSize())
                        .totalElements(categoryResponses.getTotalElements())
                        .build())
                .build();
    }

    @Override
    public WebResponse<Long> createData(Admin admin, CreateCategoryRequest request) {
        Long categoryId = categoryService.createData(request, admin);

        return WebResponse.<Long>builder().data(categoryId).build();
    }

    @Override
    public WebResponse<Long> updateData(Admin admin, UpdateCategoryRequest request) {
        Long categoryId = categoryService.updateData(request, admin);

        return WebResponse.<Long>builder().data(categoryId).build();
    }

    @Override
    public WebResponse<String> deleteData(Admin admin, Long categoryId) {
        categoryService.deleteData(categoryId);

        return WebResponse.<String>builder().data("OK").build();
    }
}

package com.unravel.api.service;

import com.unravel.api.entity.Admin;
import com.unravel.api.model.ListRequest;
import com.unravel.api.model.category.CategoryResponse;
import com.unravel.api.model.category.CreateCategoryRequest;
import com.unravel.api.model.category.UpdateCategoryRequest;
import org.springframework.data.domain.Page;

public interface CategoryService {

    Page<CategoryResponse> list(ListRequest listRequest);

    public Long createData(CreateCategoryRequest request, Admin admin);

    public Long updateData(UpdateCategoryRequest request, Admin admin);

    public void deleteData(Long id);
}

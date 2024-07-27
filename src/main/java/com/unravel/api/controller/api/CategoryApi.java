package com.unravel.api.controller.api;

import com.unravel.api.entity.Admin;
import com.unravel.api.model.WebResponse;
import com.unravel.api.model.auth.LoginRequest;
import com.unravel.api.model.category.CategoryResponse;
import com.unravel.api.model.category.CreateCategoryRequest;
import com.unravel.api.model.category.UpdateCategoryRequest;
import com.unravel.api.util.ApiStaticData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CategoryApi {

    @Operation(description = "This API will return data of place categories")
    @GetMapping(
            path = ApiStaticData.API_PREFIX + "/categories",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<List<CategoryResponse>> list(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                             @RequestParam(value = "size", required = false, defaultValue = "5") Integer size);

    @Operation(description = "This API will create new category")
    @Parameter(
            name = "admin",
            hidden = true)
    @PostMapping(
            path = ApiStaticData.API_PREFIX + "/categories",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<Long> createData(Admin admin, @RequestBody CreateCategoryRequest request);

    @Operation(description = "This API will update category")
    @Parameter(
            name = "admin",
            hidden = true)
    @PutMapping(
            path = ApiStaticData.API_PREFIX + "/categories",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<Long> updateData(Admin admin, @RequestBody UpdateCategoryRequest request);

    @Operation(description = "This API will delete category")
    @Parameter(
            name = "admin",
            hidden = true)
    @DeleteMapping(
            path = ApiStaticData.API_PREFIX + "/categories/{categoryId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<String> deleteData(Admin admin, @PathVariable("categoryId") Long categoryId);

}

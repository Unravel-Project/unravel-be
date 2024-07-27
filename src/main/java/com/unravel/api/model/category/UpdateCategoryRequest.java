package com.unravel.api.model.category;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateCategoryRequest extends SaveCategoryRequest {

    @NotNull
    private Long id;

}

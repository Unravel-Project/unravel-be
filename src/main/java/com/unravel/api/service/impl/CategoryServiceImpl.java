package com.unravel.api.service.impl;

import com.unravel.api.entity.Admin;
import com.unravel.api.entity.Category;
import com.unravel.api.model.ListRequest;
import com.unravel.api.model.category.CategoryResponse;
import com.unravel.api.model.category.CreateCategoryRequest;
import com.unravel.api.model.category.UpdateCategoryRequest;
import com.unravel.api.repository.CategoryRepository;
import com.unravel.api.service.CategoryService;
import com.unravel.api.service.ValidationService;
import com.unravel.api.util.CommonUtil;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CommonUtil commonUtil;

    private CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder().id(category.getId()).name(category.getName()).slug(category.getSlug()).description(category.getDescription()).build();
    }

    @Override
    public Page<CategoryResponse> list(ListRequest listRequest) {
        Specification<Category> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(listRequest.getSearch())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + listRequest.getSearch() + "%"));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        });

        Pageable pageable = PageRequest.of(listRequest.getPage(), listRequest.getSize());
        Page<Category> categories = categoryRepository.findAll(specification, pageable);

        List<CategoryResponse> categoryResponses = categories.stream().map(this::toCategoryResponse).toList();

        return new PageImpl<>(categoryResponses, pageable, categories.getTotalElements());
    }

    @Override
    public Long createData(CreateCategoryRequest request, Admin admin) {
        validationService.validate(request);

        Category category = new Category();
        category.setName(request.getName());
        category.setSlug(commonUtil.slugify(request.getName()));
        category.setCreatedBy(admin.getId());
        category.setUpdatedBy(admin.getId());

        return categoryRepository.save(category).getId();
    }

    @Override
    public Long updateData(UpdateCategoryRequest request, Admin admin) {
        validationService.validate(request);

        Category category = categoryRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));;
        category.setName(request.getName());
        category.setSlug(commonUtil.slugify(request.getName()));
        category.setUpdatedAt(Timestamp.from(Instant.now()));
        category.setCreatedBy(admin.getId());
        category.setUpdatedBy(admin.getId());

        return categoryRepository.save(category).getId();
    }

    @Override
    public void deleteData(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));;
        categoryRepository.delete(category);
    }
}

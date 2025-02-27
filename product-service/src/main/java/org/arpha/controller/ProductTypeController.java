package org.arpha.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.product.request.ModifyProductTypeRequest;
import org.arpha.dto.product.response.GetProductTypeResponse;
import org.arpha.entity.Product;
import org.arpha.service.ProductTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-types")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public GetProductTypeResponse findById(@PathVariable long id) {
        return productTypeService.findById(id);
    }

    @GetMapping
    public Page<GetProductTypeResponse> findAll(@PageableDefault Pageable pageable, @QuerydslPredicate(root = Product.class)
    Predicate predicate) {
        return productTypeService.findAll(predicate, pageable);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GetProductTypeResponse update(@PathVariable long id, @RequestBody @Valid
    ModifyProductTypeRequest modifyProductTypeRequest) {
        return productTypeService.update(id, modifyProductTypeRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        productTypeService.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public GetProductTypeResponse create(@RequestBody @Valid ModifyProductTypeRequest modifyProductTypeRequest) {
        return productTypeService.create(modifyProductTypeRequest);
    }

}

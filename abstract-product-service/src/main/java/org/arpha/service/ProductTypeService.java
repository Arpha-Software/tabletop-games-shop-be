package org.arpha.service;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.product.request.ModifyProductTypeRequest;
import org.arpha.dto.product.response.GetProductTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductTypeService {

    GetProductTypeResponse create(ModifyProductTypeRequest modifyProductTypeRequest);
    GetProductTypeResponse findById(long id);
    Page<GetProductTypeResponse> findAll(Predicate predicate, Pageable pageable);
    void delete(long id);
    GetProductTypeResponse update(long id, ModifyProductTypeRequest modifyProductTypeRequest);

}

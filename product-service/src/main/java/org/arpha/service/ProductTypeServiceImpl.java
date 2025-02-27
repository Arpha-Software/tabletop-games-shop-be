package org.arpha.service;

import com.querydsl.core.types.Predicate;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.product.request.ModifyProductTypeRequest;
import org.arpha.dto.product.response.GetProductTypeResponse;
import org.arpha.exception.CreateEntityException;
import org.arpha.mapper.ProductTypeMapper;
import org.arpha.repository.ProductTypeRepository;
import org.arpha.utils.Boxed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductTypeServiceImpl implements ProductTypeService{

    private final ProductTypeRepository productTypeRepository;
    private final ProductTypeMapper productTypeMapper;

    @Override
    public GetProductTypeResponse create(ModifyProductTypeRequest modifyProductTypeRequest) {
        return Boxed
                .of(modifyProductTypeRequest)
                .mapToBoxed(productTypeMapper::toProductType)
                .mapToBoxed(productTypeRepository::save)
                .mapToBoxed(productTypeMapper::toGetProductTypeResponse)
                .orElseThrow(() -> new CreateEntityException("Couldn't create exception because of bad request"));
    }

    @Override
    @Transactional(readOnly = true)
    public GetProductTypeResponse findById(long id) {
        return Boxed
                .of(id)
                .flatOpt(productTypeRepository::findById)
                .mapToBoxed(productTypeMapper::toGetProductTypeResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product type with %s id wasn't found!".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GetProductTypeResponse> findAll(Predicate predicate, Pageable pageable) {
        return productTypeRepository.findAll(predicate, pageable).map(productTypeMapper::toGetProductTypeResponse);
    }

    @Override
    public void delete(long id) {
        Boxed
                .of(id)
                .ifPresent(productTypeRepository::deleteById);
    }

    @Override
    public GetProductTypeResponse update(long id, ModifyProductTypeRequest modifyProductTypeRequest) {
        return Boxed
                .of(id)
                .flatOpt(productTypeRepository::findById)
                .doWith(productType -> productTypeMapper.update(productType, modifyProductTypeRequest))
                .mapToBoxed(productTypeRepository::save)
                .mapToBoxed(productTypeMapper::toGetProductTypeResponse)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't create exception because of bad request"));
    }
}

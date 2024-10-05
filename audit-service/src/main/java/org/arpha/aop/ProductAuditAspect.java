package org.arpha.aop;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.audit.TargetType;
import org.arpha.dto.product.CategoryDto;
import org.arpha.dto.product.GenreDto;
import org.arpha.dto.product.response.ProductResponse;
import org.arpha.service.AuditService;
import org.arpha.utills.AspectUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static org.arpha.dto.audit.Action.ADD_CATEGORY_TO_PRODUCT;
import static org.arpha.dto.audit.Action.ADD_GENRE_TO_PRODUCT;
import static org.arpha.dto.audit.Action.CREATE_CATEGORY;
import static org.arpha.dto.audit.Action.CREATE_GENRE;
import static org.arpha.dto.audit.Action.CREATE_PRODUCT;
import static org.arpha.dto.audit.Action.DELETE_CATEGORY_BY_ID;
import static org.arpha.dto.audit.Action.DELETE_GENRE_BY_ID;
import static org.arpha.dto.audit.Action.DELETE_PRODUCT_BY_ID;

@Component
@Aspect
@RequiredArgsConstructor
public class ProductAuditAspect {

    private final AuditService auditService;

    @AfterReturning(
            value = "execution(public org.arpha.dto.product.CategoryDto createCategory(org.arpha.dto.product.CategoryDto))",
            argNames = "categoryDto",
            returning = "categoryDto")
    public void createCategoryAdvice(CategoryDto categoryDto) {
        auditService.saveAudit(CREATE_CATEGORY, categoryDto.getId(), AspectUtils.getAuthenticatedUserId(), TargetType.CATEGORY);
    }

    @AfterReturning(
            value = "execution(public void deleteCategory(long)) && args(id)",
            argNames = "id")
    public void deleteCategoryByIdAdvice(long id) {
        auditService.saveAudit(DELETE_CATEGORY_BY_ID, id, AspectUtils.getAuthenticatedUserId(), TargetType.CATEGORY);
    }

    @AfterReturning(
            value = "execution(public org.arpha.dto.product.GenreDto createGenre(org.arpha.dto.product.GenreDto))",
            argNames = "genreDto",
            returning = "genreDto")
    public void createGenreAdvice(GenreDto genreDto) {
        auditService.saveAudit(CREATE_GENRE, genreDto.getId(), AspectUtils.getAuthenticatedUserId(), TargetType.GENRE);
    }

    @AfterReturning(
            value = "execution(public void deleteGenre(long)) && args(id)",
            argNames = "id")
    public void deleteGenreByIdAdvice(long id) {
        auditService.saveAudit(DELETE_GENRE_BY_ID, id, AspectUtils.getAuthenticatedUserId(), TargetType.GENRE);
    }

    @AfterReturning(
            value = "execution(public org.arpha.dto.product.response.ProductResponse createProduct(org.arpha.dto.product.request.CreateProductRequest))",
            argNames = "createProductRequest",
            returning = "createProductRequest")
    public void createProductAdvice(ProductResponse createProductRequest) {
        auditService.saveAudit(CREATE_PRODUCT, createProductRequest.getId(), AspectUtils.getAuthenticatedUserId(), TargetType.PRODUCT);
    }

    @AfterReturning(
            value = "execution(public void deleteProduct(long)) && args(id)",
            argNames = "id")
    public void deleteProductByIdAdvice(long id) {
        auditService.saveAudit(DELETE_PRODUCT_BY_ID, id, AspectUtils.getAuthenticatedUserId(), TargetType.PRODUCT);
    }

    @AfterReturning(
            value = "execution(public org.arpha.dto.product.response.ProductResponse addGenre(long, java.util.Set))",
            argNames = "productResponse",
            returning = "productResponse")
    public void addGenreToProductAdvice(ProductResponse productResponse) {
        auditService.saveAudit(ADD_GENRE_TO_PRODUCT, productResponse.getId(), AspectUtils.getAuthenticatedUserId(), TargetType.PRODUCT);
    }

    @AfterReturning(
            value = "execution(public org.arpha.dto.product.response.ProductResponse addCategory(long, java.util.Set))",
            argNames = "productResponse",
            returning = "productResponse")
    public void addCategoryToProductAdvice(ProductResponse productResponse) {
        auditService.saveAudit(ADD_CATEGORY_TO_PRODUCT, productResponse.getId(), AspectUtils.getAuthenticatedUserId(), TargetType.PRODUCT);
    }

}

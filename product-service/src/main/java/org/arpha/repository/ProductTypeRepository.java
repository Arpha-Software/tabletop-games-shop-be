package org.arpha.repository;

import org.arpha.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long>, QuerydslPredicateExecutor<ProductType> {
}

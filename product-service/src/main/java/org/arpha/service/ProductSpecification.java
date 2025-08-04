package org.arpha.service;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.arpha.dto.product.request.ProductFilterRequest;
import org.arpha.entity.Category;
import org.arpha.entity.Genre;
import org.arpha.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductSpecification {

    public Specification<Product> from(ProductFilterRequest filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }

            if (filter.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }
            if (filter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            if (filter.getMinPlayers() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("minPlayerNumber"), filter.getMinPlayers()));
            }
            if (filter.getMaxPlayers() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxPlayerNumber"), filter.getMaxPlayers()));
            }

            if (filter.getMinAge() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("minAge"), filter.getMinAge()));
            }

            if (!CollectionUtils.isEmpty(filter.getCategories())) {
                Join<Product, Category> categoryJoin = root.join("categories", JoinType.INNER);
                predicates.add(categoryJoin.get("name").in(filter.getCategories()));
            }

            if (!CollectionUtils.isEmpty(filter.getGenres())) {
                Join<Product, Genre> genreJoin = root.join("genres", JoinType.INNER);
                predicates.add(genreJoin.get("name").in(filter.getGenres()));
            }

            if (!CollectionUtils.isEmpty(filter.getMechanics())) {
                predicates.add(root.join("mechanics").in(filter.getMechanics()));
            }

            if (filter.getAuthor() != null && !filter.getAuthor().isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + filter.getAuthor().toLowerCase() + "%"));
            }

            if (filter.getPublisher() != null && !filter.getPublisher().isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("publisher")), "%" + filter.getPublisher().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

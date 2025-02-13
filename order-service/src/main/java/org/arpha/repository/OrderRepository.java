package org.arpha.repository;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.order.response.OrderDetailsResponse;
import org.arpha.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {

    Page<Order> findAllByUserId(long userId, Predicate predicate, Pageable pageable);
}

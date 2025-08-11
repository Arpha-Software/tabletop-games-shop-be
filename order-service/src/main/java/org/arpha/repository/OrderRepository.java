package org.arpha.repository;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.order.enums.OrderStatus;
import org.arpha.dto.order.response.OrderDetailsResponse;
import org.arpha.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {

    Page<Order> findAllByUserId(long userId, Predicate predicate, Pageable pageable);
    Page<Order> findAllByUserId(long userId, Pageable pageable);
    List<Order> findAllByUserId(long userId, Sort sort);
    List<Order> findAllByOrderStatusIsIn(Collection<OrderStatus> orderStatuses);

    @Query("SELECT o.id FROM Order o WHERE o.user IS NULL AND " +
            "((:email IS NOT NULL AND LOWER(o.customerDetails.email) = LOWER(:email)) OR " +
            "(:phone IS NOT NULL AND o.customerDetails.phoneNumber = :phone) OR " +
            "(:firstName IS NOT NULL AND :lastName IS NOT NULL AND LOWER(o.customerDetails.firstName) = LOWER(:firstName) AND LOWER(o.customerDetails.lastName) = LOWER(:lastName)))")
    List<Long> findGuestOrderIds(@Param("email") String email, @Param("phone") String phone, @Param("firstName") String firstName, @Param("lastName") String lastName);

    @Modifying
    @Query("UPDATE Order o SET o.user.id = :userId WHERE o.id IN :orderIds")
    void assignUserToOrders(@Param("orderIds") List<Long> orderIds, @Param("userId") long userId);

}

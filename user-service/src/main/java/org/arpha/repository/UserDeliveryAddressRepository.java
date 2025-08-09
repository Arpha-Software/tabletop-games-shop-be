package org.arpha.repository;

import org.arpha.entity.UserDeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDeliveryAddressRepository extends JpaRepository<UserDeliveryAddress, Long> {

    List<UserDeliveryAddress> findByUserId(long userId);
}
package org.arpha.repository;

import org.arpha.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByUserEmail(String email);
    Optional<Wishlist> findByShareableLink(String shareableLink);
}
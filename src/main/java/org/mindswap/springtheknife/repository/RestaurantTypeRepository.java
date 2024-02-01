package org.mindswap.springtheknife.repository;

import jakarta.transaction.Transactional;
import org.mindswap.springtheknife.model.RestaurantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RestaurantTypeRepository extends JpaRepository<RestaurantType, Long> {
    Optional<RestaurantType> findByType(String type);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE restaurant_type AUTO_INCREMENT = 1", nativeQuery = true)
    default void resetId() {
    }
}

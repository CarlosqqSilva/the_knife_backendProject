package org.mindswap.springtheknife.repository;

import org.mindswap.springtheknife.model.RestaurantType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantTypeRepository extends JpaRepository<RestaurantType, Long> {
    Optional<RestaurantType> findByType(String type);
}

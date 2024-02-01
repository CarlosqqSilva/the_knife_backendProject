package org.mindswap.springtheknife.repository;

import org.mindswap.springtheknife.model.RestaurantType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantTypeRepository extends JpaRepository<RestaurantType, Long> {
    Optional<RestaurantType> findByType(String type);

    Page<RestaurantType> findAll(Pageable pageable);
}

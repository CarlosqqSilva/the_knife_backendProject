package org.mindswap.springtheknife.repository;

import org.mindswap.springtheknife.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByEmail(String email);
    Optional<Restaurant> findByPhoneNumber(String phoneNumber);

    Page<Restaurant> findAll(Pageable pageable);
}

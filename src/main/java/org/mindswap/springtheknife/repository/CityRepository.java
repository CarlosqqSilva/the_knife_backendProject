package org.mindswap.springtheknife.repository;

import org.mindswap.springtheknife.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

    Optional<City> findByName(String cityName);
}
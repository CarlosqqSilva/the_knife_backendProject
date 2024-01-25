package org.mindswap.springtheknife.repository;

import org.mindswap.springtheknife.model.UserExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserExperienceRepository extends JpaRepository<UserExperience, Long> {

    List<UserExperience> findAll();

    Optional<UserExperience> findById(Long id);

}

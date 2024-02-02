package org.mindswap.springtheknife.repository;

import org.mindswap.springtheknife.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

  //  @Query("SELECT s FROM User s WHERE s.userName = ?1")
    Optional<User> findByUserName(String username);

    Optional<User> findByEmail(String email);
}

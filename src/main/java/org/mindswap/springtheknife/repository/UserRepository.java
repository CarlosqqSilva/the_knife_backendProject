package org.mindswap.springtheknife.repository;


import org.mindswap.springtheknife.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findById(Long id);

  //  @Query("SELECT s FROM User s WHERE s.userName = ?1")
    Optional<User> findByUserName(String username);

    Optional<User> findByEmail(String email);

    Page<User> findAll(Pageable pageable);
}

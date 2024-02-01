package org.mindswap.springtheknife.repository;
import jakarta.transaction.Transactional;
import org.mindswap.springtheknife.model.Booking;
import org.mindswap.springtheknife.model.UserExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAll();

    Optional<Booking> findByBookingTime(LocalDateTime localDateTime);
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE booking AUTO_INCREMENT = 1", nativeQuery = true)
    default void resetId() {
    }
}

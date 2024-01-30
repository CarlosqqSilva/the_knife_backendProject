package org.mindswap.springtheknife.repository;
import org.mindswap.springtheknife.model.Booking;
import org.mindswap.springtheknife.model.UserExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAll();

    Optional<Booking> findByBookingTime(LocalDateTime localDateTime);

}

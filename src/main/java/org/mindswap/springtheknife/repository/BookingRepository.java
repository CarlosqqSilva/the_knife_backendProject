package org.mindswap.springtheknife.repository;
import org.mindswap.springtheknife.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingTime(LocalDateTime localDateTime);

}

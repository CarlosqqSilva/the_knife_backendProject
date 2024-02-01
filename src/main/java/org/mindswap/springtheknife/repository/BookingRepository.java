package org.mindswap.springtheknife.repository;
import org.mindswap.springtheknife.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;




@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findAll(Pageable pageable);
    Optional<Booking> findByBookingTime(LocalDateTime localDateTime);

}

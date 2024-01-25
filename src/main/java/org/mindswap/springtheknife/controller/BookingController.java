package org.mindswap.springtheknife.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.mindswap.springtheknife.dto.BookingCreateDto;
import org.mindswap.springtheknife.dto.BookingGetDto;
import org.mindswap.springtheknife.dto.BookingPatchDto;
import org.mindswap.springtheknife.exceptions.BookingAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.BookingNotFoundException;
import org.mindswap.springtheknife.model.Booking;
import org.mindswap.springtheknife.service.BookingService;
import org.mindswap.springtheknife.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/bookings")
public class BookingController {
   BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Get all Bookings", description = "This method returns a list of all Bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Booking",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class))}),})
    @GetMapping("/")
    public ResponseEntity<List<BookingGetDto>> getBooking() {
        return new ResponseEntity<>(bookingService.getBooking(), HttpStatus.OK);
    }

    @Operation(summary = "Get a Booking by id", description = "This method returns a booking by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Booking",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class))}),
            @ApiResponse(responseCode = "400", description = "Booking id not found",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookingGetDto> getBookingById (@PathVariable("id") Long id) throws BookingNotFoundException {
        return new ResponseEntity<>(bookingService.getById(id), HttpStatus.OK);
    }
    @Operation(summary = "Create a new Booking", description = "This method creates a new Booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New Booking created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class))}),
            @ApiResponse(responseCode = "400", description = "Verify the Booking details",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<BookingGetDto> addBooking(@Valid @RequestBody BookingCreateDto booking) throws BookingAlreadyExistsException {
        return new ResponseEntity<>(bookingService.addBooking(booking), HttpStatus.OK);
    }

    @Operation(summary = "Update a Booking", description = "Updates a Booking in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Booking property already exists")})
    @PatchMapping("/{id}")
    public ResponseEntity<BookingGetDto> patchBooking (@PathVariable("id") Long id, @Valid @RequestBody BookingPatchDto booking) throws BookingNotFoundException {
        return new ResponseEntity<>(bookingService.patchBooking(id, booking), HttpStatus.OK);
    }
    @Operation(summary = "Delete a Booking", description = "Deletes a Booking from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Booking id not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking (@PathVariable("id") Long id) throws BookingNotFoundException {
        bookingService.deleteBooking(id);
        return new ResponseEntity<>(Message.BOOKING_ID + id + Message.DELETE_SUCCESSFULLY, HttpStatus.OK);
    }
}
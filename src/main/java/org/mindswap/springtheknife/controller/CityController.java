package org.mindswap.springtheknife.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.mindswap.springtheknife.dto.city.CityDto;
import org.mindswap.springtheknife.dto.city.CityGetDto;
import org.mindswap.springtheknife.exceptions.city.CityNotFoundException;
import org.mindswap.springtheknife.exceptions.city.CityAlreadyExistsException;
import org.mindswap.springtheknife.model.City;
import org.mindswap.springtheknife.service.city.CityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

;

@RestController
@RequestMapping("api/v1/cities")
public class CityController {

    private final CityServiceImpl cityServiceImpl;

    @Autowired
    public CityController(CityServiceImpl cityServiceImpl) {
        this.cityServiceImpl = cityServiceImpl;
    }

    @Operation(summary = "Get all cities", description = "Returns a list of add clities")
    @ApiResponse(responseCode = "200", description = "Return successfully completed")
    @GetMapping("/")
    public ResponseEntity<List<CityGetDto>> getAllCities(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy
    ) throws Exception {
        return new ResponseEntity<>(cityServiceImpl.getAllCities(pageNumber, pageSize, sortBy), HttpStatus.OK);
    }

    @Operation(summary = "Get a city by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the city",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = City.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = @Content) })
    @GetMapping("/{cityId}")
    public ResponseEntity<CityGetDto> getCity (@PathVariable("cityId") Long cityId) throws CityNotFoundException {
        return new ResponseEntity<>(cityServiceImpl.getCity(cityId), HttpStatus.OK);
    }

    @Operation(summary = "Add a city", description = "Adds a city to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addition successfully completed"),
            @ApiResponse(responseCode = "400", description = "City already exists")})
    @PostMapping("/")
    public ResponseEntity<CityDto> addNewCity (@Valid @RequestBody CityDto city, BindingResult bindingResult) throws CityAlreadyExistsException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CityDto city1 = cityServiceImpl.create(city);
        return new ResponseEntity<>(city1, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a city", description = "Updates a city in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "City property already exists")})
    @PatchMapping(path = "{cityId}")
    public ResponseEntity<String> updateCity (@Valid @RequestBody City city, @PathVariable @Parameter(name = "cityId", description = "city_id", example = "1") long cityId) throws CityNotFoundException {
        cityServiceImpl.update(cityId, city);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete a city", description = "Deletes a city from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "City id not found")})
    @DeleteMapping("/{cityId}")
    public ResponseEntity<String> deleteCity (@PathVariable @Parameter(name = "cityId", example = "1") long cityId) throws CityNotFoundException {
        cityServiceImpl.delete(cityId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
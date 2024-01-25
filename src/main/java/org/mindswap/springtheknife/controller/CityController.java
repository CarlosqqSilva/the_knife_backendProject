package org.mindswap.springtheknife.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.mindswap.springtheknife.dto.CityDto;
import org.mindswap.springtheknife.exceptions.CityNotFoundException;
import org.mindswap.springtheknife.exceptions.DuplicateCityException;
import org.mindswap.springtheknife.service.CityService;
import org.mindswap.springtheknife.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @Operation(summary = "Get all cities", description = "Returns a list of add clities")
    @ApiResponse(responseCode = "200", description = "Return successfully completed")
    @GetMapping("/")
    public ResponseEntity<List<City>> getCities() throws Exception {
        return new ResponseEntity<>(cityService.getCities(), HttpStatus.OK);
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
    public ResponseEntity<City> getCity (@PathVariable("cityId") Long cityId) throws CityNotFoundException {
        return new ResponseEntity<>(cityService.get(cityId), HttpStatus.OK);
    }

    @Operation(summary = "Add a city", description = "Adds a city to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addition successfully completed"),
            @ApiResponse(responseCode = "400", description = "City already exists")})
    @PostMapping("/")
    public ResponseEntity<City> addNewCity (@Valid
                                                  @RequestBody
                                            CityDto city, BindingResult bindingResult) throws DuplicateCityException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        City city1 = cityService.create(city);
        return new ResponseEntity<>(city1, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a city", description = "Updates a city in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "City property already exists")})
    @PatchMapping(path = "{cityId}")
    public ResponseEntity<String> updateCity (@Valid @RequestBody City city, @PathVariable @Parameter(name = "cityId", description = "city_id", example = "1") long cityId) throws CityNotFoundException {
        cityService.update(cityId, city);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete a city", description = "Deletes a city from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "City id not found")})
    @DeleteMapping("/{cityId}")
    public ResponseEntity<String> deleteCity (@PathVariable @Parameter(name = "cityId", example = "1") long cityId) throws CityNotFoundException {
        cityService.delete(cityId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
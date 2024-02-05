package org.mindswap.springtheknife.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.mindswap.springtheknife.dto.restaurantTypeDto.RestaurantTypeDto;
import org.mindswap.springtheknife.exceptions.restaurantType.RestaurantTypeAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.restaurantType.RestaurantTypeNotFoundException;
import org.mindswap.springtheknife.model.RestaurantType;
import org.mindswap.springtheknife.service.restauranttype.RestaurantTypeService;
import org.mindswap.springtheknife.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "api/v1/restaurantTypes")
public class RestaurantTypeController {
    RestaurantTypeService restaurantTypeService;

    @Autowired
    public RestaurantTypeController(RestaurantTypeService restaurantTypeService) {
        this.restaurantTypeService = restaurantTypeService;
    }

    @Operation(summary = "Get all RestaurantType", description = "This method returns a list of all Types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the RestaurantType",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantType.class))}),})
    @GetMapping("/")
    public ResponseEntity<List<RestaurantTypeDto>> getRestaurantType(
            @RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy") String sortBy
    ) {
        return new ResponseEntity<>(restaurantTypeService.getAllRestaurantType(pageNumber,pageSize, sortBy), HttpStatus.OK);
    }
    @Operation(summary = "Get a Type by id", description = "This method returns a Type by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the RestaurantType",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantType.class))}),
            @ApiResponse(responseCode = "400", description = "Type id not found",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTypeDto> getById (@PathVariable("id") Long id) throws RestaurantTypeNotFoundException {
        return new ResponseEntity<>(restaurantTypeService.getById(id), HttpStatus.OK);
    }
    @Operation(summary = "Create a new Type", description = "This method creates a new Type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New Type created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantType.class))}),
            @ApiResponse(responseCode = "400", description = "Verify the Type details",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<RestaurantTypeDto> addType (@Valid @RequestBody RestaurantTypeDto restaurantType) throws RestaurantTypeAlreadyExistsException {
        return new ResponseEntity<>(restaurantTypeService.addType(restaurantType), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a Type", description = "Updates a Type in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Type property already exists")})
    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantTypeDto> patchType (@PathVariable("id") Long id, @Valid @RequestBody RestaurantTypeDto restaurantType) throws RestaurantTypeNotFoundException {
        return new ResponseEntity<>(restaurantTypeService.patchType(id, restaurantType), HttpStatus.OK);
    }
    @Operation(summary = "Delete a Type", description = "Deletes a Type from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Type id not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteType (@PathVariable("id") Long id) throws RestaurantTypeNotFoundException {
        restaurantTypeService.deleteType(id);
        return new ResponseEntity<>(Message.TYPE_ID + id + Message.DELETE_SUCCESSFULLY, HttpStatus.OK);
    }
}

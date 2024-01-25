package org.mindswap.springtheknife.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.mindswap.springtheknife.dto.UserExperienceCreateDto;
import org.mindswap.springtheknife.dto.UserExperienceGetDto;
import org.mindswap.springtheknife.exceptions.RestaurantNotFoundException;
import org.mindswap.springtheknife.exceptions.UserExperienceIdAlreadyExists;
import org.mindswap.springtheknife.exceptions.UserExperienceNotFoundException;
import org.mindswap.springtheknife.exceptions.UserNotFoundException;
import org.mindswap.springtheknife.model.UserExperience;
import org.mindswap.springtheknife.service.UserExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/userExperience")
public class UserExperienceController {

    private final UserExperienceService userExperienceService;

    @Autowired
    public UserExperienceController(UserExperienceService userExperienceService) {
        this.userExperienceService = userExperienceService;
    }

    @Operation(summary = "Get all users experiences", description = "This method returns a list of all users experiences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Users",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserExperience.class))}),})
    @GetMapping("/")
    public ResponseEntity<List<UserExperienceGetDto>> getAllUsersExperiences() {
        return new ResponseEntity<>(userExperienceService.getAllUsersExperiences(),
                HttpStatus.OK);
    }

    @Operation(summary = "Get user experience by id", description = "This method returns a user experience by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Users",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserExperience.class))}),})
    @GetMapping("/{userExperienceId}")
    public ResponseEntity<UserExperienceGetDto> getUserExperienceById(@PathVariable Long id) throws UserExperienceNotFoundException {
        userExperienceService.getUserExperienceById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserExperienceGetDto> addUserExperience(@Valid @RequestBody UserExperienceCreateDto userExperienceCreateDto) throws UserExperienceIdAlreadyExists, UserNotFoundException, RestaurantNotFoundException {
        return new ResponseEntity<>(userExperienceService.addNewUserExperience(userExperienceCreateDto),
                HttpStatus.OK);
    }

}


package org.mindswap.springtheknife.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.mindswap.springtheknife.dto.userexperience.UserExperienceCreateDto;
import org.mindswap.springtheknife.dto.userexperience.UserExperienceGetDto;
import org.mindswap.springtheknife.dto.userexperience.UserExperiencePatchDto;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.exceptions.userexperience.UserExperienceNotFoundException;
import org.mindswap.springtheknife.exceptions.user.UserNotFoundException;
import org.mindswap.springtheknife.model.UserExperience;
import org.mindswap.springtheknife.service.userexperience.UserExperienceServiceImpl;
import org.mindswap.springtheknife.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/userexperiences")
public class UserExperienceController {

    private final UserExperienceServiceImpl userExperienceService;

    @Autowired
    public UserExperienceController(UserExperienceServiceImpl userExperienceService) {
        this.userExperienceService = userExperienceService;
    }

    @Operation(summary = "Get all users experiences", description = "This method returns a list of all users experiences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Users",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserExperience.class))}),})
    @GetMapping("/")
    public ResponseEntity<List<UserExperienceGetDto>> getAllUsersExperiences() {
        return new ResponseEntity<>(userExperienceService.getAllUsersExperiences(),HttpStatus.OK);
    }

    @Operation(summary = "Get user experience by id", description = "This method returns a user experience by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Users",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserExperience.class))}),})
    @GetMapping("/{userExperienceId}")
    public ResponseEntity<UserExperienceGetDto> getUserExperienceById(@PathVariable("userExperienceId") Long id) throws UserExperienceNotFoundException {
        UserExperienceGetDto userExperience = userExperienceService.getUserExperienceById(id);
        return new ResponseEntity<>(userExperience, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserExperienceGetDto> addUserExperience(@Valid @RequestBody UserExperienceCreateDto userExperienceCreateDto) throws UserNotFoundException, RestaurantNotFoundException {
        return new ResponseEntity<>(userExperienceService.addNewUserExperience(userExperienceCreateDto),
                HttpStatus.OK);
    }


    @DeleteMapping("/{userExperienceId}")
    public ResponseEntity<String> deleteUserExperience(@PathVariable("userExperienceId") Long userExperienceId)
            throws UserExperienceNotFoundException {
        userExperienceService.deleteUserExperience(userExperienceId);
        return new ResponseEntity<>(Message.USER_EXPERIENCE_ID_DELETED + userExperienceId,HttpStatus.OK);
    }

    @PatchMapping(path = "/{userExperienceId}")
    public ResponseEntity<UserExperiencePatchDto> updateUserExperience
            (@PathVariable("userExperienceId") Long userExperienceId,
             @Valid @RequestBody UserExperiencePatchDto userExperience) throws UserExperienceNotFoundException {
        userExperienceService.updateUserExperience(userExperienceId, userExperience);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


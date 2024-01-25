package org.mindswap.springtheknife.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.mindswap.springtheknife.dto.UserCreateDto;
import org.mindswap.springtheknife.dto.UserGetDto;
import org.mindswap.springtheknife.dto.UserPatchDto;
import org.mindswap.springtheknife.exceptions.UserAlreadyExists;
import org.mindswap.springtheknife.exceptions.UserEmailTaken;
import org.mindswap.springtheknife.exceptions.UserNotFoundException;
import org.mindswap.springtheknife.model.User;
import org.mindswap.springtheknife.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "This method returns a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Users",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),})
    @GetMapping("/")
    public ResponseEntity<List<UserGetDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


    @Operation(summary = "Get a user by id", description = "This method returns a users by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the User",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "User id not found",
                    content = @Content),
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserGetDto> getUser(@PathVariable("userId") Long userId) throws UserNotFoundException {
       UserGetDto user = userService.getUserById(userId);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Create a new user", description = "This method creates a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New user created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Verify the user details",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<UserCreateDto> createUser(@Valid @RequestBody UserCreateDto user)
            throws UserAlreadyExists, UserEmailTaken {
       userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Update a user", description = "Updates a user in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "User property already exists")})
    @PatchMapping("/{userId}")
    public ResponseEntity<UserPatchDto> patchUser(@Valid @PathVariable("userId") Long id,
                                                  @Valid @RequestBody UserPatchDto user) throws UserNotFoundException {
        userService.updateUser(id, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete a user", description = "Deletes a user from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User id not found")})
    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<User> deleteUser(@Valid @PathVariable("userId") Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
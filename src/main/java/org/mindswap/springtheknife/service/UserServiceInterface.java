package org.mindswap.springtheknife.service;

import org.mindswap.springtheknife.dto.UserCreateDto;
import org.mindswap.springtheknife.dto.UserGetDto;
import org.mindswap.springtheknife.dto.UserPatchDto;
import org.mindswap.springtheknife.exceptions.UserAlreadyExists;
import org.mindswap.springtheknife.exceptions.UserEmailTaken;
import org.mindswap.springtheknife.exceptions.UserNotFoundException;
import org.mindswap.springtheknife.model.User;

import java.util.List;

public interface UserServiceInterface {

    List<UserGetDto> getAllUsers();

    UserGetDto getUser(Long id) throws UserNotFoundException;


    UserCreateDto createUser(UserCreateDto user) throws UserNotFoundException, UserAlreadyExists, UserEmailTaken;

    UserPatchDto updateUser(Long id, UserPatchDto user) throws UserNotFoundException;

    void deleteUser(Long id) throws UserNotFoundException;
}

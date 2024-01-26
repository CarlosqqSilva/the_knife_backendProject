package org.mindswap.springtheknife.service.user;

import org.mindswap.springtheknife.dto.user.UserCreateDto;
import org.mindswap.springtheknife.dto.user.UserGetDto;
import org.mindswap.springtheknife.dto.user.UserPatchDto;
import org.mindswap.springtheknife.exceptions.user.UserAlreadyExists;
import org.mindswap.springtheknife.exceptions.user.UserEmailTaken;
import org.mindswap.springtheknife.exceptions.user.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<UserGetDto> getAllUsers();

    UserGetDto getUserById(Long id) throws UserNotFoundException;


    UserCreateDto createUser(UserCreateDto user) throws UserNotFoundException, UserAlreadyExists, UserEmailTaken;

    UserPatchDto updateUser(Long id, UserPatchDto user) throws UserNotFoundException;

    void deleteUser(Long id) throws UserNotFoundException;


}


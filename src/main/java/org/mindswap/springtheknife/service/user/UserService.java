package org.mindswap.springtheknife.service.user;

import org.mindswap.springtheknife.dto.user.UserCreateDto;
import org.mindswap.springtheknife.dto.user.UserGetDto;
import org.mindswap.springtheknife.dto.user.UserPatchDto;
import org.mindswap.springtheknife.exceptions.user.UserAlreadyExists;
import org.mindswap.springtheknife.exceptions.user.UserEmailTaken;
import org.mindswap.springtheknife.exceptions.user.UserNotFoundException;
import org.mindswap.springtheknife.model.User;

import java.util.List;

public interface UserService {

    List<UserGetDto> getAllUsers();

    UserGetDto getUser(Long id) throws UserNotFoundException;


   // UserGetDto getUserById(Long id) throws UserNotFoundException;

    UserGetDto createUser(UserCreateDto user) throws UserNotFoundException, UserAlreadyExists, UserEmailTaken;

    UserPatchDto updateUser(Long id, UserPatchDto user) throws UserNotFoundException, UserAlreadyExists;

    void deleteUser(Long id) throws UserNotFoundException;


}



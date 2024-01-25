package org.mindswap.springtheknife.service;

import org.mindswap.springtheknife.converter.UserConverter;
import org.mindswap.springtheknife.dto.UserCreateDto;
import org.mindswap.springtheknife.dto.UserGetDto;
import org.mindswap.springtheknife.dto.UserPatchDto;
import org.mindswap.springtheknife.exceptions.UserAlreadyExists;
import org.mindswap.springtheknife.exceptions.UserEmailTaken;
import org.mindswap.springtheknife.exceptions.UserNotFoundException;
import org.mindswap.springtheknife.model.User;
import org.mindswap.springtheknife.repository.UserRepository;
import org.mindswap.springtheknife.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserGetDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
             .map(UserConverter::fromEntityToGetDto)
             .toList();
   }

    @Override
    public UserGetDto getUserById(Long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(id + Message.USER_ID_DOES_NOT_EXIST);
        }
       User user = userOptional.get();
       return UserConverter.fromEntityToGetDto(user);

    }


    @Override
    public UserCreateDto createUser(UserCreateDto user) throws UserAlreadyExists, UserEmailTaken {
        Optional<User> userOptional = this.userRepository.findByUserName(user.userName());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExists(Message.USER_ID_ALREADY_EXISTS);
        }
        if (userRepository.findByEmail(user.email()).isPresent()) {
            throw new UserEmailTaken(user.email() + Message.EMAIL_TAKEN);
        }
        User userToAdd =UserConverter.fromCreateDtoToEntity(user);

        userRepository.save(userToAdd);
       return user;

    }

    @Override
    public UserPatchDto updateUser(Long id, UserPatchDto user) throws UserNotFoundException {
        Optional<User> userOptional= userRepository.findById(id);
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException(id + Message.USER_ID_DOES_NOT_EXIST);
        }
    User userToUpdate = userOptional.get();
        if (user.userName()!= null &&!user.userName().isEmpty() &&!user.userName().equals(userToUpdate)){
            userToUpdate.setUserName(user.userName());
        }
        if (user.email()!= null &&!user.email().isEmpty() &&!user.email().equals(userToUpdate)){
            userToUpdate.setEmail(user.email());
        }

        return UserConverter.fromEntityToPatchDto(userRepository.save(userToUpdate));
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id + Message.USER_ID_DOES_NOT_EXIST));
        userRepository.deleteById(id);
    }

}



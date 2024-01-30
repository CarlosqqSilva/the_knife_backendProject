package org.mindswap.springtheknife.service.user;

import org.mindswap.springtheknife.converter.UserConverter;
import org.mindswap.springtheknife.dto.user.UserCreateDto;
import org.mindswap.springtheknife.dto.user.UserGetDto;
import org.mindswap.springtheknife.dto.user.UserPatchDto;
import org.mindswap.springtheknife.exceptions.user.UserAlreadyExists;
import org.mindswap.springtheknife.exceptions.user.UserEmailTaken;
import org.mindswap.springtheknife.exceptions.user.UserNotFoundException;
import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.model.User;
import org.mindswap.springtheknife.repository.RestaurantRepository;
import org.mindswap.springtheknife.repository.UserRepository;
import org.mindswap.springtheknife.service.restaurant.RestaurantService;
import org.mindswap.springtheknife.service.restaurant.RestaurantServiceImpl;
import org.mindswap.springtheknife.service.restauranttype.RestaurantTypeImpl;
import org.mindswap.springtheknife.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RestaurantRepository restaurantService) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantService;
    }

    @Override
    public List<UserGetDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
             .map(UserConverter::fromEntityToGetDto)
             .toList();
   }

    @Override
    public UserGetDto getUser(Long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(id + Message.USER_ID_DOES_NOT_EXIST);
        }
       return UserConverter.fromEntityToGetDto(userOptional.get());

    }

    public User getUserById(Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(Message.USER_ID_DOES_NOT_EXIST + userId + Message.NOT_EXIST);
        }
        return userOptional.get();
    }
    @Override
    public UserGetDto createUser(UserCreateDto user) throws UserAlreadyExists, UserEmailTaken {
        Optional<User> userOptional = this.userRepository.findByUserName(user.userName());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExists(Message.USER_ID_ALREADY_EXISTS);
        }
        if (userRepository.findByEmail(user.email()).isPresent()) {
            throw new UserEmailTaken(user.email() + Message.EMAIL_TAKEN);
        }
        Set<Restaurant> favorites = user.favoriteRestaurants().stream().map(restaurantRepository::findById).filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toSet());
        User newUser = userRepository.save(UserConverter.fromCreateDtoToEntity(user, favorites));

        return UserConverter.fromEntityToGetDto(newUser);


    }

    @Override
    public UserPatchDto updateUser(Long id, UserPatchDto user) throws UserNotFoundException, UserAlreadyExists {
        Optional<User> userOptional= userRepository.findById(id);
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException(id + Message.USER_ID_DOES_NOT_EXIST);
        }
    User userToUpdate = userOptional.get();
        if(user.userName().equals(userToUpdate.getUserName())) {
            throw new UserAlreadyExists(Message.USER_NAME_ALREADY_INSERTED);
        }

        if (user.userName()!= null &&!user.userName().isEmpty() &&!user.userName().equals(userToUpdate.getUserName())){
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



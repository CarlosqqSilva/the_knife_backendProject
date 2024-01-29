package org.mindswap.springtheknife.service.userexperience;

import org.mindswap.springtheknife.converter.UserExperienceConverter;
import org.mindswap.springtheknife.dto.userexperience.UserExperienceCreateDto;
import org.mindswap.springtheknife.dto.userexperience.UserExperienceGetDto;
import org.mindswap.springtheknife.dto.userexperience.UserExperiencePatchDto;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.exceptions.user.UserNotFoundException;
import org.mindswap.springtheknife.exceptions.userexperience.UserExperienceNotFoundException;
import org.mindswap.springtheknife.model.UserExperience;
import org.mindswap.springtheknife.repository.UserExperienceRepository;
import org.mindswap.springtheknife.service.restaurant.RestaurantServiceImpl;
import org.mindswap.springtheknife.service.user.UserServiceImpl;
import org.mindswap.springtheknife.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserExperienceServiceImpl implements UserExperienceService {

    private final UserExperienceRepository userExperienceRepository;

    private final UserServiceImpl userServiceImpl;

    private final RestaurantServiceImpl restaurantServiceImpl;

    @Autowired
    public UserExperienceServiceImpl(UserExperienceRepository userExperienceRepository, UserServiceImpl userService, UserServiceImpl userServiceImpl, RestaurantServiceImpl restaurantService, RestaurantServiceImpl restaurantServiceImpl) {
        this.userExperienceRepository = userExperienceRepository;
        this.userServiceImpl = userServiceImpl;
        this.restaurantServiceImpl = restaurantServiceImpl;

    }

    @Override
    public List<UserExperienceGetDto> getAllUsersExperiences() {
        List<UserExperience> userExperiences = userExperienceRepository.findAll();
        return userExperiences.stream()
                .map(UserExperienceConverter::fromEntityToGetDto)
                .toList();
    }

    @Override
    public UserExperienceGetDto getUserExperienceById(Long id) throws UserExperienceNotFoundException {
        Optional<UserExperience> userExperienceOptional = userExperienceRepository.findById(id);
        if (userExperienceOptional.isEmpty()) {
            throw new UserExperienceNotFoundException(id + Message.USER_EXPERIENCE_ID_NOT_FOUND);
        }
        UserExperience userExperience = userExperienceOptional.get();
        return UserExperienceConverter.fromEntityToGetDto(userExperience);
    }

    @Override
    public UserExperienceGetDto addNewUserExperience(UserExperienceCreateDto userExperience) throws UserNotFoundException, RestaurantNotFoundException {
       /* User newUser = UserConverter.fromGetDtoToEntity(userService.getUserById(userExperience.userId()));
        Restaurant newRestaurant = RestaurantConverter.fromRestaurantDtoToModel(restaurantService.getById(userExperience.restaurantId()));
        UserExperience userExperienceEntity = UserExperienceConverter.fromUserExperienceCreateDtoToEntity(userExperience, newUser, newRestaurant);
        UserExperience userExperienceSaved = userExperienceRepository.save(userExperienceEntity);
        return UserExperienceConverter.fromEntityToGetDto(userExperienceSaved);*/
        UserExperience userExperienceToSave = UserExperienceConverter.fromUserExperienceCreateDtoToEntity
                (userExperience, userServiceImpl.getUserById(userExperience.userId()),
                        restaurantServiceImpl.getById(userExperience.restaurantId()));
        userExperienceRepository.save(userExperienceToSave);
        return UserExperienceConverter.fromEntityToGetDto(userExperienceToSave);
    }

    @Override
    public UserExperiencePatchDto updateUserExperience(Long id, UserExperiencePatchDto userExperience) throws UserExperienceNotFoundException {
        Optional<UserExperience> userExperienceOptional = userExperienceRepository.findById(id);
        if (!userExperienceOptional.isPresent()) {
            throw new UserExperienceNotFoundException(id + Message.USER_EXPERIENCE_ID_NOT_FOUND);
        }
        UserExperience userExperienceToUpdate = userExperienceOptional.get();
        if (userExperience.rating() > 0 && userExperience.rating() != (userExperienceToUpdate.getRating())) {
            userExperienceToUpdate.setRating(userExperience.rating());
        }
        if (userExperience.comment() != null && !userExperience.comment().equals(userExperienceToUpdate.getComment())) {
            userExperienceToUpdate.setComment(userExperience.comment());
        }
        return UserExperienceConverter.fromEntityToPatchDto(userExperienceRepository.save(userExperienceToUpdate));
    }

    @Override
    public void deleteUserExperience(Long userExperienceId) throws UserExperienceNotFoundException {
        userExperienceRepository.findById(userExperienceId).orElseThrow(() -> new UserExperienceNotFoundException(userExperienceId + Message.USER_EXPERIENCE_ID_NOT_FOUND));
        userExperienceRepository.deleteById(userExperienceId);
    }

}


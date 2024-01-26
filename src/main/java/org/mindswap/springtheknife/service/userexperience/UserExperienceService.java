package org.mindswap.springtheknife.service.userexperience;

import org.mindswap.springtheknife.dto.userexperience.UserExperienceCreateDto;
import org.mindswap.springtheknife.dto.userexperience.UserExperienceGetDto;
import org.mindswap.springtheknife.dto.userexperience.UserExperiencePatchDto;
import org.mindswap.springtheknife.exceptions.restaurant.RestaurantNotFoundException;
import org.mindswap.springtheknife.exceptions.userexperience.UserExperienceNotFoundException;
import org.mindswap.springtheknife.exceptions.user.UserNotFoundException;

import java.util.List;

public interface UserExperienceService {
    List<UserExperienceGetDto> getAllUsersExperiences();

    UserExperienceGetDto getUserExperienceById(Long id) throws UserExperienceNotFoundException;


    UserExperienceGetDto addNewUserExperience(UserExperienceCreateDto userExperience) throws UserNotFoundException, RestaurantNotFoundException, UserExperienceNotFoundException;


    UserExperiencePatchDto updateUserExperience(Long id, UserExperiencePatchDto userExperience) throws UserExperienceNotFoundException;
}


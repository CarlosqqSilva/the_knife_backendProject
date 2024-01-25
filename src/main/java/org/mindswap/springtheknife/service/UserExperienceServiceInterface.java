package org.mindswap.springtheknife.service;

import org.mindswap.springtheknife.dto.UserExperienceCreateDto;
import org.mindswap.springtheknife.dto.UserExperienceGetDto;
import org.mindswap.springtheknife.exceptions.RestaurantNotFoundException;
import org.mindswap.springtheknife.exceptions.UserExperienceIdAlreadyExists;
import org.mindswap.springtheknife.exceptions.UserExperienceNotFoundException;
import org.mindswap.springtheknife.exceptions.UserNotFoundException;
import org.mindswap.springtheknife.model.UserExperience;

import java.util.List;

public interface UserExperienceServiceInterface {
    List<UserExperienceGetDto> getAllUsersExperiences();

    UserExperienceGetDto getUserExperienceById(Long id) throws UserExperienceNotFoundException;



    UserExperienceGetDto addNewUserExperience(UserExperienceCreateDto userExperience) throws UserNotFoundException, RestaurantNotFoundException, UserExperienceNotFoundException;
}


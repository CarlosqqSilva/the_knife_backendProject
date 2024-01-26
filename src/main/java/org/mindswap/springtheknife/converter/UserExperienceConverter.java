package org.mindswap.springtheknife.converter;


import org.mindswap.springtheknife.dto.userexperience.UserExperienceCreateDto;
import org.mindswap.springtheknife.dto.userexperience.UserExperienceGetDto;
import org.mindswap.springtheknife.dto.userexperience.UserExperiencePatchDto;
import org.mindswap.springtheknife.model.Restaurant;
import org.mindswap.springtheknife.model.User;
import org.mindswap.springtheknife.model.UserExperience;

public class UserExperienceConverter {


    public static UserExperience fromUserExperienceCreateDtoToEntity(UserExperienceCreateDto userExperienceCreateDto, User user, Restaurant restaurant){
        return UserExperience.builder()
                .user(user)
                .restaurant(restaurant)
                .rating(userExperienceCreateDto.rating())
                .comment(userExperienceCreateDto.comment())
                .timestamp(userExperienceCreateDto.timestamp())
                .build();
    }


    public static UserExperienceCreateDto fromEntityToCreateDto(UserExperience userExperience) {
        return new UserExperienceCreateDto(
                userExperience.getUser().getId(),
                userExperience.getRestaurant().getId(),
                userExperience.getRating(),
                userExperience.getComment(),
                userExperience.getTimestamp()
        );
    }


    public static UserExperienceGetDto fromEntityToGetDto(UserExperience userExperience) {
        return new UserExperienceGetDto(
                userExperience.getId(),
                UserConverter.fromEntityToGetDto(userExperience.getUser()),
                RestaurantConverter.fromModeltoRestaurantDto(userExperience.getRestaurant()),
                userExperience.getRating(),
                userExperience.getComment(),
                userExperience.getTimestamp()
        );

    }


    public static UserExperiencePatchDto fromEntityToPatchDto(UserExperience userExperience) {
    return new UserExperiencePatchDto(
            userExperience.getRating(),
            userExperience.getComment()

    );


    }
}


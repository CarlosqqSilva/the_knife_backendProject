package org.mindswap.springtheknife.converter;


import org.mindswap.springtheknife.dto.user.UserCreateDto;
import org.mindswap.springtheknife.dto.user.UserGetDto;
import org.mindswap.springtheknife.dto.user.UserPatchDto;
import org.mindswap.springtheknife.model.User;

public class UserConverter {

    public static User fromCreateDtoToEntity(UserCreateDto userDto) {
        return  User.builder()
                .userName(userDto.userName())
                .password(userDto.password())
                .firstName(userDto.firstName())
                .email(userDto.email())
                .lastName(userDto.lastName())
                .dateOfBirth(userDto.dateOfBirth())
                .build();
            }


    public static UserGetDto fromEntityToGetDto(User user) {
        return new UserGetDto(
                user.getId(),

                user.getUserName()

        );
    }

    public static UserPatchDto fromEntityToPatchDto(User user) {
        return new UserPatchDto(
                user.getUserName(),
                user.getPassword(),
                user.getEmail()
        );
    }

    public static UserCreateDto fromEntityToCreateDto(User user) {
        return new UserCreateDto(
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth()

        );
    }


    public static User fromGetDtoToEntity(UserGetDto user) {
        return  User.builder()
                .id(user.userId())
                .userName(user.userName())
                .build();
    }

    public static UserGetDto fromCreateDtoToGetDto(UserCreateDto userCreateDto){
        User tempUser = fromCreateDtoToEntity(userCreateDto);
        return fromEntityToGetDto(tempUser);
    }
    }


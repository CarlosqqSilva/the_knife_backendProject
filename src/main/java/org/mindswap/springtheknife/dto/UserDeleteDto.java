package org.mindswap.springtheknife.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.mindswap.springtheknife.utils.Message;

import java.time.LocalDate;

public record UserDeleteDto (

        @NotBlank(message = Message.INVALID_USER_ID)
        Long id,
        String userName,

        String password,

        String email,

        String firstName,

        String lastName,

        LocalDate dateOfBirth
)  {

}




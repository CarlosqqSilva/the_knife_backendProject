package org.mindswap.springtheknife.dto;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.mindswap.springtheknife.utils.Message;

public record UserPatchDto(



    @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+){0,19}$", message = Message.VALID_USERNAME)
    String userName,

    @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+){0,19}$", message = Message.VALID_PASSWORD)
    String password,
    @Email(message = "Enter a valid Email")
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    String email
) {

}


package org.mindswap.springtheknife.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.mindswap.springtheknife.utils.Message;

public record UserGetDto(

    Long userId,
        @NotBlank(message = Message.USERNAME_MANDATORY)
        @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+){0,19}$", message = Message.VALID_USERNAME)
    String userName
    ){

}

package org.mindswap.springtheknife.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.mindswap.springtheknife.utils.Message;

public record UserGetDto(


        @NotBlank(message = Message.USERNAME_MANDATORY)
        @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+){0,19}$", message = Message.VALID_USERNAME)
    String userName
    ){

}
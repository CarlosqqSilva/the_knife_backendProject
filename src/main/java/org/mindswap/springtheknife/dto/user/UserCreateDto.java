package org.mindswap.springtheknife.dto.user;

import jakarta.validation.constraints.*;
import org.mindswap.springtheknife.utils.Message;

import java.time.LocalDate;

public record UserCreateDto (

     @NotBlank(message = Message.USERNAME_MANDATORY)
     @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+){0,19}$", message = Message.VALID_USERNAME)
     String userName,
     @NotBlank(message = Message.PASSWORD_MANDATORY)
     @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+){0,19}$", message = Message.VALID_PASSWORD)
     String password,
     @Email(message = Message.EMAIL_MANDATORY)
     @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
             + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = Message.VALID_EMAIL)
     String email,

     @NotBlank(message = Message.FIRSTNAME_MANDATORY)
     @Pattern(regexp = "^[a-zA-Z ]{0,25}$")
     String firstName,
     @NotBlank(message = Message.LASTNAME_MANDATORY)
     @Pattern(regexp = "^[a-zA-Z ]{0,25}$", message = Message.VALID_LASTNAME)
     String lastName,
     @NotNull(message = Message.DATE_OF_BIRTH_MANDATORY)
     @Past(message = Message.VALID_DATE_OF_BIRTH)
     LocalDate dateOfBirth
)  {

}

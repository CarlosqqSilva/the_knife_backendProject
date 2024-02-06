package org.mindswap.springtheknife.dto.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.mindswap.springtheknife.utils.Message.*;

public record UserCreateDto (

     @NotBlank(message = USERNAME_MANDATORY)
     @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+){0,19}$", message = VALID_USERNAME)
     String userName,
     @NotBlank(message = PASSWORD_MANDATORY)
     @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+){0,19}$", message = VALID_PASSWORD)
     String password,
     @Email(message = EMAIL_MANDATORY)
     @Pattern(regexp = EMAIL_VALIDATOR, message = VALID_EMAIL)
     String email,

     @NotBlank(message = FIRSTNAME_MANDATORY)
     @Pattern(regexp = "^[a-zA-Z]{1,50}$", message = VALID_FIRSTNAME)
     String firstName,
     @NotBlank(message = LASTNAME_MANDATORY)
     @Pattern(regexp = "^[a-zA-Z]{1,50}$", message = VALID_LASTNAME)
     String lastName,

     @Valid
     @NotNull(message = DATE_OF_BIRTH_MANDATORY)
     @Past(message = VALID_DATE_OF_BIRTH)
     LocalDate dateOfBirth,

     Set<Long> favoriteRestaurants
)  {

}

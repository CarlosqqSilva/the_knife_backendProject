package org.mindswap.springtheknife.dto.userexperience;

import jakarta.validation.constraints.*;
import org.mindswap.springtheknife.utils.Message;

public record UserExperiencePatchDto(


        @NotNull(message = Message.RATING_MANDATORY)
        @Min(0)
        @Max(10)
        Double rating,

        @NotBlank(message = Message.COMMENT_MANDATORY)
        @Pattern(regexp = "^[^\\n]{1,250}$", message = Message.INVALID_COMMENT)
        String comment
) {
}

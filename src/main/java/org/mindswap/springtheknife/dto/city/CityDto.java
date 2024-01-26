package org.mindswap.springtheknife.dto.city;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.mindswap.springtheknife.utils.Message;

public record CityDto(
        @Schema(example = "Porto")
        @NotNull(message = Message.NAME_ERROR)
        String name
) {
}
package org.mindswap.springtheknife.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.mindswap.springtheknife.utilities.Messages;

public record CityDto(
        @Schema(example = "Porto")
        @NotNull(message = Messages.NAME_ERROR)
        String name
) {
}
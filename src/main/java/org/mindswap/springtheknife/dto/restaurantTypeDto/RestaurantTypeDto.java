package org.mindswap.springtheknife.dto.restaurantTypeDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.mindswap.springtheknife.utils.Message;

public record RestaurantTypeDto(

        Long id,
        @Schema(example = "ItalianFood")
        @NotNull(message = Message.TYPE_MANDATORY)
        String type
) {

}
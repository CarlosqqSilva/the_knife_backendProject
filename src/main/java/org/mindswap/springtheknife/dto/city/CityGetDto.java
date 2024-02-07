package org.mindswap.springtheknife.dto.city;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.mindswap.springtheknife.dto.restaurant.RestaurantGetDto;
import org.mindswap.springtheknife.utils.Message;

import java.io.Serializable;
import java.util.List;

public record CityGetDto(

        Long id,

        @Schema(example = "Porto")
        @NotNull(message = Message.NAME_ERROR)
        String name,

        @NotEmpty
        List<RestaurantGetDto> restaurantList

) implements Serializable {
}

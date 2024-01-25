package org.mindswap.springtheknife.converter;

import org.mindswap.springtheknife.dto.city.CityDto;
import org.mindswap.springtheknife.model.City;

public class CityConverter {

    public static CityDto fromModelToCreateDto(City city) {
        return new CityDto(
                city.getName()
        );
    }

    public static City fromCreateDtoToModel (CityDto cityDto){
        return City.builder()
                .name(cityDto.name())
                .build();
    }
}

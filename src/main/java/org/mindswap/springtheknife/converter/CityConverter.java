package org.mindswap.springtheknife.converter;

import org.mindswap.springtheknife.dto.city.CityDto;
import org.mindswap.springtheknife.dto.city.CityGetDto;
import org.mindswap.springtheknife.model.City;

public class CityConverter {

    public static CityDto fromModelToCreateDto(City city) {
        return new CityDto(
                city.getName(),
                city.getRestaurants().stream().map(RestaurantConverter::fromModelToRestaurantDto).toList()
        );
    }

    public static City fromCreateDtoToModel (CityDto cityDto){
        return City.builder()
                .name(cityDto.name())
                .build();
    }

    public static CityGetDto fromModelToCityDto(City city) {
        return new CityGetDto(
                city.getCityId(),
                city.getName(),
                city.getRestaurants().stream().map(RestaurantConverter::fromModelToRestaurantDto).toList()
        );

    }


    public static CityGetDto fromCreateDtoToDto(CityDto city) {
        City tempCity = fromCreateDtoToModel(city);
        return fromModelToCityDto(tempCity);
    }
}

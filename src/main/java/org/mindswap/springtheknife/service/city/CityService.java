package org.mindswap.springtheknife.service.city;

import org.mindswap.springtheknife.dto.city.CityDto;
import org.mindswap.springtheknife.exceptions.city.CityNotFoundException;
import org.mindswap.springtheknife.exceptions.city.DuplicateCityException;
import org.mindswap.springtheknife.model.City;

import java.util.List;

public interface CityService {
    List<City> getCities() throws Exception;

    City get(Long cityId) throws CityNotFoundException;

    City create(CityDto city) throws DuplicateCityException;

    void update(long cityId, City city) throws CityNotFoundException;

    void delete(long cityId) throws CityNotFoundException;

}

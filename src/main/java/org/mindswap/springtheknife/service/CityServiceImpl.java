package org.mindswap.springtheknife.service;

import jakarta.validation.Valid;
import org.mindswap.springtheknife.converter.CityConverter;
import org.mindswap.springtheknife.dto.CityDto;
import org.mindswap.springtheknife.exceptions.CityNotFoundException;
import org.mindswap.springtheknife.exceptions.DuplicateCityException;
import org.mindswap.springtheknife.model.City;
import org.mindswap.springtheknife.repository.CityRepository;
import org.mindswap.springtheknife.utilities.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl (CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public City create(CityDto city) throws DuplicateCityException {
        Optional<City> cityOptional = this.cityRepository.findByName(city.name());
        if(cityOptional.isPresent())
            throw new DuplicateCityException(Messages.DUPLICATE_NAME + city.name() + Messages.EXIST);
        @Valid City newCity = CityConverter.fromCreateDtoToModel(city);
        return cityRepository.save(newCity);

    }

    @Override
    public void update(long cityId, City city) throws CityNotFoundException {
        Optional<City> cityOptional = cityRepository.findById(cityId);
        if (cityOptional.isEmpty()) {
            throw new CityNotFoundException(Messages.CITY_WITH_ID + cityId + Messages.NOT_EXIST);
        }
        City cityToUpdate = cityOptional.get();
        if (city.getName() != null && !city.getName().isEmpty() && !city.getName().equals(cityToUpdate.getName())) {
            cityToUpdate.setName(city.getName());
        }
        cityRepository.save(cityToUpdate);
    }
    @Override
    public void delete(long cityId) throws CityNotFoundException {
        boolean exists = cityRepository.existsById(cityId);
        if (!exists) {
            throw new CityNotFoundException(Messages.CITY_WITH_ID + cityId + Messages.NOT_EXIST);
        }
        cityRepository.deleteById(cityId);
    }
    @Override
    public City get(Long cityId) throws CityNotFoundException {
        Optional<City> cityOptional = cityRepository.findById(cityId);
        if (cityOptional.isEmpty()) {
            throw new CityNotFoundException(Messages.CITY_WITH_ID + cityId + Messages.NOT_EXIST);
        }
        return cityOptional.get();
    }
}

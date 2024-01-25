package org.mindswap.springtheknife.service.city;

import jakarta.validation.Valid;
import org.mindswap.springtheknife.converter.CityConverter;
import org.mindswap.springtheknife.dto.city.CityDto;
import org.mindswap.springtheknife.exceptions.city.CityNotFoundException;
import org.mindswap.springtheknife.exceptions.city.DuplicateCityException;
import org.mindswap.springtheknife.model.City;
import org.mindswap.springtheknife.repository.CityRepository;
import org.mindswap.springtheknife.utils.Message;
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
            throw new DuplicateCityException(Message.DUPLICATE_NAME + city.name() + Message.EXIST);
        @Valid City newCity = CityConverter.fromCreateDtoToModel(city);
        return cityRepository.save(newCity);

    }

    @Override
    public void update(long cityId, City city) throws CityNotFoundException {
        Optional<City> cityOptional = cityRepository.findById(cityId);
        if (cityOptional.isEmpty()) {
            throw new CityNotFoundException(Message.CITY_WITH_ID + cityId + Message.NOT_EXIST);
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
            throw new CityNotFoundException(Message.CITY_WITH_ID + cityId + Message.NOT_EXIST);
        }
        cityRepository.deleteById(cityId);
    }
    @Override
    public City get(Long cityId) throws CityNotFoundException {
        Optional<City> cityOptional = cityRepository.findById(cityId);
        if (cityOptional.isEmpty()) {
            throw new CityNotFoundException(Message.CITY_WITH_ID + cityId + Message.NOT_EXIST);
        }
        return cityOptional.get();
    }
}

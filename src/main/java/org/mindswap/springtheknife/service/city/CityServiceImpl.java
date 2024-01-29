package org.mindswap.springtheknife.service.city;

import org.mindswap.springtheknife.converter.CityConverter;
import org.mindswap.springtheknife.dto.city.CityDto;
import org.mindswap.springtheknife.dto.city.CityGetDto;
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
    public List<CityGetDto> getCities() {
        List<City> cities = this.cityRepository.findAll();
        return cities.stream()
        .map(CityConverter::fromModelToCityDto)
                .toList();
    }

    @Override
    public CityGetDto getCity(Long id) throws CityNotFoundException {
        Optional<City> cityOptional = cityRepository.findById(id);
        if (cityOptional.isEmpty()) {
            throw new CityNotFoundException(id + Message.CITY_NOT_FOUND);
        }
        return CityConverter.fromModelToCityDto(cityOptional.get());

    }

    public City getCityById(Long cityId) throws CityNotFoundException {
        Optional<City> cityOptional = cityRepository.findById(cityId);
        if (cityOptional.isEmpty()) {
            throw new CityNotFoundException(Message.CITY_WITH_ID + cityId + Message.NOT_EXIST);
        }
        return cityOptional.get();
    }

    @Override
    public CityGetDto create(CityDto city) throws DuplicateCityException {
        Optional<City> cityOptional = this.cityRepository.findByName(city.name());
        if(cityOptional.isPresent())
            throw new DuplicateCityException(Message.DUPLICATE_NAME + city.name() + Message.EXIST);
        cityRepository.save(CityConverter.fromCreateDtoToModel(city));
        return CityConverter.fromCreateDtoToDto(city);

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

}

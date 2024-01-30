package org.mindswap.springtheknife.service.restauranttype;

import org.mindswap.springtheknife.converter.RestaurantTypeConverter;
import org.mindswap.springtheknife.dto.restaurantTypeDto.RestaurantTypeDto;
import org.mindswap.springtheknife.exceptions.restaurantType.RestaurantTypeAlreadyExistsException;
import org.mindswap.springtheknife.exceptions.restaurantType.RestaurantTypeNotFoundException;
import org.mindswap.springtheknife.model.RestaurantType;
import org.mindswap.springtheknife.repository.RestaurantTypeRepository;
import org.mindswap.springtheknife.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RestaurantTypeImpl implements RestaurantTypeService {
    RestaurantTypeRepository restaurantTypeRepository;

    @Autowired
    public RestaurantTypeImpl(RestaurantTypeRepository restaurantTypeRepository) {
        this.restaurantTypeRepository = restaurantTypeRepository;
    }

    @Override
    public List<RestaurantTypeDto> getRestaurantType(){
        List<RestaurantType> restaurantType = restaurantTypeRepository.findAll();
        return restaurantType.stream().map(RestaurantTypeConverter::fromModelToRestaurantTypeDto).toList();
    }
    public Set<RestaurantTypeDto> getRestaurantTypeById(Set<Long> restaurantTypeId){
        List<RestaurantType> restaurantType = restaurantTypeRepository.findAllById(restaurantTypeId);
        return restaurantType.stream().map(RestaurantTypeConverter::fromModelToRestaurantTypeDto).collect(Collectors.toSet());
            }

    @Override
    public RestaurantTypeDto getById(Long id) throws RestaurantTypeNotFoundException {
        RestaurantType restaurantType = restaurantTypeRepository.findById(id).orElseThrow(() -> new RestaurantTypeNotFoundException(Message.TYPE_ID + id + Message.NOT_FOUND));
        return RestaurantTypeConverter.fromModelToRestaurantTypeDto(restaurantType);
    }

    @Override
    public RestaurantTypeDto addType (RestaurantTypeDto restaurantType) throws RestaurantTypeAlreadyExistsException {
        Optional<RestaurantType> typeOptional = restaurantTypeRepository.findByType(restaurantType.type());
        if (typeOptional.isPresent()) {
            throw new RestaurantTypeAlreadyExistsException(Message.ALREADY_EXISTS);
        }
        RestaurantType restaurantType1 = RestaurantTypeConverter.fromRestaurantTypeDtoToModel(restaurantType);
        return RestaurantTypeConverter.fromModelToRestaurantTypeDto(restaurantTypeRepository.save(restaurantType1));
    }

    @Override
    public void deleteType (Long restaurantTypeId) throws RestaurantTypeNotFoundException {
        restaurantTypeRepository.findById(restaurantTypeId).orElseThrow(() -> new RestaurantTypeNotFoundException(Message.TYPE_ID + restaurantTypeId + Message.NOT_FOUND));
        restaurantTypeRepository.deleteById(restaurantTypeId);
    }

    @Override
    public RestaurantTypeDto patchType (Long id, RestaurantTypeDto restaurantType) throws RestaurantTypeNotFoundException {
        RestaurantType dbRestaurantType = restaurantTypeRepository.findById(id).orElseThrow(() -> new RestaurantTypeNotFoundException(Message.TYPE_ID + id + Message.NOT_FOUND));
        if (restaurantTypeRepository.findByType(restaurantType.type()).isPresent()) {
            throw new IllegalStateException(Message.TYPE_TAKEN);
        }
        if (restaurantType.type() != null) {
            dbRestaurantType.setType(restaurantType.type());
        }
        return RestaurantTypeConverter.fromModelToRestaurantTypeDto(restaurantTypeRepository.save(dbRestaurantType));
    }
}

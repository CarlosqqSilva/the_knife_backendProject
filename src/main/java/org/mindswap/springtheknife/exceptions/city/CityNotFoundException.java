package org.mindswap.springtheknife.exceptions.city;

public class CityNotFoundException extends CityServiceException{
    public CityNotFoundException(String message) {
        super(message);
    }
}

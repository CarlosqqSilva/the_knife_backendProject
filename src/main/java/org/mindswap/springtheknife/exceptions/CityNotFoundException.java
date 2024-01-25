package org.mindswap.springtheknife.exceptions;

public class CityNotFoundException extends CityServiceException{
    public CityNotFoundException(String message) {
        super(message);
    }
}

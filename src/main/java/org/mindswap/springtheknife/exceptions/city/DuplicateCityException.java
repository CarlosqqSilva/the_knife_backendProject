package org.mindswap.springtheknife.exceptions.city;


public class DuplicateCityException extends CityServiceException {
    public DuplicateCityException(String message) {
        super(message);
    }
}

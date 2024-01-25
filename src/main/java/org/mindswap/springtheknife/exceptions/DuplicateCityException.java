package org.mindswap.springtheknife.exceptions;


public class DuplicateCityException extends CityServiceException {
    public DuplicateCityException(String message) {
        super(message);
    }
}

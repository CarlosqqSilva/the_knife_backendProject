package org.mindswap.springtheknife.utils;

import jakarta.validation.constraints.Pattern;

import javax.swing.text.html.parser.DTDConstants;

public class Message {
    public final static String USER_ID_DOES_NOT_EXIST = " User ID does not exist.";
    public static final String USER_ID_ALREADY_EXISTS = " User ID already exists.";
    public static final String INVALID_USER_ID = "Invalid User ID";
    public static final String USERNAME_MANDATORY = "Insert UserName";
    public static final String PASSWORD_MANDATORY = "Insert Password";
    public static final String FIRSTNAME_MANDATORY = "Insert First Name";
    public static final String LASTNAME_MANDATORY = "Insert LastName";
    public static final String DATE_OF_BIRTH_MANDATORY = "Insert Date of Birth";
    public static final String VALID_EMAIL = "Insert a valid Email";
    public static final String VALID_LASTNAME = "Insert a valid LastName";
    public static final String VALID_DATE_OF_BIRTH = "Insert a valid Date of Birth";
    public static final String EMAIL_MANDATORY = "Insert an Email";
    public static final String VALID_USERNAME = "Insert a valid UserName";
    public static final String VALID_PASSWORD = "Insert a valid Password";
    public static final String EMAIL_TAKEN = "Email already taken";
    public static final String USER_EXPERIENCE_ID_NOT_FOUND = "Invalid UserName";
    public static final Object USER_EXPERIENCE_ID_ALREADY_EXISTS = "User Experience ID already exists.";
    public static final String BOOKING_ID = "Booking with Id";
    public static final String DELETE_SUCCESSFULLY = "deleted successfully";
    public static final String BOOKING_MANDATORY = "Insert BookingTime";
    public static final String STATUS_MANDATORY = "Insert Status";
    public static final String NOT_FOUND = "not found";
    public static final String ALREADY_EXISTS = "Already exists";
    public static final String VALID_STATUS = "Valid status";
    public static final String CITY_WITH_ID = "City with id";
    public static final String NOT_EXIST = "does not exist";
    public static final String DUPLICATE_NAME ="City with name";
    public static final String EXIST = "already exists";
    public static final String NAME_ERROR = "Must have name";
    public static final String TYPE_MANDATORY = "Must have Type";
    public static final String TYPE_ID = "Type Id";
    public static final String TYPE_TAKEN = "Type taken";
    public static final String USER_EXPERIENCE_ID_DELETED = " - Deleted successfully User Experience with ID";
    public static final String USER_NAME_ALREADY_INSERTED = "User Name already inserted";
    public static final String CITY_NOT_FOUND = "City not found";
    public static final String RESTAURANT_NAME_MANDATORY = "Restaurant must have a name.";
    public static final String VALID_RESTAURANT_NAME = "Restaurant must have a valid name.";
    public static final String RESTAURANT_VALIDATOR = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+){0,25}$";
    public static final String RESTAURANT_ADDRESS_MANDATORY = "Restaurant must have an address.";
    public static final String EMAIL_VALIDATOR = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";
    public static final String PHONE_NUMBER_MANDATORY = "Restaurant must have a phone number.";
    public static final String PHONE_NUMBER_VALIDATOR = "^\\+351(9[1236]|2)\\d{8}$";
    public static final String LATITUDE_MANDATORY = "Insert Restaurant Latitude";
    public static final String LONGITUDE_MANDATORY = "Insert Restaurant Longitude";
    public static final String LONGITUDE_VALIDATOR = "^[-+]?((1[0-7]\\d(\\.\\d+)?)|(180(\\.0+)?)|([1-9]?\\d(\\.\\d+)?))$";
    public static final String INVALID_CITY_ID = "Insert a valid City Id";
    public static final String INVALID_PHONE_NUMBER = "Insert a valid phone number";
    public static final String INVALID_RATING = "Insert a valid Rating between 0 and 10";
    public static final String RATING_MANDATORY = "Insert Rating";
    public static final String COMMENT_MANDATORY = "Insert Comment";
    public static final String INVALID_COMMENT = "Insert a valid Comment";
    public static final String USER_ID_MANDATORY = "Insert User Id";
    public static final String RESTAURANT_ID_MANDATORY = "Insert Restaurant Id";
}

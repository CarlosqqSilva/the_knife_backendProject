package org.mindswap.springtheknife.exceptions.user;

public class UserAlreadyExists extends Exception {
    public UserAlreadyExists (String message){

        super (message);
    }
}

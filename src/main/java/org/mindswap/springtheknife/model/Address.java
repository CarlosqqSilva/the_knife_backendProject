package org.mindswap.springtheknife.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Address implements Serializable {

    private String street;
    private String number;
    private String zipCode;
}

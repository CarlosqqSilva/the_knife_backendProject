package org.mindswap.springtheknife.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Address {

    private String street;
    private String number;
    private String zipCode;
}

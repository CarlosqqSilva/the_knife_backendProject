package org.mindswap.springtheknife.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "restaurants")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Restaurant must have a name.")
    private String name;
    @Setter
    private String address;
    @Setter
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Restaurant must have a phone number.")
    @Column(unique = true)
    private String phoneNumber;
    private Double latitude;
    private Double longitude;
    private Double rating;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant",orphanRemoval = true)
    private List<UserExperience> userExperienceList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "city_id")
    private City city;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Booking> bookingList;
    @ManyToMany
    private Set<RestaurantType> restaurantTypes = new HashSet<>();
}

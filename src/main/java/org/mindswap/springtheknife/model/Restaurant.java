package org.mindswap.springtheknife.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @NotBlank(message = "Restaurant must have a address.")
    private String address;
    @NotBlank()
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Restaurant must have a phone number.")
    @Column(unique = true)
    private String phoneNumber;
    private Double latitude;
    private Double longitude;
    private Double rating;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Booking> bookingList;
    @OneToMany
    @JoinColumn(name = "restaurant")
    private List<UserExperience> userExperienceList;
}

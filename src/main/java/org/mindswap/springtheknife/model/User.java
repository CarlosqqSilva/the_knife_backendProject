package org.mindswap.springtheknife.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserExperience> userExperiences = new ArrayList<>();

  /*  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Restaurant> favouriteRestaurants = new ArrayList<>();*/

    @Setter
    @Column(unique = true)
    private String userName;

    private String password;

    @Setter
    @Column(unique = true)
    private String email;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;


}

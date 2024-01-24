package org.mindswap.springtheknife.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.ArrayList;


@Data
@Entity
@Builder
@Table(name = "UserExperience")
public class UserExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Restaurant restaurant;

   /* @OneToMany(mappedBy = "customerExperience", cascade = CascadeType.ALL)
    private List<CustomerPhoto> photos = new ArrayList<>();*/

    private int rating;

    private String comment;

    private LocalDateTime timestamp;


}

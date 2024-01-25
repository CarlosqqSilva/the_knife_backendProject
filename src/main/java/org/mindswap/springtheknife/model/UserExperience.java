package org.mindswap.springtheknife.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;


import java.time.LocalDateTime;
import java.util.ArrayList;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userExperience")
public class UserExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

   /* @OneToMany(mappedBy = "customerExperience", cascade = CascadeType.ALL)
    private List<CustomerPhoto> photos = new ArrayList<>();*/

    private int rating;

    private String comment;

    private LocalDateTime timestamp;


}

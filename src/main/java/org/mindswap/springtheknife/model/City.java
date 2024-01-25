package org.mindswap.springtheknife.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.mindswap.springtheknife.utilities.Messages;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    @NotNull(message = Messages.NAME_ERROR)
    @Column(unique = true)
    private String name;

    /*@OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Restaurant> restaurants;*/
}

package org.mindswap.springtheknife.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.mindswap.springtheknife.utils.Message;
import java.util.HashSet;
import java.util.Set;

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

    @NotNull(message = Message.NAME_ERROR)
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "city",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Restaurant> restaurants = new HashSet<>();
}


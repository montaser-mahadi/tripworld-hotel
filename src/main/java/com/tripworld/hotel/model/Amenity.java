package com.tripworld.hotel.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "amenity_mst")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "amenity_id")
    private Integer Id;

    @Column(name = "short_desc", length = 10, nullable = false)
    @Size(max = 10, min = 1)
    @NotBlank
    private String shortDescription;

    @Column(name = "description", length = 200, nullable = false)
    @Size(max = 200, min = 1)
    @NotBlank
    private String description;

    public Amenity(String shortDesc, String description) {
        this.shortDescription = shortDesc;
        this.description = description;
    }
}

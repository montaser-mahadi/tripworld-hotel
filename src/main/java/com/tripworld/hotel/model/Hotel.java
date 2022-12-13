package com.tripworld.hotel.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "Hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hotel_id")
    private Integer id;

    @Column(name = "hotel_name", length = 50, nullable = false)
    @Size(max = 50, min = 1)
    @NotBlank
    private String hotelName;

    @Column(name = "description", length = 500, nullable = false)
    @Size(max = 500, min = 1)
    @NotBlank
    private String description;

    @Column(name = "city_code", length = 3, nullable = false)
    @Size(max = 3, min = 1)
    @NotBlank
    private String cityCode;

    public Hotel(String hotelName, String description, String cityCode) {
        this.hotelName = hotelName;
        this.description = description;
        this.cityCode = cityCode;
    }
}

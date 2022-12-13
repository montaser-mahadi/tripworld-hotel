package com.tripworld.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "hotel_amenities")
public class HotelAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hotel_ame_id", nullable = false)
    private Integer Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amenity_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Amenity amenity;

    private boolean chargeable;
    private double amount;

    public HotelAmenity(Hotel hotel, Amenity amenity, boolean chargeable, double amount) {
        this.hotel = hotel;
        this.amenity = amenity;
        this.chargeable = chargeable;
        this.amount = amount;
    }
}

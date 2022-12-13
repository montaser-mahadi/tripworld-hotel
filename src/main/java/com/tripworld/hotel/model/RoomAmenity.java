package com.tripworld.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "room_amenities")
public class RoomAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_ame_id")
    private Integer Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amenity_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Amenity amenity;

    private boolean chargeable;
    private double amount;

    public RoomAmenity(Room room, Amenity amenity, boolean chargeable, double amount) {
        this.room = room;
        this.amenity = amenity;
        this.chargeable = chargeable;
        this.amount = amount;
    }
}
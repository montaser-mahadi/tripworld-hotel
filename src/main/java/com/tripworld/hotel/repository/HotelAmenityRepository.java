package com.tripworld.hotel.repository;

import com.tripworld.hotel.model.HotelAmenity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HotelAmenityRepository extends JpaRepository<HotelAmenity, Integer> {

}

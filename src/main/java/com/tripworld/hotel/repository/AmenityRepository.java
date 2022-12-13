package com.tripworld.hotel.repository;

import com.tripworld.hotel.model.Amenity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Integer> {
    Page<Amenity> findByShortDescriptionContaining(String shortDesc, Pageable pageable);
}

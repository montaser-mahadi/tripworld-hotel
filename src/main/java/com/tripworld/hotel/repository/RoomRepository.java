package com.tripworld.hotel.repository;

import com.tripworld.hotel.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Page<Room> findByDescription(String description, Pageable pageable);
}

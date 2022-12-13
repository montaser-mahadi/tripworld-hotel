package com.tripworld.hotel.service.hotel.imp;

import com.tripworld.hotel.exception.ResourceNotFoundException;
import com.tripworld.hotel.model.Room;
import com.tripworld.hotel.repository.HotelRepository;
import com.tripworld.hotel.repository.RoomRepository;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.PagedResponse;
import com.tripworld.hotel.service.hotel.RoomService;
import com.tripworld.hotel.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;
    private static final String DESCRIPTION = "description";

    public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }


    // - Rooms under a hotel
    @Override
    public Room searchRoom(Integer roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", roomId));
        return room;
    }

    // insert room
    @Override
    public Room addRoom(Room room, Integer hotelId) {
        Room newRoom = hotelRepository.findById(hotelId).map(hotel -> {
            room.setHotel(hotel);
            return roomRepository.save(room);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Hotel with id = ", "hotelId", room));
        return newRoom;
    }

    @Override
    public ResponseEntity<ApiResponse> deleteRoom(Integer id) {
        roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));
        roomRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted room"), HttpStatus.OK);
    }
}

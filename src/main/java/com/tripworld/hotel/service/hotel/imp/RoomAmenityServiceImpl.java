package com.tripworld.hotel.service.hotel.imp;

import com.tripworld.hotel.exception.HotelApiException;
import com.tripworld.hotel.exception.ResourceNotFoundException;
import com.tripworld.hotel.model.Amenity;
import com.tripworld.hotel.model.Room;
import com.tripworld.hotel.model.RoomAmenity;
import com.tripworld.hotel.repository.AmenityRepository;
import com.tripworld.hotel.repository.RoomAmenityRepository;
import com.tripworld.hotel.repository.RoomRepository;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.PagedResponse;
import com.tripworld.hotel.service.hotel.RoomAmenityService;
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
public class RoomAmenityServiceImpl implements RoomAmenityService {

    @Autowired
    private RoomAmenityRepository roomAmenityRepository;
    private static final String AMOUNT = "amount";
    private static final String NO_ID_FOR_THIS = "Not found Room Amenity id with this id";
    private static final String AMENITY_DOES_NOT_BELONG_TO_AMENITYROOM = "Amenity does not belong to amenityRoom";
    private static final String ROOM_DOES_NOT_BELONG_TO_AMENITYROOM = "Room does not belong to amenityRoom";
    @Autowired
    private AmenityRepository amenityRepository;
    @Autowired
    private RoomRepository roomRepository;

    public RoomAmenityServiceImpl(RoomAmenityRepository roomAmenityRepository, AmenityRepository amenityRepository, RoomRepository roomRepository) {
        this.roomAmenityRepository = roomAmenityRepository;
        this.amenityRepository = amenityRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomAmenity searchRoomAmenity(Integer roomAmenityId) {
        RoomAmenity roomAmenity = roomAmenityRepository.findById(roomAmenityId)
                .orElseThrow(() -> new ResourceNotFoundException(NO_ID_FOR_THIS, "roomAmenityId", roomAmenityId));
        return roomAmenity;
    }

    @Override
    public RoomAmenity addRoomAmenity(RoomAmenity roomAmenity, Integer roomId, Integer amenityId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found room with id = ", "roomId", roomId));
        Amenity amenity = amenityRepository.findById(amenityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found amenity with id = ", "amenityId", amenityId));
        if (room != null && amenity != null) {
            roomAmenity.setAmenity(amenity);
            roomAmenity.setRoom(room);
            return roomAmenityRepository.save(roomAmenity);
        }
        return roomAmenity;
    }

    @Override
    public ApiResponse deleteRoomAmenity(Integer roomAmenityId, Integer roomId, Integer amenityId) {
        RoomAmenity roomAmenity = roomAmenityRepository.findById(roomAmenityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found room amenityId with id = ", "roomAmenityId", roomAmenityId));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found room with id = ", "roomId", roomId));
        Amenity amenity = amenityRepository.findById(amenityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found amenity with id = ", "amenityId", amenityId));
        if (roomAmenity.getRoom().getId().equals(room.getId())) {
            if (roomAmenity.getAmenity().getId().equals(amenity.getId())) {
                roomAmenityRepository.deleteById(roomAmenityId);
                return new ApiResponse(Boolean.TRUE, "You successfully deleted roomAmenity");
            }
        }
        throw new HotelApiException(HttpStatus.NOT_FOUND, ROOM_DOES_NOT_BELONG_TO_AMENITYROOM
                + "Or " + AMENITY_DOES_NOT_BELONG_TO_AMENITYROOM);
    }
}
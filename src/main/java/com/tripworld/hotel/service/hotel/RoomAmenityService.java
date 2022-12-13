package com.tripworld.hotel.service.hotel;

import com.tripworld.hotel.model.RoomAmenity;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.PagedResponse;
import org.springframework.http.ResponseEntity;

public interface RoomAmenityService {

    RoomAmenity searchRoomAmenity(Integer roomAmenityId);

    RoomAmenity addRoomAmenity(RoomAmenity roomAmenity, Integer roomId, Integer amenityId);

    ApiResponse deleteRoomAmenity(Integer id, Integer roomId, Integer amenityId);
}

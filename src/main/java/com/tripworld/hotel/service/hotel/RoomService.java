package com.tripworld.hotel.service.hotel;

import com.tripworld.hotel.model.Room;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.PagedResponse;
import org.springframework.http.ResponseEntity;

public interface RoomService {

    Room searchRoom(Integer roomId);

    Room addRoom(Room room, Integer hotelId);

    ResponseEntity<ApiResponse> deleteRoom(Integer id);
}

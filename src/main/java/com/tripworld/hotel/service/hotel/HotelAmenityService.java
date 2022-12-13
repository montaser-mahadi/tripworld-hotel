package com.tripworld.hotel.service.hotel;

import com.tripworld.hotel.model.HotelAmenity;
import com.tripworld.hotel.model.RoomAmenity;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.PagedResponse;
import org.springframework.http.ResponseEntity;

public interface HotelAmenityService {

    HotelAmenity searchHotelAmenity(Integer hotelAmenityId);

    HotelAmenity addHotelAmenity(HotelAmenity hotelAmenity, Integer hotelId, Integer amenityId);

    ApiResponse deleteHotelAmenity(Integer hotelAmenityId, Integer hotelId, Integer amenityId);
}

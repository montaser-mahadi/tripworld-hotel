package com.tripworld.hotel.service.hotel;

import com.tripworld.hotel.model.Hotel;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.PagedResponse;
import org.springframework.http.ResponseEntity;

public interface HotelService {
    
    PagedResponse<Hotel> searchHotel(String hotelName, int page, int size);

    Hotel addHotel(Hotel hotel);

    ResponseEntity<ApiResponse> deleteHotel(Integer id);
}

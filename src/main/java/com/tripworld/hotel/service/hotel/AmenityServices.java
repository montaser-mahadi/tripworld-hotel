package com.tripworld.hotel.service.hotel;

import com.tripworld.hotel.model.Amenity;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.PagedResponse;
import org.springframework.http.ResponseEntity;

public interface AmenityServices {

    PagedResponse<Amenity> searchAmenity(String Search, int page, int size);

    Amenity addAmenity(Amenity amenity);

    ResponseEntity<ApiResponse> deleteAmenity(Integer id);


}
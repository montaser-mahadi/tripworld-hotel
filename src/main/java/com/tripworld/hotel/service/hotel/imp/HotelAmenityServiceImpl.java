package com.tripworld.hotel.service.hotel.imp;

import com.tripworld.hotel.exception.HotelApiException;
import com.tripworld.hotel.exception.ResourceNotFoundException;
import com.tripworld.hotel.model.Amenity;
import com.tripworld.hotel.model.Hotel;
import com.tripworld.hotel.model.HotelAmenity;
import com.tripworld.hotel.repository.AmenityRepository;
import com.tripworld.hotel.repository.HotelAmenityRepository;
import com.tripworld.hotel.repository.HotelRepository;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.PagedResponse;
import com.tripworld.hotel.service.hotel.HotelAmenityService;
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
public class HotelAmenityServiceImpl implements HotelAmenityService {

    @Autowired
    private HotelAmenityRepository hotelAmenityRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    private static final String AMOUNT = "amount";
    private static final String NO_ID_FOR_THIS = "Not found Hotel Amenity id with this id";
    private static final String AMENITY_DOES_NOT_BELONG_TO_AMENITYHOTEL = "Amenity does not belong to amenity hotel";
    private static final String HOTEL_DOES_NOT_BELONG_TO_AMENITYHOTEL = "Hotel does not belong to amenity hotel";

    public HotelAmenityServiceImpl(HotelAmenityRepository hotelAmenityRepository, HotelRepository hotelRepository, AmenityRepository amenityRepository) {
        this.hotelAmenityRepository = hotelAmenityRepository;
        this.hotelRepository = hotelRepository;
        this.amenityRepository = amenityRepository;
    }

    @Override
    public HotelAmenity searchHotelAmenity(Integer hotelAmenityId) {
        HotelAmenity hotelAmenity = hotelAmenityRepository.findById(hotelAmenityId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel Amenity", "hotelAmenityId", hotelAmenityId));
        return hotelAmenity;
    }

    @Override
    public HotelAmenity addHotelAmenity(HotelAmenity hotelAmenity, Integer hotelId, Integer amenityId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Hotel with id = ", "hotelId", hotelId));
        Amenity amenity = amenityRepository.findById(amenityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found amenity with id = ", "amenityId", amenityId));

        if (hotel != null && amenity != null) {
            hotelAmenity.setAmenity(amenity);
            hotelAmenity.setHotel(hotel);
            return hotelAmenityRepository.save(hotelAmenity);
        }
        return hotelAmenity;
    }

    @Override
    public ApiResponse deleteHotelAmenity(Integer hotelAmenityId, Integer hotelId, Integer amenityId) {
        HotelAmenity hotelAmenity = hotelAmenityRepository.findById(hotelAmenityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found hotel amenity with id = ", "hotelAmenityId", hotelAmenityId));
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found hotel with id = ", "hotelId", hotelId));
        Amenity amenity = amenityRepository.findById(amenityId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found amenity with id = ", "amenityId", amenityId));
        if (hotelAmenity.getHotel().getId().equals(hotel.getId())) {
            if (hotelAmenity.getAmenity().getId().equals(amenity.getId())) {
                hotelAmenityRepository.deleteById(hotelAmenityId);
                return new ApiResponse(Boolean.TRUE, "You successfully deleted hotelAmenity");
            }
        }
        throw new HotelApiException(HttpStatus.NOT_FOUND, HOTEL_DOES_NOT_BELONG_TO_AMENITYHOTEL
                + "Or" + AMENITY_DOES_NOT_BELONG_TO_AMENITYHOTEL);
    }
}
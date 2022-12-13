package com.tripworld.hotel.service.hotel.imp;


import com.tripworld.hotel.exception.ResourceNotFoundException;
import com.tripworld.hotel.model.Hotel;
import com.tripworld.hotel.repository.HotelRepository;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.PagedResponse;
import com.tripworld.hotel.service.hotel.HotelService;
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
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    private static final String HOTELNAME = "hotelName";

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public PagedResponse<Hotel> searchHotel(String hotelName, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, HOTELNAME);
        Page<Hotel> hotelPage = hotelRepository.findByHotelName(hotelName, pageable);
        if (hotelPage.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), hotelPage.getNumber(), hotelPage.getSize(), hotelPage.getTotalElements(),
                    hotelPage.getTotalPages(), hotelPage.isLast());
        }
        List<Hotel> hotelList = hotelPage.getNumberOfElements() > 0 ? hotelPage.getContent() : Collections.emptyList();
        return new PagedResponse<>(hotelList, hotelPage.getNumber(), hotelPage.getSize(), hotelPage.getTotalElements(),
                hotelPage.getTotalPages(), hotelPage.isLast());
    }

    @Override
    public Hotel addHotel(Hotel hotel) {
        Hotel newHotel = hotelRepository.save(new Hotel(hotel.getHotelName(), hotel.getDescription(),
                hotel.getCityCode()));
        return newHotel;
    }

    @Override
    public ResponseEntity<ApiResponse> deleteHotel(Integer id) {
        hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", id));
        hotelRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted hotel"), HttpStatus.OK);
    }
}

package com.tripworld.hotel.controller;


import com.tripworld.hotel.exception.ResponseEntityErrorException;
import com.tripworld.hotel.model.HotelAmenity;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.hotel.imp.HotelAmenityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/hotelAmenity")
public class HotelAmenityController {

    @Autowired
    private HotelAmenityServiceImpl hotelAmenityService;

    public HotelAmenityController(HotelAmenityServiceImpl hotelAmenityService) {
        this.hotelAmenityService = hotelAmenityService;
    }

    @ExceptionHandler(ResponseEntityErrorException.class)
    public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityErrorException exception) {
        return exception.getApiResponse();
    }


    // search hotelAmenity by id
    @PostMapping("/search/{hotelAmenityId}")
    public ResponseEntity<HotelAmenity> searchHotelAmenity(@PathVariable(value = "hotelAmenityId") Integer hotelAmenityId) {
        HotelAmenity hotelAmenity = hotelAmenityService.searchHotelAmenity(hotelAmenityId);
        return new ResponseEntity<>(hotelAmenity, HttpStatus.OK);
    }


    // create room
    @PostMapping("/create/{hotelId}/{amenityId}")
    public ResponseEntity<HotelAmenity> createHotelAmenity(@PathVariable(value = "hotelId") Integer hotelId,
                                                           @PathVariable(value = "amenityId") Integer amenityId,
                                                           @RequestBody HotelAmenity hotelAmenityRequest) {

        HotelAmenity hotelAmenity = hotelAmenityService.addHotelAmenity(hotelAmenityRequest, hotelId, amenityId);
        return new ResponseEntity<>(hotelAmenity, HttpStatus.ACCEPTED);
    }

    // delete RoomAmenity by id
    @DeleteMapping("delete/{hotelAmenityId}/{hotelId}/{amenityId}")
    public ApiResponse deleteRoomAmenity(@PathVariable(name = "hotelAmenityId") Integer hotelAmenityId,
                                         @PathVariable(name = "hotelId") Integer hotelId,
                                         @PathVariable(name = "amenityId") Integer amenityId) {
        return hotelAmenityService.deleteHotelAmenity(hotelAmenityId, hotelId, amenityId);
    }
}

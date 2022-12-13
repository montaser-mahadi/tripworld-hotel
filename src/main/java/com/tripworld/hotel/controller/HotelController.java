package com.tripworld.hotel.controller;


import com.tripworld.hotel.exception.ResponseEntityErrorException;
import com.tripworld.hotel.model.Hotel;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.PagedResponse;
import com.tripworld.hotel.service.hotel.imp.HotelServiceImpl;
import com.tripworld.hotel.utils.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelServiceImpl hotelService;

    @ExceptionHandler(ResponseEntityErrorException.class)
    public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityErrorException exception) {
        return exception.getApiResponse();
    }


    // search hotel by hotel name
    @GetMapping("/search")
    public ResponseEntity<PagedResponse<Hotel>> searchHotelByName(@RequestParam(name = "hotelName", required = false) String hotelName,
                                                                  @RequestParam(name = "page", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
                                                                  @RequestParam(name = "size", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size) {
        PagedResponse<Hotel> response = hotelService.searchHotel(hotelName, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // create hotel
    @PostMapping("/create")
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel newHotel = hotelService.addHotel(hotel);
        return new ResponseEntity<>(newHotel, HttpStatus.CREATED);
    }


    // delete hotel by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteHotel(@PathVariable(name = "id") Integer id) {
        return hotelService.deleteHotel(id);
    }
}
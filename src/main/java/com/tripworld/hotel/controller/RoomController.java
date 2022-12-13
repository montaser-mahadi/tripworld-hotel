package com.tripworld.hotel.controller;


import com.tripworld.hotel.exception.ResponseEntityErrorException;
import com.tripworld.hotel.model.HotelAmenity;
import com.tripworld.hotel.model.Room;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.PagedResponse;
import com.tripworld.hotel.service.hotel.imp.RoomServiceImpl;
import com.tripworld.hotel.utils.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    @Autowired
    private RoomServiceImpl roomService;

    public RoomController(RoomServiceImpl roomService) {
        this.roomService = roomService;
    }

    @ExceptionHandler(ResponseEntityErrorException.class)
    public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityErrorException exception) {
        return exception.getApiResponse();
    }


    // search room by id
    @PostMapping("/search/{roomId}")
    public ResponseEntity<Room> searchRoom(@PathVariable(value = "roomId") Integer roomId) {
        Room room = roomService.searchRoom(roomId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }


    // create room
    @PostMapping("/create/{hotelId}")
    public ResponseEntity<Room> createRoom(@PathVariable(value = "hotelId") Integer hotelId, @RequestBody Room roomRequest) {
        Room room = roomService.addRoom(roomRequest, hotelId);
        return new ResponseEntity<>(room, HttpStatus.ACCEPTED);
    }


    // delete hotel by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteRoom(@PathVariable(name = "id") Integer id) {
        return roomService.deleteRoom(id);
    }
}
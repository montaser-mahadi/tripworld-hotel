package com.tripworld.hotel.controller;

import com.tripworld.hotel.exception.ResponseEntityErrorException;
import com.tripworld.hotel.model.RoomAmenity;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.hotel.imp.RoomAmenityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roomAmenity")
public class RoomAmenityController {

    @Autowired
    private RoomAmenityServiceImpl roomAmenityService;


    public RoomAmenityController(RoomAmenityServiceImpl roomAmenityService) {
        this.roomAmenityService = roomAmenityService;
    }

    @ExceptionHandler(ResponseEntityErrorException.class)
    public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityErrorException exception) {
        return exception.getApiResponse();
    }


    // search by id
    @PostMapping("search/{roomAmenityId}")
    public ResponseEntity<RoomAmenity> searchById(@PathVariable(value = "roomAmenityId") Integer roomAmenityId) {
        RoomAmenity roomAmenity = roomAmenityService.searchRoomAmenity(roomAmenityId);
        return new ResponseEntity<>(roomAmenity, HttpStatus.OK);
    }


    // create room
    @PostMapping("create/{roomId}/{amenityId}")
    public ResponseEntity<RoomAmenity> createRoomAmenity(@PathVariable(value = "roomId") Integer roomId,
                                                         @PathVariable(value = "amenityId") Integer amenityId,
                                                         @RequestBody RoomAmenity roomAmenityRequest) {
        RoomAmenity roomAmenity = roomAmenityService.addRoomAmenity(roomAmenityRequest, roomId, amenityId);
        return new ResponseEntity<>(roomAmenity, HttpStatus.ACCEPTED);
    }

    // delete RoomAmenity by id
    @DeleteMapping("delete/{roomAmenityId}/{roomId}/{amenityId}")
    public ApiResponse deleteRoomAmenity(@PathVariable(name = "roomAmenityId") Integer roomAmenityId,
                                         @PathVariable(name = "roomId") Integer roomId,
                                         @PathVariable(name = "amenityId") Integer amenityId) {
        return roomAmenityService.deleteRoomAmenity(roomAmenityId, roomId, amenityId);
    }
}
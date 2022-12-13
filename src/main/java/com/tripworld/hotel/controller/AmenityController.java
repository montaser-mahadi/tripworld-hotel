package com.tripworld.hotel.controller;


import com.tripworld.hotel.exception.ResponseEntityErrorException;
import com.tripworld.hotel.model.Amenity;
import com.tripworld.hotel.repository.AmenityRepository;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.PagedResponse;
import com.tripworld.hotel.service.hotel.imp.AmenityServiceImpl;
import com.tripworld.hotel.utils.AppConstant;
import com.tripworld.hotel.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/amenity")
public class AmenityController {

    @Autowired
    private AmenityServiceImpl amenityService;
    @Autowired
    private AmenityRepository amenityRepository;

    public AmenityController(AmenityServiceImpl amenityService, AmenityRepository amenityRepository) {
        this.amenityService = amenityService;
        this.amenityRepository = amenityRepository;
    }

    @ExceptionHandler(ResponseEntityErrorException.class)
    public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityErrorException exception) {
        return exception.getApiResponse();
    }

    // search amenity
    @GetMapping("/search")
    public PagedResponse<Amenity> searchAmenity(
            @RequestParam(name = "page", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size,
            @RequestParam(name = "query") String query) {
        AppUtils.validatePageNumberAndSize(page, size);
        return amenityService.searchAmenity(query, page, size);
    }


    // create Amenity
    @PostMapping("/create")
    public ResponseEntity<Amenity> addAmenity(@Valid @RequestBody Amenity amenity) {
        amenityService.addAmenity(amenity);
        return new ResponseEntity<>(amenity, HttpStatus.OK);
    }


    // delete Amenity by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAmenity(@PathVariable(name = "id") Integer id) {
        return amenityService.deleteAmenity(id);
    }
}
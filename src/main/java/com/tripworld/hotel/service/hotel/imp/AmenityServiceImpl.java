package com.tripworld.hotel.service.hotel.imp;

import com.tripworld.hotel.exception.ResourceNotFoundException;
import com.tripworld.hotel.model.Amenity;
import com.tripworld.hotel.repository.AmenityRepository;
import com.tripworld.hotel.service.ApiResponse;
import com.tripworld.hotel.service.PagedResponse;
import com.tripworld.hotel.service.hotel.AmenityServices;
import com.tripworld.hotel.utils.AppUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

@Service
@Data
public class AmenityServiceImpl implements AmenityServices {

    private static final String AMENITY_STR = "Amenities";
    private static final String AMENITY_ID = "id";
    private static final String SHORTDESC = "shortDescription";
    @NotBlank
    @Size(max = 10, message = "Amenity short description must be maximum 10 characters, the value is too long")
    private String shortDescription;

    @NotBlank
    @Size(min = 200, message = "Amenity description must be maximum 200 characters, the description is too long")
    private String description;

    @Autowired
    private AmenityRepository amenityRepository;


    @Override
    public PagedResponse<Amenity> searchAmenity(String Search, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, SHORTDESC);
        Page<Amenity> amenityPageable = amenityRepository.findByShortDescriptionContaining(Search, pageable);
        if (amenityPageable.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), amenityPageable.getNumber(), amenityPageable.getSize(),
                    amenityPageable.getTotalElements(), amenityPageable.getTotalPages(), amenityPageable.isLast());
        }
        List<Amenity> amenities = amenityPageable.toList();
        return new PagedResponse<>(amenities, amenityPageable.getNumber(),
                amenityPageable.getSize(), amenityPageable.getTotalElements()
                , amenityPageable.getTotalPages(), amenityPageable.isLast());
    }

    @Override
    public Amenity addAmenity(Amenity amenity) {
        try {
            return amenityRepository.save(amenity);
        } catch (Exception exception) {
            return amenity;
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteAmenity(Integer id) {
        amenityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Amenity", "id", id));
        amenityRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted Amenity"), HttpStatus.OK);
    }
}

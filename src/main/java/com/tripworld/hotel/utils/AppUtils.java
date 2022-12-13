package com.tripworld.hotel.utils;

import com.tripworld.hotel.exception.HotelApiException;
import org.springframework.http.HttpStatus;

public class AppUtils {
    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new HotelApiException(HttpStatus.BAD_REQUEST, "Page number cannot be less than zero.");
        }

        if (size < 0) {
            throw new HotelApiException(HttpStatus.BAD_REQUEST, "Size number cannot be less than zero.");
        }

        if (size > AppConstant.MAX_PAGE_SIZE) {
            throw new HotelApiException(HttpStatus.BAD_REQUEST, "Page size must not be greater than " + AppConstant.MAX_PAGE_SIZE);
        }
    }
}


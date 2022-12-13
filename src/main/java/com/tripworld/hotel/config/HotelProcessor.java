package com.tripworld.hotel.config;

import com.tripworld.hotel.model.Hotel;
import org.springframework.batch.item.ItemProcessor;


public class HotelProcessor implements ItemProcessor<Hotel, Hotel> {
    @Override
    public Hotel process(Hotel hotel) throws Exception {
       return hotel;
    }
}

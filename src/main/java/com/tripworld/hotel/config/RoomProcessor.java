package com.tripworld.hotel.config;


import com.tripworld.hotel.model.Room;
import org.springframework.batch.item.ItemProcessor;

public class RoomProcessor implements ItemProcessor<Room, Room> {


    @Override
    public Room process(Room room) throws Exception {
        return room;
    }
}

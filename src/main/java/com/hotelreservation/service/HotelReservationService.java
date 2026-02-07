package com.hotelreservation.service;

import com.hotelreservation.model.Hotel;

import java.util.ArrayList;
import java.util.List;

public class HotelReservationService {
    List<Hotel> hotelList=new ArrayList<>();
    public void addHotel(String name,int weekdayRate,int weekendRate){
        Hotel hotel=new Hotel(name,weekdayRate,weekendRate);
        hotelList.add(hotel);
    }
    public List<Hotel> getHotels(){
        return hotelList;
    }
    public Hotel findCheapestHotel(int days){
        Hotel cheapestHotel = null;
        int minTotalRate = Integer.MAX_VALUE;
        for(Hotel hotel : hotelList){
            int totalRate = hotel.getWeekdayRate()*days;
            if(totalRate<minTotalRate){
                minTotalRate=totalRate;
                cheapestHotel = hotel;
            }
        }
        return cheapestHotel;
    }
}

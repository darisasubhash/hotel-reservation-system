package com.hotelreservation.service;

import com.hotelreservation.model.Hotel;
import com.hotelreservation.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class HotelReservationService {
    List<Hotel> hotelList=new ArrayList<>();
    public void addHotel(String name,int weekdayRate,int weekendRate){
        Hotel hotel=new Hotel(name,weekdayRate,weekendRate,0);
        hotelList.add(hotel);
    }
    //Add hotel with rating
    public void addHotel(String name, int weekdayRate, int weekendRate, int rating) {
        hotelList.add(new Hotel(name, weekdayRate, weekendRate, rating));
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
    public List<Hotel> findCheapestHotelByDates(String[] dates){
        List<Hotel> cheapestHotels = new ArrayList<>();
        int minTotalRate = Integer.MAX_VALUE;
        for(Hotel hotel : hotelList){
            int totalRate = 0;
            for(String date :dates){
                if(DateUtil.isWeekend(date)){
                    totalRate += hotel.getWeekendRate();
                }
                else {
                    totalRate +=hotel.getWeekdayRate();
                }
            }
            if(totalRate < minTotalRate){
                minTotalRate = totalRate;
                cheapestHotels.clear();
                cheapestHotels.add(hotel);
            } else if (totalRate == minTotalRate) {
                cheapestHotels.add(hotel);
            }
        }
        return cheapestHotels;
    }
}

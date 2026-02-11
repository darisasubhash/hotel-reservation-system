package com.hotelreservation.service;

import com.hotelreservation.HotelReservation;
import com.hotelreservation.exception.HotelReservationException;
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
    //findin gcheapest hotel with best rating
    public Hotel findCheapestBestHotel(String[] dates){
        List<Hotel> cheapestHotels=new ArrayList<>();
        int minTotalRate=Integer.MAX_VALUE;
        for(Hotel hotel:hotelList){
            int totalRate=0;
            for (String date : dates){
                if(DateUtil.isWeekend(date)){
                    totalRate+= hotel.getWeekendRate();
                }
                else {
                    totalRate+=hotel.getWeekdayRate();
                }
            }
            if(totalRate<minTotalRate){
                minTotalRate=totalRate;
                cheapestHotels.clear();
                cheapestHotels.add(hotel);
            } else if (totalRate == minTotalRate) {
                cheapestHotels.add(hotel);
            }
        }
        Hotel bestRatedHotel=cheapestHotels.get(0);
        for (Hotel hotel : cheapestHotels){
            if(hotel.getRating() > bestRatedHotel.getRating()){
                bestRatedHotel =hotel;
            }
        }
        return bestRatedHotel;
    }

    public int calculateTotalRate(Hotel hotel, String[] dates, boolean isRewardCustomer) {
        int totalRate = 0;
        for (String date : dates) {
            if (DateUtil.isWeekend(date)) {
                totalRate += isRewardCustomer ? hotel.getRewardWeekendRate() : hotel.getWeekendRate();
            } else {
                totalRate += isRewardCustomer ? hotel.getRewardWeekdayRate() : hotel.getWeekdayRate();
            }
        }
        return totalRate;
    }

    public Hotel bestRatedHotel(String[] dates){
        Hotel bestHotel=null;
        int bestRated=Integer.MIN_VALUE;
        for(Hotel hotel :hotelList){
            if(hotel.getRating()>bestRated){
                bestHotel=hotel;
                bestRated=hotel.getRating();
            }
        }
        return bestHotel;
    }
    //adding reward customer rates
    public void addHotel(String name, int weekdayRate, int weekendRate, int rewardWeekdayRate, int rewardWeekendRate,int rating) {
        hotelList.add(new Hotel(name, weekdayRate, weekendRate, rewardWeekdayRate, rewardWeekendRate, rating));
    }
    //UC-9 calculate cheapest best rated hotel for reward customer
    private void validateInput(String customerType, String[] dates) {
        if (!(customerType.equalsIgnoreCase("Regular") ||
                customerType.equalsIgnoreCase("Reward"))) {
            throw new HotelReservationException("Invalid Customer Type");
        }
        if (dates == null || dates.length == 0) {
            throw new HotelReservationException("Date Range Cannot Be Empty");
        }
        for (String date : dates) {
            try {
                DateUtil.isWeekend(date);
            } catch (Exception e) {
                throw new HotelReservationException("Invalid Date Format");
            }
        }
    }
    public Hotel findCheapestBestHotelForCustomer(String customerType, String[] dates) {
        validateInput(customerType, dates);
        List<Hotel> cheapestHotels = new ArrayList<>();
        int minTotalRate = Integer.MAX_VALUE;
        for (Hotel hotel : hotelList) {
            int totalRate = 0;
            for (String date : dates) {
                if (DateUtil.isWeekend(date)) {
                    if (customerType.equalsIgnoreCase("Reward"))
                        totalRate += hotel.getRewardWeekendRate();
                    else
                        totalRate += hotel.getWeekendRate();

                }
                else {
                    if (customerType.equalsIgnoreCase("Reward"))
                        totalRate += hotel.getRewardWeekdayRate();
                    else
                        totalRate += hotel.getWeekdayRate();
                }
            }
            if (totalRate < minTotalRate) {
                minTotalRate = totalRate;
                cheapestHotels.clear();
                cheapestHotels.add(hotel);
            }
            else if (totalRate == minTotalRate) {
                cheapestHotels.add(hotel);
            }
        }
        Hotel bestRatedHotel = cheapestHotels.getFirst();
        for (Hotel hotel : cheapestHotels) {
            if (hotel.getRating() > bestRatedHotel.getRating()) {
                bestRatedHotel = hotel;
            }
        }
        return bestRatedHotel;
    }


}

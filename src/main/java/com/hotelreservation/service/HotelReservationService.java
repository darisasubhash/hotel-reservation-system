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
    //finding the cheapest hotel with best rating
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
    private static final String DATE_PATTERN = "^(0[1-9]|[12][0-9]|3[01])[A-Z][a-z]{2}[0-9]{4}$";
    private static final String CUSTOMER_PATTERN = "^(Regular|Reward)$";
    private void validateInput(String customerType, String[] dates) {
        if (!(customerType.matches(CUSTOMER_PATTERN) ||
                customerType.equalsIgnoreCase("Reward"))) {
            throw new HotelReservationException("Invalid Customer Type");
        }
        if (dates == null || dates.length == 0) {
            throw new HotelReservationException("Date Range Cannot Be Empty");
        }
        for (String date : dates) {
            if(!date.matches(DATE_PATTERN)){
                throw new HotelReservationException("Invalid Dat Format");
            }
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
    //UC-10 Finding the cheapest best hotel for customer using streams
    public Hotel findCheapestBestHotelUsingStreams(String customerType, String[] dates) {
        validateInput(customerType, dates);
        return hotelList.stream()
                .sorted((h1, h2) -> {
                    int total1 = calculateTotalRate(h1, dates, customerType.equalsIgnoreCase("Reward"));
                    int total2 = calculateTotalRate(h2, dates, customerType.equalsIgnoreCase("Reward"));
                    if (total1 != total2) return Integer.compare(total1, total2);
                    return Integer.compare(h2.getRating(), h1.getRating());})
                .findFirst()
                .orElseThrow(() -> new HotelReservationException("No Hotels Available"));
    }




}

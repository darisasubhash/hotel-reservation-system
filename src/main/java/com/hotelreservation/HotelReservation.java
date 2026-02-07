package com.hotelreservation;

import com.hotelreservation.service.HotelReservationService;

public class HotelReservation {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Reservation System... ");
        HotelReservationService service = new HotelReservationService();
        service.addHotel("Lakewood",110,90);
        service.addHotel("Bridgewood",160,60);
        service.addHotel("Ridgewood",220,150);
        System.out.println("Hotels added successfully ");
    }
}

package com.hotelreservation;
import com.hotelreservation.model.Hotel;
import com.hotelreservation.service.HotelReservationService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class HotelReservationTest {
    //UC-1 Add Hotel
    @Test
    public void givenHotelDetailsReturnsCountWhenAdded(){
        HotelReservationService service=new HotelReservationService();
        service.addHotel("Lakewood",110,90);
        Assert.assertEquals(1,service.getHotels().size());
    }
    //UC-2 find cheapest hotel
    @Test
    public void givenDateRangeReturnCheapestHotel(){
        HotelReservationService service = new HotelReservationService();
        service.addHotel("Lakewood", 110, 90);
        service.addHotel("Bridgewood", 160, 60);
        service.addHotel("Ridgewood", 220, 150);
        Hotel cheapestHotel=service.findCheapestHotel(2);
        Assert.assertEquals("Lakewood",cheapestHotel.getName());
    }
    //UC-3 ability to add weekday and weekend
    @Test
    public void givenHotelWhenAddedStoreWeekdayAndWeekendRates() {
        HotelReservationService service = new HotelReservationService();
        service.addHotel("Lakewood", 110, 90);
        Hotel hotel = service.getHotels().get(0);
        Assert.assertEquals("Lakewood", hotel.getName());
        Assert.assertEquals(110, hotel.getWeekdayRate());
        Assert.assertEquals(90, hotel.getWeekendRate());
    }
    //UC-4 find the cheapest hotel based on weekday and weekend
    @Test
    public void givenDatesReturnCheapestOnWeekdayAndWeekend(){
        HotelReservationService service = new HotelReservationService();
        service.addHotel("Lakewood", 110, 90);
        service.addHotel("Bridgewood", 150, 50);
        service.addHotel("Ridgewood", 220, 150);
        String[] dates ={"11Sep2020","12Sep2020"};
        List<Hotel> cheapestHotels = service.findCheapestHotelByDates(dates);
        Assert.assertEquals(2,cheapestHotels.size());
        Assert.assertEquals("Lakewood", cheapestHotels.get(0).getName());
        Assert.assertEquals("Bridgewood", cheapestHotels.get(1).getName());
    }
    //UC-5 add hotel with rating
    @Test
    public void givenHotel_WhenAdded_ShouldStoreRating() {
        HotelReservationService service = new HotelReservationService();
        service.addHotel("Lakewood", 110, 90, 3);
        Hotel hotel = service.getHotels().get(0);
        Assert.assertEquals("Lakewood", hotel.getName());
        Assert.assertEquals(3, hotel.getRating());
    }
    //Uc-6 finding cheapest hotel with best rating
    @Test
    public void givenDates_ShouldReturnCheapestBestRatedHotel() {

        HotelReservationService service = new HotelReservationService();

        service.addHotel("Lakewood", 110, 90, 3);
        service.addHotel("Bridgewood", 150, 50, 4);
        service.addHotel("Ridgewood", 220, 150, 5);

        String[] dates = {"11Sep2020", "12Sep2020"};

        Hotel hotel = service.findCheapestBestHotel(dates);
        int totalRate = service.calculateTotalRate(hotel, dates);

        Assert.assertEquals("Bridgewood", hotel.getName());
        Assert.assertEquals(4, hotel.getRating());
        Assert.assertEquals(200, totalRate);
    }
    //UC-7 Finfing the best rated hotel for given dates
    @Test
    public void givenDatesReturnBestRatedHotel(){
        HotelReservationService service=new HotelReservationService();
        service.addHotel("Lakewood", 110, 90, 3);
        service.addHotel("Bridgewood", 150, 50, 4);
        service.addHotel("Ridgewood", 220, 150, 5);
        String[] dates = {"11Sep2020", "12Sep2020"};
        Hotel hotel=service.bestRatedHotel(dates);
        int totalRate=service.calculateTotalRate(hotel,dates);
        Assert.assertEquals("Ridgewood",hotel.getName());
        Assert.assertEquals(370,totalRate);
    }
    @Test
    public void givenHotelsWhenRewardRatesAddedStoreCorrectly() {
        HotelReservationService service = new HotelReservationService();
        service.addHotel("Lakewood", 110, 90, 80, 80, 3);
        service.addHotel("Bridgewood", 150, 50, 110, 50, 4);
        service.addHotel("Ridgewood", 220, 150, 100, 40, 5);
        Hotel lakewood = service.getHotels().get(0);
        Assert.assertEquals(80, lakewood.getRewardWeekdayRate());
        Assert.assertEquals(80, lakewood.getRewardWeekendRate());
    }
}

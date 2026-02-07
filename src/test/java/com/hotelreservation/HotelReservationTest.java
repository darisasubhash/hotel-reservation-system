package com.hotelreservation;
import com.hotelreservation.model.Hotel;
import com.hotelreservation.service.HotelReservationService;
import org.junit.Assert;
import org.junit.Test;

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

}

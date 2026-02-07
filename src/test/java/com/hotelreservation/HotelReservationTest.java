package com.hotelreservation;
import com.hotelreservation.service.HotelReservationService;
import org.junit.Assert;
import org.junit.Test;

public class HotelReservationTest {
    //Uc-1 Add Hotel
    @Test
    public void givenHotelDetailsReturnsCountWhenAdded(){
        HotelReservationService service=new HotelReservationService();
        service.addHotel("Lakewood",110,90);
        Assert.assertEquals(1,service.getHotels().size());
    }

}

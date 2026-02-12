package com.hotelreservation.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {
    private static final DateTimeFormatter formatter=DateTimeFormatter.ofPattern("ddMMMyyyy", Locale.ENGLISH);
    public static boolean isWeekend(String date){
        LocalDate localDate=LocalDate.parse(date,formatter);
        DayOfWeek day = localDate.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}

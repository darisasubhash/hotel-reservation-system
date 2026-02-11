package com.hotelreservation.model;

public class Hotel {
    private String name;
    private int weekdayRate;
    private int weekendRate;

    private int rewardWeekdayRate;
    private int rewardWeekendRate;

    private int rating;

    public Hotel(String name, int weekdayRate, int weekendRate, int rewardWeekdayRate, int rewardWeekendRate,int rating) {
        this.name = name;
        this.weekdayRate = weekdayRate;
        this.weekendRate = weekendRate;
        this.rewardWeekdayRate=rewardWeekdayRate;
        this.rewardWeekendRate=rewardWeekendRate;
        this.rating = rating;
    }
    public Hotel(String name, int weekdayRate, int weekendRate,int rating) {
        this.name = name;
        this.weekdayRate = weekdayRate;
        this.weekendRate = weekendRate;
        this.rewardWeekdayRate=0;
        this.rewardWeekendRate=0;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeekdayRate() {
        return weekdayRate;
    }

    public void setWeekdayRate(int weekdayRate) {
        this.weekdayRate = weekdayRate;
    }

    public int getWeekendRate() {
        return weekendRate;
    }

    public void setWeekendRate(int weekendRate) {
        this.weekendRate = weekendRate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRewardWeekdayRate() {
        return rewardWeekdayRate;
    }

    public void setRewardWeekdayRate(int rewardWeekdayRate) {
        this.rewardWeekdayRate = rewardWeekdayRate;
    }

    public int getRewardWeekendRate() {
        return rewardWeekendRate;
    }

    public void setRewardWeekendRate(int rewardWeekendRate) {
        this.rewardWeekendRate = rewardWeekendRate;
    }
}

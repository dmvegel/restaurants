package com.github.dmvegel.restaurants.restaurant.to;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class VoteHistoryTO {
    @NotNull
    Integer restaurantId;

    @NotNull
    LocalDate date;

    public VoteHistoryTO(Integer restaurantId, LocalDate date) {
        this.restaurantId = restaurantId;
        this.date = date;
    }

    @Override
    public String toString() {
        return "VoteHistoryTO{" +
                "restaurantId=" + restaurantId +
                ", date=" + date +
                '}';
    }
}

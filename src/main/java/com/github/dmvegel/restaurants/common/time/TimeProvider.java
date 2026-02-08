package com.github.dmvegel.restaurants.common.time;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class TimeProvider {
    public static final LocalTime CHANGE_DEADLINE = LocalTime.of(11, 0);

    public LocalDate dateNow() {
        return LocalDate.now();
    }

    public LocalDateTime dateTimeNow() {
        return LocalDateTime.now();
    }
}

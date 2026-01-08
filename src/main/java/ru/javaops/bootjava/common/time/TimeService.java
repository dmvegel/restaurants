package ru.javaops.bootjava.common.time;

import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class TimeService {
    public static final LocalTime CHANGE_DEADLINE = LocalTime.of(11, 0);

    public LocalTime now() {
        return LocalTime.now();
    }
}

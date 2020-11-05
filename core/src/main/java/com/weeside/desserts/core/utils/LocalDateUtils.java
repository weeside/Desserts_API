package com.weeside.desserts.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateUtils {

    public static LocalDateTime to(LocalDate localDate) {
        LocalTime localTime = LocalTime.of(0, 0, 0);
        return LocalDateTime.of(localDate, localTime);
    }
}

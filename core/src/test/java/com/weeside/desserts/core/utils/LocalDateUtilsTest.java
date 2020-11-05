package com.weeside.desserts.core.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateUtilsTest {

    @Test
    void to() {
        // given
        LocalDate localDate = LocalDate.of(2020, 11, 1);

        // when
        LocalDateTime actual = LocalDateUtils.to(localDate);

        // then
        LocalDateTime expected = LocalDateTime.of(localDate, LocalTime.of(0, 0, 0));
        assertThat(actual).isEqualTo(expected);
    }
}
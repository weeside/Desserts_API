package com.weeside.desserts.external.question.service;

import com.weeside.desserts.core.RandomGenerator;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomGeneratorTest {

    private RandomGenerator dut;

    @BeforeEach
    void setUp() {
        dut = new RandomGenerator();
    }

    @Test
    @DisplayName("upper만큼 숫자를 중복없이 랜덤으로 생성한다.")
    void generate() {
        // when
        int upper = 20;
        List<Integer> actual = dut.generateRandomNumbers(upper);

        // then
        Set<Integer> filteredRandomValues = Sets.newHashSet(actual);
        assertThat(actual.size()).isEqualTo(filteredRandomValues.size());
        actual.forEach(randomValue -> assertTrue(randomValue < upper));
    }
}
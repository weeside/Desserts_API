package com.weeside.desserts.external.insight.component;

import com.weeside.desserts.domain.insight.InsightCategory;
import com.weeside.desserts.external.insight.vo.ResultStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LessThanLastWeekInsightDeciderTest {

    private LessThanLastWeekInsightDecider dut;

    @BeforeEach
    void setUp() {
        dut = new LessThanLastWeekInsightDecider();
    }

    @ParameterizedTest
    @MethodSource
    void decide(double lastWeek, double thisWeek, double lastMonth, double thisMonth, InsightCategory expected) {
        // given
        ResultStatistics resultStatistics = ResultStatistics.builder()
                .lastWeek(lastWeek)
                .thisWeek(thisWeek)
                .lastMonth(lastMonth)
                .thisMonth(thisMonth)
                .build();

        // when
        InsightCategory actual = dut.decide(resultStatistics);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> decide() {
        return Stream.of(
                Arguments.of(5.1, 5.0, 0, 0, InsightCategory.LESS_THAN_LAST_WEEK),
                Arguments.of(2.0, 1.0, 0, 0, InsightCategory.LESS_THAN_LAST_WEEK),
                Arguments.of(9.0, 8.0, 0, 0, InsightCategory.LESS_THAN_LAST_WEEK),
                Arguments.of(0, 5.0, 0, 0, null),
                Arguments.of(0, 5.1, 0, 0, null),
                Arguments.of(4.9, 5.0, 3.0, 0, null)
        );
    }
}
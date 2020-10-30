package com.weeside.desserts.external.insight.component;

import com.weeside.desserts.domain.insight.InsightCategory;
import com.weeside.desserts.external.insight.vo.ResultStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class InsightDecidersTest {

    private InsightDeciders dut;

    @BeforeEach
    void setUp() {
        List<InsightDecider> insightDeciders = Arrays.asList(
                new FirstGoodInsightDecider(),
                new FirstBadInsightDecider(),
                new LessThanLastWeekInsightDecider(),
                new BetterThanLastWeekInsightDecider(),
                new LessThanLastMonthAndLessThanLastWeekInsightDecider(),
                new BetterThanLastWeekAndLessThanLastMonthInsightDecider(),
                new LessThanLastWeekAndBetterThanLastMonthInsightDecider(),
                new BetterThanLastWeekAndBetterThanLastMonthInsightDecider()
        );

        dut = new InsightDeciders(insightDeciders);
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
            Arguments.of(0, 5.1, 0, 0, InsightCategory.GOOD_START),
            Arguments.of(0, 4.9, 0, 0, InsightCategory.BAD_START),
            Arguments.of(5.0, 4.9, 0, 0, InsightCategory.LESS_THAN_LAST_WEEK),
            Arguments.of(4.9, 5.0, 0, 0, InsightCategory.BETTER_THAN_LAST_WEEK),
            Arguments.of(5.0, 4.9, 5.0, 4.9, InsightCategory.LESS_THAN_LAST_WEEK_AND_BAD_LAST_MONTH),
            Arguments.of(4.9, 5.0, 5.0, 4.9, InsightCategory.BETTER_THAN_LAST_WEEK_AND_BAD_LAST_MONTH),
            Arguments.of(5.0, 4.0, 4.9, 5.0, InsightCategory.LESS_THAN_LAST_WEEK_AND_GOOD_LAST_MONTH),
            Arguments.of(4.9, 5.0, 4.9, 5.0, InsightCategory.BETTER_THAN_LAST_WEEK_AND_GOOD_LAST_MONTH)
        );
    }
}
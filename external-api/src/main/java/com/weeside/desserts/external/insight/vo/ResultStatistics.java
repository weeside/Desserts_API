package com.weeside.desserts.external.insight.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResultStatistics {
    private final double thisWeek;
    private final double lastWeek;
    private final double thisMonth;
    private final double lastMonth;

    @Builder
    public ResultStatistics(double thisWeek, double lastWeek, double thisMonth, double lastMonth) {
        this.thisWeek = thisWeek;
        this.lastWeek = lastWeek;
        this.thisMonth = thisMonth;
        this.lastMonth = lastMonth;
    }

    public boolean isThisWeekBetterThan(double point) {
        return thisWeek > point;
    }

    public boolean isThisWeekBetterThanLastWeek() {
        return thisWeek > lastWeek;
    }

    public boolean isThisMonthBatterThanLastMonth() {
        return thisMonth > lastMonth;
    }

    public boolean isExistLastWeek() {
        return lastWeek != 0;
    }

    public boolean isExistLastMonth() {
        return lastMonth != 0;
    }
}

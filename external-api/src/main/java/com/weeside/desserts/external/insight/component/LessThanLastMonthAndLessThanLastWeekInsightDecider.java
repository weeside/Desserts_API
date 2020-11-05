package com.weeside.desserts.external.insight.component;

import com.weeside.desserts.domain.insight.InsightCategory;
import com.weeside.desserts.domain.memberstat.MemberStatistics;

public class LessThanLastMonthAndLessThanLastWeekInsightDecider implements InsightDecider {

    @Override
    public InsightCategory decide(MemberStatistics memberStatistics) {
        if (memberStatistics.isExistLastMonth() &&
                !memberStatistics.isThisMonthBatterThanLastMonth() &&
                !memberStatistics.isThisWeekBetterThanLastWeek()) {
            return InsightCategory.LESS_THAN_LAST_WEEK_AND_BAD_LAST_MONTH;
        }

        return null;
    }
}

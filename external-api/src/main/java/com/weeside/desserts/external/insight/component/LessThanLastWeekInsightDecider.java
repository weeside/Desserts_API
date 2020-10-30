package com.weeside.desserts.external.insight.component;

import com.weeside.desserts.domain.insight.InsightCategory;
import com.weeside.desserts.external.insight.vo.ResultStatistics;

public class LessThanLastWeekInsightDecider implements InsightDecider {

    @Override
    public InsightCategory decide(ResultStatistics resultStatistics) {
        if (resultStatistics.isExistLastWeek() &&
                !resultStatistics.isExistLastMonth() &&
                !resultStatistics.isThisWeekBetterThanLastWeek()) {
            return InsightCategory.LESS_THAN_LAST_WEEK;
        }

        return null;
    }
}

package com.weeside.desserts.external.insight.component;

import com.weeside.desserts.domain.insight.Insight;
import com.weeside.desserts.domain.insight.InsightCategory;
import com.weeside.desserts.external.insight.vo.ResultStatistics;

public class FirstBadInsightDecider implements InsightDecider {

    @Override
    public InsightCategory decide(ResultStatistics resultStatistics) {
        if (!resultStatistics.isExistLastWeek() && !resultStatistics.isThisWeekBetterThan(Insight.BASE_POINT)) {
            return InsightCategory.BAD_START;
        }

        return null;
    }
}

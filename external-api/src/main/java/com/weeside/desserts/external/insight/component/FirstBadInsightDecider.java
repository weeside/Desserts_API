package com.weeside.desserts.external.insight.component;

import com.weeside.desserts.domain.insight.Insight;
import com.weeside.desserts.domain.insight.InsightCategory;
import com.weeside.desserts.domain.memberstat.MemberStatistics;

public class FirstBadInsightDecider implements InsightDecider {

    @Override
    public InsightCategory decide(MemberStatistics memberStatistics) {
        if (!memberStatistics.isExistLastWeek() && !memberStatistics.isThisWeekBetterThan(Insight.BASE_POINT)) {
            return InsightCategory.BAD_START;
        }

        return null;
    }
}

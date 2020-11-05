package com.weeside.desserts.external.insight.component;

import com.weeside.desserts.domain.insight.InsightCategory;
import com.weeside.desserts.domain.memberstat.MemberStatistics;

public interface InsightDecider {
    InsightCategory decide(MemberStatistics memberStatistics);
}

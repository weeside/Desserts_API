package com.weeside.desserts.external.insight.component;

import com.weeside.desserts.domain.insight.InsightCategory;
import com.weeside.desserts.external.insight.vo.ResultStatistics;

public interface InsightDecider {

    InsightCategory decide(ResultStatistics resultStatistics);
}

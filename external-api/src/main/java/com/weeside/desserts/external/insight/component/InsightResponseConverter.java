package com.weeside.desserts.external.insight.component;

import com.weeside.desserts.domain.insight.Insight;
import com.weeside.desserts.external.insight.dto.InsightResponse;
import org.springframework.stereotype.Component;

@Component
public class InsightResponseConverter {

    public InsightResponse convert(Insight insight) {
        return InsightResponse.builder()
                .message(insight.getContents())
                .build();
    }
}

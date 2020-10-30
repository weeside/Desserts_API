package com.weeside.desserts.external.insight.component;

import com.weeside.desserts.domain.insight.InsightCategory;
import com.weeside.desserts.exception.DessertsException;
import com.weeside.desserts.external.insight.vo.ResultStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class InsightDeciders {

    private final List<InsightDecider> insightDeciders;

    public InsightCategory decide(ResultStatistics resultStatistics) {
        return insightDeciders.stream()
                .map(insightDecider -> insightDecider.decide(resultStatistics))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new DessertsException(
                        String.format("알맞는 인사이트를 찾을 수 없습니다. %s", resultStatistics)
                ));
    }
}

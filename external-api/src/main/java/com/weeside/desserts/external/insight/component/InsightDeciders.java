package com.weeside.desserts.external.insight.component;

import com.weeside.desserts.domain.insight.InsightCategory;
import com.weeside.desserts.domain.memberstat.MemberStatistics;
import com.weeside.desserts.exception.DessertsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class InsightDeciders {

    private final List<InsightDecider> insightDeciders;

    public InsightCategory decide(MemberStatistics memberStatistics) {
        return insightDeciders.stream()
                .map(insightDecider -> insightDecider.decide(memberStatistics))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new DessertsException(
                        String.format("알맞는 인사이트를 찾을 수 없습니다. 저번주: %f, 이번주: %f, 저번달: %f, 이번달: %f",
                                memberStatistics.getLastWeek(), memberStatistics.getThisWeek(),
                                memberStatistics.getLastMonth(), memberStatistics.getThisMonth())
                ));
    }
}

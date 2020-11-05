package com.weeside.desserts.external.insight.service;

import com.weeside.desserts.domain.insight.Insight;
import com.weeside.desserts.domain.insight.InsightCategory;
import com.weeside.desserts.domain.insight.InsightRepository;
import com.weeside.desserts.domain.memberstat.MemberStatistics;
import com.weeside.desserts.domain.memberstat.MemberStatisticsRepository;
import com.weeside.desserts.exception.DessertsException;
import com.weeside.desserts.external.insight.component.InsightDeciders;
import com.weeside.desserts.external.insight.component.InsightResponseConverter;
import com.weeside.desserts.external.insight.dto.InsightResponse;
import com.weeside.desserts.core.RandomGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class InsightService {
    private final MemberStatisticsRepository memberStatisticsRepository;
    private final InsightDeciders insightDeciders;
    private final InsightRepository insightRepository;
    private final RandomGenerator randomGenerator;
    private final InsightResponseConverter insightResponseConverter;

    public CompletableFuture<InsightResponse> getInsight(Long memberId) {
        MemberStatistics memberStatistics = memberStatisticsRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DessertsException(
                        String.format("점수가 존재하지 않습니다. memberId: %d", memberId)
                ));

        InsightCategory insightCategory = insightDeciders.decide(memberStatistics);
        List<Insight> insights = insightRepository.findAllByCategory(insightCategory);
        Integer index = randomGenerator.generateRandomNumber(insights.size());

        return CompletableFuture.completedFuture(
                insightResponseConverter.convert(insights.get(index))
        );
    }
}

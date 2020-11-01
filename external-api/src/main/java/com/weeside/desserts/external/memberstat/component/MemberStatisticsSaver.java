package com.weeside.desserts.external.memberstat.component;

import com.weeside.desserts.domain.memberstat.MemberStatistics;
import com.weeside.desserts.domain.memberstat.MemberStatisticsRepository;
import com.weeside.desserts.domain.result.Result;
import com.weeside.desserts.domain.result.ResultRepository;
import com.weeside.desserts.core.utils.LocalDateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberStatisticsSaver {
    private final MemberStatisticsRepository memberStatisticsRepository;
    private final ResultRepository resultRepository;

    public void saveByResultCreation(Long memberId, Result result) {
        Optional<MemberStatistics> maybeMemberStatistics = memberStatisticsRepository.findByMemberId(memberId);

        if (maybeMemberStatistics.isPresent()) {
            update(maybeMemberStatistics.get());
            return;
        }

        memberStatisticsRepository.save(
                MemberStatistics.newInstance(memberId, result.getPoint())
        );
    }

    private void update(MemberStatistics memberStatistics) {
        List<Result> resultsForOneMonth = resultRepository.findAllByCreatedAt(LocalDateUtils.to(LocalDate.now()));

        double thisWeek = calculateWeekPoint(resultsForOneMonth);
        double thisMonth = calculateMonthPoint(resultsForOneMonth);

        memberStatistics.update(thisWeek, thisMonth);
    }

    private double calculateWeekPoint(List<Result> resultsForOneMonth) {
        LocalDate aWeekAgo = LocalDate.now().minusWeeks(1);

        List<Integer> aWeekPoints = resultsForOneMonth.stream()
                .filter(result -> result.getCreatedAt().isAfter(LocalDateUtils.to(aWeekAgo)))
                .map(Result::getPoint)
                .collect(Collectors.toList());

        Integer aWeekTotalPoint = aWeekPoints.stream().reduce(0, Integer::sum);
        return (double) aWeekTotalPoint / aWeekPoints.size();
    }

    private double calculateMonthPoint(List<Result> resultsForOneMonth) {
        List<Integer> aMonthPoints = resultsForOneMonth.stream()
                .map(Result::getPoint)
                .collect(Collectors.toList());

        Integer aMonthTotalPoint = aMonthPoints.stream().reduce(0, Integer::sum);
        return (double) aMonthTotalPoint / aMonthPoints.size();
    }
}

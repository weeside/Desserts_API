package com.weeside.desserts.external.memberstat.component;

import com.weeside.desserts.domain.memberstat.MemberStatistics;
import com.weeside.desserts.domain.memberstat.MemberStatisticsRepository;
import com.weeside.desserts.domain.result.Result;
import com.weeside.desserts.domain.result.ResultRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MemberStatisticsSaverTest {

    @InjectMocks
    private MemberStatisticsSaver dut;

    @Mock
    private MemberStatisticsRepository memberStatisticsRepository;

    @Mock
    private ResultRepository resultRepository;

    @Test
    @DisplayName("통계가 존재하지 않다면 통계를 새롭게 생성한다.")
    void create() {
        // given
        Long memberId = 1L;
        int point = 5;
        Result result = Result.builder()
                .memberId(memberId)
                .point(point)
                .build();

        given(memberStatisticsRepository.findByMemberId(eq(memberId))).willReturn(Optional.empty());

        MemberStatistics memberStatistics = MemberStatistics.newInstance(memberId, point);
        given(memberStatisticsRepository.save(any(MemberStatistics.class))).willReturn(memberStatistics);

        // when
        dut.saveByResultCreation(memberId, result);

        // then
        then(memberStatisticsRepository).should().findByMemberId(eq(memberId));
        then(memberStatisticsRepository).should().save(any(MemberStatistics.class));
        then(resultRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("[이번주 Result만 존재] 통계가 존재한다면 새롭게 통계를 업데이트한다.")
    void updateThisWeekStatistics() {
        // given
        Long memberId = 1L;
        int point = 5;
        Result result = Result.builder()
                .memberId(memberId)
                .point(point)
                .build();
        MemberStatistics memberStatistics = mock(MemberStatistics.class);

        given(memberStatisticsRepository.findByMemberId(eq(memberId))).willReturn(Optional.of(memberStatistics));

        List<Integer> points = Arrays.asList(3, 4, 5, 6, 7, 8, 9); // 평균 6
        given(resultRepository.findAllByCreatedAt(any(LocalDateTime.class)))
                .willReturn(dummyResults(memberId, points, LocalDateTime.now()));
        willDoNothing().given(memberStatistics).update(anyDouble(), anyDouble());

        // when
        dut.saveByResultCreation(memberId, result);

        // then
        then(memberStatisticsRepository).should().findByMemberId(eq(memberId));
        then(resultRepository).should().findAllByCreatedAt(any(LocalDateTime.class));

        ArgumentCaptor<Double> thisWeekCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> thisMonthCaptor = ArgumentCaptor.forClass(Double.class);
        then(memberStatistics).should().update(thisWeekCaptor.capture(), thisMonthCaptor.capture());

        assertThat(thisWeekCaptor.getValue()).isEqualTo(6);
        assertThat(thisMonthCaptor.getValue()).isEqualTo(6);
    }

    @Test
    @DisplayName("[이번주를 제외한 이번달의 Result가 있는 경우] 통계가 존재한다면 새롭게 통계를 업데이트한다.")
    void updateThisWeekAndThisMonthStatistics() {
        // given
        Long memberId = 1L;
        int point = 5;
        Result result = Result.builder()
                .memberId(memberId)
                .point(point)
                .build();
        MemberStatistics memberStatistics = mock(MemberStatistics.class);

        given(memberStatisticsRepository.findByMemberId(eq(memberId))).willReturn(Optional.of(memberStatistics));

        LocalDateTime now = LocalDateTime.now();

        List<Integer> points = Arrays.asList(3, 4, 5, 6, 7, 8, 9); // 평균 6
        List<Result> thisWeekPoints = dummyResults(memberId, points, now);
        List<Integer> points2 = Arrays.asList(1, 2, 10); // points + points2 평균 5.5
        List<Result> thisMonthPoints = dummyResults(memberId, points2, now.minusDays(8));
        thisMonthPoints.addAll(thisWeekPoints);

        given(resultRepository.findAllByCreatedAt(any(LocalDateTime.class)))
                .willReturn(thisMonthPoints);
        willDoNothing().given(memberStatistics).update(anyDouble(), anyDouble());

        // when
        dut.saveByResultCreation(memberId, result);

        // then
        then(memberStatisticsRepository).should().findByMemberId(eq(memberId));
        then(resultRepository).should().findAllByCreatedAt(any(LocalDateTime.class));

        ArgumentCaptor<Double> thisWeekCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> thisMonthCaptor = ArgumentCaptor.forClass(Double.class);
        then(memberStatistics).should().update(thisWeekCaptor.capture(), thisMonthCaptor.capture());

        assertThat(thisWeekCaptor.getValue()).isEqualTo(6);
        assertThat(thisMonthCaptor.getValue()).isEqualTo(5.5);
    }

    private List<Result> dummyResults(Long memberId, List<Integer> points, LocalDateTime createdAt) {
        return points.stream()
                .map(point -> {
                    Result result = Result.builder()
                            .memberId(memberId)
                            .point(point)
                            .build();

                    ReflectionTestUtils.setField(result, "createdAt", createdAt);
                    return result;
                })
                .collect(Collectors.toList());
    }
}
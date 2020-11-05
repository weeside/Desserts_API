package com.weeside.desserts.domain.memberstat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(
        name = "MEMBER_STAT"
)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long memberId;

    @Column
    private double lastWeek;

    @Column
    private double thisWeek;

    @Column
    private double lastMonth;

    @Column
    private double thisMonth;

    @Builder
    private MemberStatistics(Long memberId, double lastWeek, double thisWeek, double lastMonth, double thisMonth) {
        this.memberId = memberId;
        this.lastWeek = lastWeek;
        this.thisWeek = thisWeek;
        this.lastMonth = lastMonth;
        this.thisMonth = thisMonth;
    }

    public static MemberStatistics newInstance(Long memberId, double point) {
        return MemberStatistics.builder()
                .memberId(memberId)
                .thisWeek(point)
                .thisMonth(point)
                .build();
    }

    public void update(double thisWeek, double thisMonth) {
        this.thisWeek = thisWeek;
        this.thisMonth = thisMonth;
    }

    public boolean isThisWeekBetterThan(double point) {
        return thisWeek > point;
    }

    public boolean isThisWeekBetterThanLastWeek() {
        return thisWeek > lastWeek;
    }

    public boolean isThisMonthBatterThanLastMonth() {
        return thisMonth > lastMonth;
    }

    public boolean isExistLastWeek() {
        return lastWeek != 0;
    }

    public boolean isExistLastMonth() {
        return lastMonth != 0;
    }
}

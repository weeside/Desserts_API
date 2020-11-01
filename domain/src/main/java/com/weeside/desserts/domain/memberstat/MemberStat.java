package com.weeside.desserts.domain.memberstat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(
        name = "MEMBER_STAT"
)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberStat {

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
    public MemberStat(Long memberId, double lastWeek, double thisWeek, double lastMonth, double thisMonth) {
        this.memberId = memberId;
        this.lastWeek = lastWeek;
        this.thisWeek = thisWeek;
        this.lastMonth = lastMonth;
        this.thisMonth = thisMonth;
    }
}

package com.weeside.desserts.domain.result;

import com.weeside.desserts.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(
        name = "RESULT",
        indexes = {@Index(name = "idx_result_member_id", columnList = "memberId")}
)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Result extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long memberId;

    @Column
    private int point;

    @Builder
    private Result(Long memberId, int point) {
        this.memberId = memberId;
        this.point = point;
    }
}

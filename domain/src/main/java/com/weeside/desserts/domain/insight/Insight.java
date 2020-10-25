package com.weeside.desserts.domain.insight;

import com.weeside.desserts.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(
        name = "INSIGHT",
        indexes = {}
)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Insight extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private InsightCategory category;

    @Column
    private String contents;

    @Builder
    public Insight(InsightCategory category, String contents) {
        this.category = category;
        this.contents = contents;
    }
}

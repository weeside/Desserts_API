package com.weeside.desserts.domain.insite;

import com.weeside.desserts.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(
        name = "INSITE",
        indexes = {}
)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Insite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private InsiteCategory category;

    @Column
    private String contents;

    @Builder
    public Insite(InsiteCategory category, String contents) {
        this.category = category;
        this.contents = contents;
    }
}

package com.weecode.desserts.domain.result;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(
        name = "RESULT",
        indexes = {}
)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double point;

    @Builder
    private Result(Double point) {
        this.point = point;
    }
}

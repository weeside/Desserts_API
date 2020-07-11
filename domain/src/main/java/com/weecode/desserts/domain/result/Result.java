package com.weecode.desserts.domain.result;

import javax.persistence.*;

@Entity
@Table(
        name = "RESULT",
        indexes = {}
)
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
}

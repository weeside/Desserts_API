package com.weecode.desserts.domain.question;

import com.weecode.desserts.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(
        name = "QUESTION",
        indexes = {}
)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column
    private String contents;

    @Column
    private Integer yes;

    @Column
    private Integer no;

    @Builder
    private Question(String contents, Integer yes, Integer no) {
        this.contents = contents;
        this.yes = yes;
        this.no = no;
    }
}
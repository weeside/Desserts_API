package com.weeside.desserts.external.dummy;

import com.weeside.desserts.domain.question.Question;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuestionDummyBuilder {
    public static final int YES = 1;
    public static final int NO = -1;
    public static final String CONTENTS = "contents";

    public static Question create() {
        return Question.builder()
                .contents(CONTENTS)
                .yes(YES)
                .no(NO)
                .build();
    }

    public static List<Question> create(int count) {
        return IntStream.range(0, count)
                .mapToObj(number -> Question.builder()
                        .contents(CONTENTS + number)
                        .yes(YES)
                        .no(NO)
                        .build()
                )
                .collect(Collectors.toList());
    }
}

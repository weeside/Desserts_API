package com.weecode.desserts.external.question.component;

import com.weecode.desserts.domain.question.Question;
import com.weecode.desserts.external.question.dto.QuestionResponse;
import com.weecode.desserts.external.question.dto.QuestionsResponse;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class QuestionResponseConverter {

    public QuestionsResponse convert(List<Question> questions) {
        return QuestionsResponse.builder()
                .questions(questions.stream()
                        .map(this::convert)
                        .collect(toList())
                )
                .build();
    }

    private QuestionResponse convert(Question question) {
        return QuestionResponse.builder()
                .contents(question.getContents())
                .yes(question.getYes())
                .no(question.getNo())
                .build();
    }
}

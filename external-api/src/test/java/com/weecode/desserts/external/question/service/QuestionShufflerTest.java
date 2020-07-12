package com.weecode.desserts.external.question.service;

import com.weecode.desserts.domain.question.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionShufflerTest {

    @InjectMocks
    private QuestionShuffler dut;

    @Mock
    private RandomGenerator randomGenerator;

    @Test
    @DisplayName("Question들을 섞는다.")
    void shuffle() {
        // given
        List<Question> questions = Arrays.asList();
        List<Integer> randomIndexes = Arrays.asList();
        given(randomGenerator.generate(questions.size())).willReturn(randomIndexes);

        // when
        List<Question> actual = dut.shuffle(questions);

        // then
        assertThat(actual).hasSize(questions.size());
        IntStream.range(0, randomIndexes.size())
                .forEach(index -> assertThat(questions).satisfies(originQuestions -> originQuestions.contains(actual.get(index))));
    }
}
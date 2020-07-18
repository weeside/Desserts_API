package com.weecode.desserts.external.question.service;

import com.weecode.desserts.domain.question.Question;
import com.weecode.desserts.domain.question.QuestionRepository;
import com.weecode.desserts.external.dummy.QuestionDummyBuilder;
import com.weecode.desserts.external.question.component.QuestionResponseConverter;
import com.weecode.desserts.external.question.dto.QuestionResponse;
import com.weecode.desserts.external.question.dto.QuestionsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    private QuestionService dut;

    @Mock
    private QuestionRepository questionRepository;

    private QuestionShuffler questionShuffler;

    private QuestionResponseConverter questionResponseConverter;

    @BeforeEach
    void setUp() {
        questionShuffler = new QuestionShuffler(new RandomGenerator());
        questionResponseConverter = new QuestionResponseConverter();
        dut = new QuestionService(questionRepository, questionShuffler, questionResponseConverter);
    }

    @Test
    @DisplayName("등록된 질문을 조회, 셔플하여 전달한다.")
    void readQuestions() {
        // given
        Pageable pageable = PageRequest.of(0, 20);
        Page<Question> page = mock(Page.class);
        given(questionRepository.findAll(any(Pageable.class))).willReturn(page);
        given(page.getContent()).willReturn(QuestionDummyBuilder.create(20));

        // when
        QuestionsResponse actual = dut.readQuestions(pageable).join();

        // then
        assertNotNull(actual);

        List<QuestionResponse> questions = actual.getQuestions();
        assertThat(questions).hasSize(20);

        ArgumentCaptor<Pageable> argumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        then(questionRepository).should().findAll(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getPageNumber()).isEqualTo(0);
        assertThat(argumentCaptor.getValue().getPageSize()).isEqualTo(20);
        then(page).should().getContent();
    }
}
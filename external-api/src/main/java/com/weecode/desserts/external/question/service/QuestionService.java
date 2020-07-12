package com.weecode.desserts.external.question.service;

import com.weecode.desserts.domain.question.Question;
import com.weecode.desserts.domain.question.QuestionRepository;
import com.weecode.desserts.external.question.component.QuestionResponseConverter;
import com.weecode.desserts.external.question.dto.QuestionsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionShuffler questionShuffler;
    private final QuestionResponseConverter questionResponseConverter;

    public QuestionService(QuestionRepository questionRepository,
                           QuestionShuffler questionShuffler,
                           QuestionResponseConverter questionResponseConverter) {
        this.questionRepository = questionRepository;
        this.questionShuffler = questionShuffler;
        this.questionResponseConverter = questionResponseConverter;
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<QuestionsResponse> readQuestions(Pageable pageable) {
        Page<Question> questions = questionRepository.findAll(pageable);
        List<Question> shuffledQuestions = questionShuffler.shuffle(questions.getContent());

        return CompletableFuture.completedFuture(questionResponseConverter.convert(shuffledQuestions));
    }
}

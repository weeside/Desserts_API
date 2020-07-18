package com.weeside.desserts.external.question.service;

import com.weeside.desserts.domain.question.Question;
import com.weeside.desserts.domain.question.QuestionRepository;
import com.weeside.desserts.external.question.component.QuestionResponseConverter;
import com.weeside.desserts.external.question.dto.QuestionsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.weeside.desserts.external.config.AsyncConfig.EXTERNAL_API_ASYNC_THREAD_POOL_NAME;

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

    @Async(EXTERNAL_API_ASYNC_THREAD_POOL_NAME)
    @Transactional(readOnly = true)
    public CompletableFuture<QuestionsResponse> readQuestions(Pageable pageable) {
        Page<Question> questions = questionRepository.findAll(pageable);
        List<Question> shuffledQuestions = questionShuffler.shuffle(questions.getContent());

        QuestionsResponse questionsResponse = questionResponseConverter.convert(shuffledQuestions);

        return CompletableFuture.completedFuture(questionsResponse);
    }
}

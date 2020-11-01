package com.weeside.desserts.external.question.service;

import com.weeside.desserts.domain.question.Question;
import com.weeside.desserts.core.RandomGenerator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionShuffler {
    private final RandomGenerator randomGenerator;

    public QuestionShuffler(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public List<Question> shuffle(List<Question> questions) {
        List<Integer> randomIndexes = randomGenerator.generateRandomNumbers(questions.size());

        return randomIndexes.stream()
                .map(questions::get)
                .collect(Collectors.toList());
    }
}

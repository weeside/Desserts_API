package com.weeside.desserts.external.question.service;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class RandomGenerator {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public List<Integer> generate(int upper) {
        return IntStream.range(0, upper)
                .boxed()
                .sorted(randomSort())
                .collect(Collectors.toList());
    }

    private Comparator<Integer> randomSort() {
        return (prev, next) -> RANDOM.nextInt(2) - 1;
    }
}

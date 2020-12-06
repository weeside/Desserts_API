package com.weeside.desserts.common.component;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class RandomGenerator {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public Integer generateRandomNumber(int upper) {
        return RANDOM.nextInt(upper);
    }

    public List<Integer> generateRandomNumbers(int upper) {
        return IntStream.range(0, upper)
                .boxed()
                .sorted(randomSort())
                .collect(Collectors.toList());
    }

    private Comparator<Integer> randomSort() {
        return (prev, next) -> RANDOM.nextInt(2) - 1;
    }
}

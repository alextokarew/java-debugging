package com.github.alextokarew.javadebugging.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class StreamChain {
    static void main() {
        int skip = 5;
        int limit = 10;
        IntStream.iterate(0, n -> n + 1)
                .skip(skip)
                .limit(limit)
                .filter(StreamChain::isPrime)
                .forEach(System.out::println);

        IO.println("abahaba".strip());
    }

    static boolean isPrime(int candidate) {
        return IntStream.rangeClosed(2, (int) Math.sqrt(candidate))
                .noneMatch(n -> (candidate % n == 0));
    }
}

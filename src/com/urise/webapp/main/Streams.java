package com.urise.webapp.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Streams {
    private static final int[] values = new int[]{1,4,6,6,5,4,4,4,8,8};
    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(minValue(values));
        randomValues();
        System.out.println(oddOrEven(list));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (start, val) ->  start * 10 + val);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream()
                .mapToInt(num -> num.intValue())
                .sum();

        return integers.stream()
                .filter(s -> (sum % 2 == 0) ? (s % 2 != 0) : (s % 2 == 0))
                .collect(Collectors.toList());
    }

    private static void randomValues() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            list.add(random.nextInt(50));
        }
    }
}

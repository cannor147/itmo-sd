package com.github.cannor147.itmo.software.lab02;

import com.github.cannor147.itmo.software.lab02.counter.FrequencyCounter;
import com.github.cannor147.itmo.software.lab02.counter.impl.FrequencyCounterImpl;

import java.util.Arrays;
import java.util.function.Function;

public class Application {
    public static void main(String[] args) {
        final String hashTag = parseArgument(args, 0, Function.identity());
        final int hours = parseArgument(args, 1, Integer::parseInt);
        final FrequencyCounter frequencyCounter = new FrequencyCounterImpl();
        final int[] frequencies = frequencyCounter.count(hashTag, hours);
        System.out.println(Arrays.toString(frequencies));
    }

    private static <T> T parseArgument(String[] args, int index, Function<String, T> transformer) {
        if (args.length <= index) {
            throw new IllegalArgumentException("Missed argument on position " + (index + 1) + ".");
        }

        try {
            return transformer.apply(args[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid argument on position" + (index + 1) + ".", e);
        }
    }
}

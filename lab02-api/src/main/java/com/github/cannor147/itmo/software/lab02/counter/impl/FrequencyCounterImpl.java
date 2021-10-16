package com.github.cannor147.itmo.software.lab02.counter.impl;

import com.github.cannor147.itmo.software.lab02.counter.FrequencyCounter;

public class FrequencyCounterImpl implements FrequencyCounter {
    @Override
    public int[] count(String hashTag, int hours) {
        return new int[hours];
    }
}

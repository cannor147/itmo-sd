package com.github.cannor147.itmo.sd.lab08.event;

import java.util.Map;

public interface EventsStatistic {
    void incEvent(String name);

    double getEventStatisticByName(String name);

    Map<String, Double> getAllEventStatistic();

    void printStatistic();

    void clear();
}
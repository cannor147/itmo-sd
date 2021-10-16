package com.github.cannor147.itmo.software.lab02.counter.impl;

import com.github.cannor147.itmo.software.lab02.counter.FrequencyCounter;
import com.github.cannor147.itmo.software.lab02.vk.VkRequester;
import com.github.cannor147.itmo.software.lab02.vk.impl.VkRequesterImpl;
import one.util.streamex.IntStreamEx;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Date;

public class FrequencyCounterImpl implements FrequencyCounter {
    @Override
    public int[] count(String hashTag, int hours) {
        if (hours < 1 || hours > 24) {
            throw new IllegalArgumentException("Number of hours should be in range from 1 to 24");
        }

        final LocalDateTime currentDate = LocalDateTime.now();
        final VkRequester vkRequester = new VkRequesterImpl();
        return IntStreamEx.range(hours + 1).boxed()
                .map(currentDate::minusHours)
                .map(date -> date.atZone(ZoneId.systemDefault()))
                .map(ChronoZonedDateTime::toInstant)
                .map(Date::from)
                .pairMap((endDate, startDate) -> vkRequester.count(hashTag, startDate, endDate))
                .mapToInt(x -> x)
                .toArray();
    }
}

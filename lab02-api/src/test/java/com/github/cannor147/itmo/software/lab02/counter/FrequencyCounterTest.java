package com.github.cannor147.itmo.software.lab02.counter;

import com.github.cannor147.itmo.software.lab02.counter.impl.FrequencyCounterImpl;
import com.github.cannor147.itmo.software.lab02.vk.VkRequester;
import one.util.streamex.EntryStream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FrequencyCounterTest {
    private static final String INVALID_HOURS_EXCEPTION_MESSAGE = "Number of hours should be in range from 1 to 24";

    private Random random;
    private VkRequester vkRequester;
    private FrequencyCounter frequencyCounter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.random = new Random();
        this.vkRequester = mock(VkRequester.class);
        this.frequencyCounter = new FrequencyCounterImpl(vkRequester);
    }

    @Test
    public void testHours() {
        final RuntimeException vkRequesterException = new IllegalArgumentException("Unexpected calling vk requester.");
        when(vkRequester.count(anyString(), anyObject(), anyObject())).thenThrow(vkRequesterException);
        assertThatThrownBy(() -> frequencyCounter.count("#tiktok", 0))
                .withFailMessage(INVALID_HOURS_EXCEPTION_MESSAGE);
        assertThatThrownBy(() -> frequencyCounter.count("#tiktok", 24))
                .withFailMessage(vkRequesterException.getMessage());
        assertThatThrownBy(() -> frequencyCounter.count("#tiktok", 25))
                .withFailMessage(INVALID_HOURS_EXCEPTION_MESSAGE);
    }

    @Test
    public void testCount() {
        final Map<Date, Integer> startDateToCountMap = new HashMap<>();
        when(vkRequester.count(anyString(), anyObject(), anyObject())).thenAnswer(invocation -> {
            final Date startDate = invocation.getArgumentAt(1, Date.class);
            final int count = random.nextInt(100);
            startDateToCountMap.put(startDate, count);
            return count;
        });

        final int[] actualResult = frequencyCounter.count("#tiktok", 12);
        final int[] expectedResult = EntryStream.of(startDateToCountMap)
                .reverseSorted(Map.Entry.comparingByKey())
                .mapToInt(Map.Entry::getValue)
                .toArray();
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}

package com.github.cannor147.itmo.software.lab02.vk.impl;

import com.github.cannor147.itmo.software.lab02.vk.VkRequester;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.newsfeed.responses.SearchResponse;
import org.apache.logging.log4j.util.Strings;

import java.util.Date;

public class VkRequesterImpl implements VkRequester {
    private static final int USER_ID = 7966809;
    private static final String ACCESS_TOKEN = "e80d8a02e80d8a02e80d8a027ce8741a5bee80de80d8a028957b3f78c4cc6f2a842cf14";

    private final VkApiClient vkApiClient;
    private final UserActor userActor;

    public VkRequesterImpl() {
        this.vkApiClient = new VkApiClient(HttpTransportClient.getInstance());
        this.userActor = new UserActor(USER_ID, ACCESS_TOKEN);
    }

    @Override
    public int count(String key, Date startDate, Date endDate) {
        try {
            int result = 0;
            String startFrom = "";
            do {
                final SearchResponse searchResponse = vkApiClient.newsfeed()
                        .search(userActor)
                        .q(key)
                        .count(200)
                        .startTime((int) startDate.toInstant().getEpochSecond())
                        .endTime((int) endDate.toInstant().getEpochSecond())
                        .startFrom(startFrom)
                        .execute();
                startFrom = searchResponse.getNextFrom();
                result += searchResponse.getItems().size();
            } while (!Strings.isEmpty(startFrom));
            return result;
        } catch (ApiException | ClientException e) {
            throw new RuntimeException("Problems with connection to vk.", e);
        }
    }
}

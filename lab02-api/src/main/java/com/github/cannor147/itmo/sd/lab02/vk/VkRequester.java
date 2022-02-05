package com.github.cannor147.itmo.sd.lab02.vk;

import java.util.Date;

public interface VkRequester {
    int count(String key, Date startTime, Date endTime);
}

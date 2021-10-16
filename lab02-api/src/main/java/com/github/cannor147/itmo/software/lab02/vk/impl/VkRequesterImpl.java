package com.github.cannor147.itmo.software.lab02.vk.impl;

import com.github.cannor147.itmo.software.lab02.vk.VkRequester;

import java.util.Date;

public class VkRequesterImpl implements VkRequester {
    @Override
    public int count(String key, Date startTime, Date endTime) {
        System.out.println(startTime + " | " + endTime);
        return startTime.hashCode() % 10;
    }
}

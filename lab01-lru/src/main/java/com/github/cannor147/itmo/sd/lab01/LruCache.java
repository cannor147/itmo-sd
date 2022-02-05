package com.github.cannor147.itmo.sd.lab01;

public interface LruCache<K, V> {
    V get(K key);
    void put(K key, V value);
    int size();
    int capacity();
    void changeCapacity(int capacity);
}

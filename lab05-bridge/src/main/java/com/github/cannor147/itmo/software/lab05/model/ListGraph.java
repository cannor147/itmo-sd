package com.github.cannor147.itmo.software.lab05.model;

import com.github.cannor147.itmo.software.lab05.api.DrawingApi;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class ListGraph implements Graph {
    private final int size;
    private final List<Pair<Integer, Integer>> edges;

    public ListGraph(int size, List<Integer> firstVerteces, List<Integer> secondVerteces) {
        this(size, StreamEx.zip(firstVerteces, secondVerteces, Pair::of).toList());
    }

    public ListGraph(int size, int[] firstVerteces, int[] secondVerteces) {
        this(size, stream(firstVerteces).boxed().collect(toList()), stream(secondVerteces).boxed().collect(toList()));
    }

    @Override
    public void draw(DrawingApi drawingApi) {
        // No operations.
    }

    @Override
    public int size() {
        return size;
    }
}

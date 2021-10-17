package com.github.cannor147.itmo.software.lab05.model;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MatrixGraph implements Graph {
    private final boolean[][] adjacencyMatrix;

    @Override
    public int size() {
        return adjacencyMatrix.length;
    }

    @Override
    public List<Pair<Integer, Integer>> getEdges() {
        final List<Pair<Integer, Integer>> edges = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[0].length; j++) {
                if (adjacencyMatrix[i][j]) {
                    edges.add(Pair.of(i, j));
                }
            }
        }
        return edges;
    }
}

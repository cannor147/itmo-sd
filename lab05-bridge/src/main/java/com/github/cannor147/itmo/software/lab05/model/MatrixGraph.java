package com.github.cannor147.itmo.software.lab05.model;

import com.github.cannor147.itmo.software.lab05.api.DrawingApi;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatrixGraph implements Graph {
    private final int[][] adjacencyMatrix;

    @Override
    public void draw(DrawingApi drawingApi) {
        // No operations.
    }

    @Override
    public int size() {
        return adjacencyMatrix.length;
    }
}

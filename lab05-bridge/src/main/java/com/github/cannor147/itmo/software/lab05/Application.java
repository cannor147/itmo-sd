package com.github.cannor147.itmo.software.lab05;

import com.github.cannor147.itmo.software.lab05.api.AwtDrawingApi;
import com.github.cannor147.itmo.software.lab05.api.DrawingApi;
import com.github.cannor147.itmo.software.lab05.api.FxDrawingApi;
import com.github.cannor147.itmo.software.lab05.drawer.GraphDrawer;
import com.github.cannor147.itmo.software.lab05.model.Graph;
import com.github.cannor147.itmo.software.lab05.model.ListGraph;

import java.util.Random;

public class Application {
    public static void main(String[] args) {
        final Random random = new Random();
        final DrawingApi drawingApi = random.nextBoolean() ? new FxDrawingApi(600, 600) : new AwtDrawingApi(600, 600);
        final GraphDrawer graphDrawer = new GraphDrawer(drawingApi);

        final Graph graph = new ListGraph(7, new int[]{1, 1, 5, 2, 6, 3}, new int[]{2, 3, 7, 3, 1, 4});
        graphDrawer.draw(graph);
    }
}

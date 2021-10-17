package com.github.cannor147.itmo.software.lab05;

import com.github.cannor147.itmo.software.lab05.api.AwtDrawingApi;
import com.github.cannor147.itmo.software.lab05.api.DrawingApi;
import com.github.cannor147.itmo.software.lab05.api.FxDrawingApi;

public class Application {
    public static void main(String[] args) {
//        final DrawingApi drawingApi = new FxDrawingApi(600, 600);
        final DrawingApi drawingApi = new AwtDrawingApi(600, 600);
        drawingApi.drawCircle(0, 0, 100);
        drawingApi.drawCircle(200, 200, 100);
        drawingApi.show();
    }
}

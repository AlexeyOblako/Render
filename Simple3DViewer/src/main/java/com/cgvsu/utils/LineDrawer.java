package com.cgvsu.utils;

import com.cgvsu.math.Vector2f;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Map;


public class LineDrawer {
    public static void drawLineWithZBuffer(Vector2f v1, Vector2f v2, GraphicsContext gc, ArrayList<ArrayList<Float>> zBuffer, Map<Vector2f, Float> depthMap) {

        float x0 = v1.getX();
        float x1 = v2.getX();
        float y0 = v1.getY();
        float y1 = v2.getY();
        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
        float depth1 = depthMap.get(v1);
        float depth2 = depthMap.get(v2);

        if (steep) {
            // swap x0, y0
            float temp = x0;
            x0 = y0;
            y0 = temp;

            // swap x1, y1
            temp = x1;
            x1 = y1;
            y1 = temp;
        }

        if (x0 > x1) {
            // swap x0, x1
            float temp = x0;
            x0 = x1;
            x1 = temp;

            // swap y0, y1
            temp = y0;
            y0 = y1;
            y1 = temp;

            // swap depth1, depth2
            temp = depth1;
            depth1 = depth2;
            depth2 = temp;
        }

        float dx = x1 - x0;
        float dy = Math.abs(y1 - y0);
        float depthIncrement = (depth2 - depth1) / dx; // Change in depth per pixel in x-direction

        float error = dx / 2.0f;
        int yStep = (y0 < y1) ? 1 : -1;
        int y = (int) y0;
        float depth = depth1; // Initial depth

        for (int x = (int) x0; x <= (int) x1; x++) {
            float t = steep ? y : x;
            float z = steep ? x : y;

            if (ZBuffer.testBuffer(x, y, depth, zBuffer)) {
                gc.strokeRect(steep ? y : x, steep ? x : y, 1, 1);
            }

            error -= dy;
            if (error < 0) {
                y += yStep;
                error += dx;
            }

            depth += depthIncrement; // Update depth for the next pixel
        }
    }

}

package com.cgvsu.utils.triangles_utils;


import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import com.cgvsu.utils.ZBuffer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.*;


public class BufferedTriangleRasterization {

    private static final Comparator<Vector2f> COMPARATOR = (a, b) -> {
        int cmp = Float.compare(a.getY(), b.getY());
        if (cmp != 0) {
            return cmp;
        } else return Float.compare(a.getX(), b.getX());
    };

    public static void drawTriangle(
            final GraphicsContext gc,
            final Map<Vector2f, Float> depthMap,
            final Vector2f v1,
            final Vector2f v2,
            final Vector2f v3,
            ArrayList<ArrayList<Float>> zbuffer,

            final Color c1
    ) {
        float depth1 = depthMap.get(v1);
        float depth2 = depthMap.get(v2);
        float depth3 = depthMap.get(v3);
        // Sort vertices by y.
        final Vector2f[] verts = new Vector2f[]{v1, v2, v3};
        Arrays.sort(verts, COMPARATOR);
        final int x1 = (int) verts[0].getX();
        final int x2 = (int) verts[1].getX();
        final int x3 = (int) verts[2].getX();
        final int y1 = (int) verts[0].getY();
        final int y2 = (int) verts[1].getY();
        final int y3 = (int) verts[2].getY();

        drawTopTriangle(gc, c1, x1, y1, x2, y2, x3, y3, depth1, depth2, depth3, zbuffer);
        drawBottomTriangle(gc, c1, x1, y1, x2, y2, x3, y3, depth1, depth2, depth3, zbuffer);
    }

    private static void drawTopTriangle(
            final GraphicsContext gc,
            final Color c1,
            final int x1, final int y1,
            final int x2, final int y2,
            final int x3, final int y3,
            final float depth1, final float depth2, final float depth3,
            ArrayList<ArrayList<Float>> zbuffer

    ) {
        PixelWriter pw = gc.getPixelWriter();
        final int x2x1 = x2 - x1;
        final int x3x1 = x3 - x1;
        final int y2y1 = y2 - y1;
        final int y3y1 = y3 - y1;

        for (int y = y1; y < y2; y++) {
            // No need to check if the divisors are null, because the loop will not execute if y1 == y2.
            int l = x2x1 * (y - y1) / y2y1 + x1; // Edge 1-2.
            int r = x3x1 * (y - y1) / y3y1 + x1; // Edge 1-3.
            if (l > r) { // Swap.
                int tmp = l;
                l = r;
                r = tmp;
            }
            for (int x = l; x <= r; x++) {

                float z = interpolateDepth(x, y, x1, y1, x2, y2, x3, y3, depth1, depth2, depth3);

                if (ZBuffer.testBuffer(x, y, z, zbuffer)) {
                    pw.setColor(x, y, c1);
                }

            }
        }

    }

    private static void drawBottomTriangle(
            final GraphicsContext gc,
            final Color c1,
            final int x1, final int y1,
            final int x2, final int y2,
            final int x3, final int y3,
            final float depth1, final float depth2,
            final float depth3,
            ArrayList<ArrayList<Float>> zbuffer

    ) {
        final int x3x2 = x3 - x2;
        final int x3x1 = x3 - x1;
        final int y3y2 = y3 - y2;
        final int y3y1 = y3 - y1;

        PixelWriter pw = gc.getPixelWriter();
        // Draw the separating line and bottom triangle.
        if (y3y2 == 0 || y3y1 == 0) return; // Stop now if the bottom triangle is degenerate (avoids div by zero).
        for (int y = y2; y <= y3; y++) {
            int l = x3x2 * (y - y2) / y3y2 + x2; // Edge 2-3.
            int r = x3x1 * (y - y1) / y3y1 + x1; // Edge 1-3.
            if (l > r) {
                int tmp = l;
                l = r;
                r = tmp;
            }
            for (int x = l; x <= r; x++) {

                float z = interpolateDepth(x, y, x1, y1, x2, y2, x3, y3, depth1, depth2, depth3);


                if (ZBuffer.testBuffer(x, y, z, zbuffer)) {
                    pw.setColor(x, y, c1);
                }

            }
        }

    }

    private static float interpolateZ(int x1, int y1, int x2, int y2, int x3, int y3, float depth1, float depth2, float depth3, float area, int y, int x) {
        float u = ((y - y1) * (x3 - x1) - (y3 - y1) * (x - x1)) / area;
        float v = ((y - y2) * (x1 - x2) - (y1 - y2) * (x - x2)) / area;
        float w = 1 - u - v;

        return u * depth1 + v * depth2 + w * depth3;
    }

    private static float calculateTriangleArea(float x1, float y1, float x2, float y2, float x3, float y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0f);
    }

    // Метод для интерполяции глубины точки P внутри треугольника на основе его вершин и глубин в вершинах
    public static float interpolateDepth(float px, float py, float v1x, float v1y, float v2x, float v2y, float v3x, float v3y, float depth1, float depth2, float depth3) {
        float totalArea = calculateTriangleArea(v1x, v1y, v2x, v2y, v3x, v3y);

        float w1 = calculateTriangleArea(px, py, v2x, v2y, v3x, v3y) / totalArea;
        float w2 = calculateTriangleArea(px, py, v3x, v3y, v1x, v1y) / totalArea;
        float w3 = calculateTriangleArea(px, py, v1x, v1y, v2x, v2y) / totalArea;

        return w1 * depth1 + w2 * depth2 + w3 * depth3;
    }
}






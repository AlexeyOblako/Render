package com.cgvsu.utils.triangles_utils;

import com.cgvsu.math.Vector2f;
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
            final Vector2f v1, final Color c1,
            final Vector2f v2, final Color c2,
            final Vector2f v3, final Color c3,
            ArrayList<ArrayList<Float>> zbuffer
    ) {
        float depth1 = depthMap.get(v1);
        float depth2 = depthMap.get(v2);
        float depth3 = depthMap.get(v3);

        // Сортировка вершин по Y
        final Vector2f[] verts = new Vector2f[]{v1, v2, v3};
        Arrays.sort(verts, COMPARATOR);
        final int x1 = (int) verts[0].getX();
        final int x2 = (int) verts[1].getX();
        final int x3 = (int) verts[2].getX();
        final int y1 = (int) verts[0].getY();
        final int y2 = (int) verts[1].getY();
        final int y3 = (int) verts[2].getY();

        drawTopTriangle(gc, x1, y1, x2, y2, x3, y3, depth1, depth2, depth3, c1, c2, c3, zbuffer);
        drawBottomTriangle(gc, x1, y1, x2, y2, x3, y3, depth1, depth2, depth3, c1, c2, c3, zbuffer);
    }

    private static void drawTopTriangle(
            final GraphicsContext gc,
            final int x1, final int y1,
            final int x2, final int y2,
            final int x3, final int y3,
            final float depth1, final float depth2, final float depth3,
            final Color c1, final Color c2, final Color c3,
            ArrayList<ArrayList<Float>> zbuffer
    ) {
        PixelWriter pw = gc.getPixelWriter();
        final int x2x1 = x2 - x1;
        final int x3x1 = x3 - x1;
        final int y2y1 = y2 - y1;
        final int y3y1 = y3 - y1;

        for (int y = y1; y < y2; y++) {
            int l = x2x1 * (y - y1) / y2y1 + x1; // Edge 1-2
            int r = x3x1 * (y - y1) / y3y1 + x1; // Edge 1-3
            if (l > r) {
                int tmp = l;
                l = r;
                r = tmp;
            }
            for (int x = l; x <= r; x++) {
                float z = interpolateDepth(x, y, x1, y1, x2, y2, x3, y3, depth1, depth2, depth3);
                if (ZBuffer.testBuffer(x, y, z, zbuffer)) {
                    Color interpolatedColor = interpolateColor(x, y, x1, y1, c1, x2, y2, c2, x3, y3, c3);
                    pw.setColor(x, y, interpolatedColor);
                }
            }
        }
    }

    private static void drawBottomTriangle(
            final GraphicsContext gc,
            final int x1, final int y1,
            final int x2, final int y2,
            final int x3, final int y3,
            final float depth1, final float depth2, final float depth3,
            final Color c1, final Color c2, final Color c3,
            ArrayList<ArrayList<Float>> zbuffer
    ) {
        final int x3x2 = x3 - x2;
        final int x3x1 = x3 - x1;
        final int y3y2 = y3 - y2;
        final int y3y1 = y3 - y1;

        PixelWriter pw = gc.getPixelWriter();
        if (y3y2 == 0 || y3y1 == 0) return; // Stop now if the bottom triangle is degenerate (avoids div by zero).
        for (int y = y2; y <= y3; y++) {
            int l = x3x2 * (y - y2) / y3y2 + x2; // Edge 2-3
            int r = x3x1 * (y - y1) / y3y1 + x1; // Edge 1-3
            if (l > r) {
                int tmp = l;
                l = r;
                r = tmp;
            }
            for (int x = l; x <= r; x++) {
                float z = interpolateDepth(x, y, x1, y1, x2, y2, x3, y3, depth1, depth2, depth3);
                if (ZBuffer.testBuffer(x, y, z, zbuffer)) {
                    Color interpolatedColor = interpolateColor(x, y, x1, y1, c1, x2, y2, c2, x3, y3, c3);
                    pw.setColor(x, y, interpolatedColor);
                }
            }
        }
    }

    private static Color interpolateColor(
            final int x, final int y,
            final int x1, final int y1, final Color c1,
            final int x2, final int y2, final Color c2,
            final int x3, final int y3, final Color c3
    ) {
        float totalArea = calculateTriangleArea(x1, y1, x2, y2, x3, y3);

        float w1 = calculateTriangleArea(x, y, x2, y2, x3, y3) / totalArea;
        float w2 = calculateTriangleArea(x, y, x3, y3, x1, y1) / totalArea;
        float w3 = calculateTriangleArea(x, y, x1, y1, x2, y2) / totalArea;

        // Интерполяция каждого компонента цвета с ограничением значений
        double red = Math.min(1.0, Math.max(0.0, w1 * c1.getRed() + w2 * c2.getRed() + w3 * c3.getRed()));
        double green = Math.min(1.0, Math.max(0.0, w1 * c1.getGreen() + w2 * c2.getGreen() + w3 * c3.getGreen()));
        double blue = Math.min(1.0, Math.max(0.0, w1 * c1.getBlue() + w2 * c2.getBlue() + w3 * c3.getBlue()));

        return new Color(red, green, blue, 1.0);
    }

    private static float calculateTriangleArea(float x1, float y1, float x2, float y2, float x3, float y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0f);
    }

    public static float interpolateDepth(float px, float py, float v1x, float v1y, float v2x, float v2y, float v3x, float v3y, float depth1, float depth2, float depth3) {
        float totalArea = calculateTriangleArea(v1x, v1y, v2x, v2y, v3x, v3y);

        float w1 = calculateTriangleArea(px, py, v2x, v2y, v3x, v3y) / totalArea;
        float w2 = calculateTriangleArea(px, py, v3x, v3y, v1x, v1y) / totalArea;
        float w3 = calculateTriangleArea(px, py, v1x, v1y, v2x, v2y) / totalArea;

        return w1 * depth1 + w2 * depth2 + w3 * depth3;
    }
}
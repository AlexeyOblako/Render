package com.cgvsu.utils;

import java.util.ArrayList;

public class ZBuffer {

    // Инициализация Z-буфера
    public static ArrayList<ArrayList<Float>> getDefaultPixelDepthMatrix(int width, int height) {
        ArrayList<ArrayList<Float>> buffer = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            ArrayList<Float> row = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                row.add(Float.MAX_VALUE); // Изначально все пиксели имеют максимальную глубину
            }
            buffer.add(row);
        }
        return buffer;
    }

    // Проверка и обновление Z-буфера
    public static boolean testBuffer(int x, int y, float depth, ArrayList<ArrayList<Float>> buffer) {
        if (x >= 0 && x < buffer.size() && y >= 0 && y < buffer.get(0).size()) {
            if (depth < buffer.get(x).get(y)) {
                buffer.get(x).set(y, depth); // Обновляем глубину
                return true;
            }
        }
        return false;
    }
}
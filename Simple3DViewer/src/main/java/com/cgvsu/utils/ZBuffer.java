package com.cgvsu.utils;

import java.util.ArrayList;

public class ZBuffer {

    //Будет использоваться в RenderEngine, чтобы подготовить буфер для хранения глубин пикселей
    public static ArrayList<ArrayList<Float>> getDefaultPixelDepthMatrix(int width, int height) {
        ArrayList<ArrayList<Float>> buffer = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            ArrayList<Float> fl = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                fl.add(Float.MAX_VALUE);
            }
            buffer.add(fl);
        }
        return buffer;
    }

    //Проверка видимости пикселей полигонов
    public static boolean testBuffer(int x, int y, float depth, ArrayList<ArrayList<Float>> buffer) {
        if (x >= 0 && x < buffer.size() && y >= 0 && y < buffer.get(0).size()) {
            if (depth < buffer.get(x).get(y)) {
                buffer.get(x).set(y, depth);
                return true;
            }
        }
        return false;
    }
}

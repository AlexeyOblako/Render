package com.cgvsu.utils;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.TriPolyModel;
import com.cgvsu.model.Triangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class ZBuffer {

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

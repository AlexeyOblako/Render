package com.cgvsu.utils.triangles_utils;

import javafx.scene.paint.Color;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ColorUtils {
    private static final Random rand = ThreadLocalRandom.current();

    public static Color getRandomColor() {
        return new Color(
                rand.nextDouble(),
                rand.nextDouble(),
                rand.nextDouble(),
                1.0f
        );
    }
}

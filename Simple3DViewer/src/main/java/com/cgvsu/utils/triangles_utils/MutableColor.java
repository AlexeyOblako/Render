package com.cgvsu.utils.triangles_utils;

/**
 * A color with mutable red, green, and blue values.
 */
public class MutableColor {
    /**
     * The red component of the color.
     */
    public float red;

    /**
     * The green component of the color.
     */
    public float green;

    /**
     * The blue component of the color.
     */
    public float blue;

    /**
     * Sets the new values for the red, green, and blue components.
     */
    public void set(float r, float g, float b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    /**
     * Converts this color to a 32-bit integer containing this color in the ARGB format.
     */
    public int toArgb() {
        int a = 255 << 24;
        int r = (int) (red * 255) << 16;
        int g = (int) (green * 255) << 8;
        int b = (int) (blue * 255);
            return a | r | g | b;
    }
}

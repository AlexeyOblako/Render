package com.cgvsu.math;

public class Vector2f extends Vector {
    public Vector2f(float x, float y) {
        super(new float[]{x, y});
    }

    public Vector2f(float[] points) {
        super(points);
        if (points.length != 2) {
            throw new IllegalArgumentException("Вектор должен быть размерности 2");
        }
    }

    public float getX() {
        return components[0];
    }

    public float getY() {
        return components[1];
    }

    @Override
    public float length() {
        return (float) Math.sqrt(components[0] * components[0] + components[1] * components[1]);
    }

    @Override
    public Vector2f normalize() {
        float len = length();
        return new Vector2f(components[0] / len, components[1] / len);
    }

    @Override
    public Vector2f add(Vector other) {
        return new Vector2f(components[0] + other.get(0), components[1] + other.get(1));
    }

    @Override
    public Vector2f deduct(Vector other) {
        return new Vector2f(components[0] - other.get(0), components[1] - other.get(1));
    }

    @Override
    protected Vector2f createInstance(float[] components) {
        return new Vector2f(components);
    }

    public float crossMagnitude(Vector2f other) {
        return this.components[0] * other.components[1] - this.components[1] * other.components[0];
    }

    public Vector2f to(Vector2f end) {
        return new Vector2f(end.getX() - components[0], end.getY() - components[1]);
    }

    public void set(float x, float y) {
        components[0] = x;
        components[1] = y;
    }

    public static Vector2f multiply(Vector2f vector, float scalar) {
        return new Vector2f(
                vector.components[0] * scalar,
                vector.components[1] * scalar
        );
    }

    public Vector2f multiply(float scalar) {
        return new Vector2f(
                components[0] * scalar,
                components[1] * scalar
        );
    }
}

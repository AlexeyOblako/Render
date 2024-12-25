package com.cgvsu.math;

public class Vector4f extends Vector {
    public Vector4f(float x, float y, float z, float w) {
        super(new float[]{x, y, z, w});
    }

    public Vector4f(float[] points) {
        super(points);
        if (points.length != 4) {
            throw new IllegalArgumentException("Вектор должен быть размерности 4");
        }
    }

    public float getX() {
        return components[0];
    }

    public float getY() {
        return components[1];
    }

    public float getZ() {
        return components[2];
    }

    public float getW() {
        return components[3];
    }

    @Override
    public float length() {
        return (float) Math.sqrt(components[0] * components[0] + components[1] * components[1] + components[2] * components[2] + components[3] * components[3]);
    }

    @Override
    public Vector4f normalize() {
        float len = length();
        return new Vector4f(components[0] / len, components[1] / len, components[2] / len, components[3] / len);
    }

    @Override
    public Vector4f add(Vector other) {
        return new Vector4f(components[0] + other.get(0), components[1] + other.get(1), components[2] + other.get(2), components[3] + other.get(3));
    }

    @Override
    public Vector4f deduct(Vector other) {
        return new Vector4f(components[0] - other.get(0), components[1] - other.get(1), components[2] - other.get(2), components[3] - other.get(3));
    }

    @Override
    protected Vector4f createInstance(float[] components) {
        return new Vector4f(components);
    }

    public Vector3f normalizeTo3f() {
        return new Vector3f(
                components[0] / components[3],
                components[1] / components[3],
                components[2] / components[3]
        );
    }
}

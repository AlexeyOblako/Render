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

    public Vector3f crossProduct(Vector4f other) {
        return new Vector3f(
                components[1] * other.components[2] - components[2] * other.components[1],
                components[2] * other.components[0] - components[0] * other.components[2],
                components[0] * other.components[1] - components[1] * other.components[0]
        );
    }

    public static Vector3f crossProduct(Vector4f first, Vector4f second) {
        return new Vector3f(
                first.components[1] * second.components[2] - first.components[2] * second.components[1],
                first.components[2] * second.components[0] - first.components[0] * second.components[2],
                first.components[0] * second.components[1] - first.components[1] * second.components[0]
        );
    }

    public static Vector4f multiply(Vector4f vector, float scalar) {
        return new Vector4f(
                vector.components[0] * scalar,
                vector.components[1] * scalar,
                vector.components[2] * scalar,
                vector.components[3] * scalar
        );
    }

    public Vector4f multiply(float scalar) {
        return new Vector4f(
                components[0] * scalar,
                components[1] * scalar,
                components[2] * scalar,
                components[3] * scalar
        );
    }

    public Vector4f multiply(Vector4f other) {
        return new Vector4f(
                components[0] * other.components[0],
                components[1] * other.components[1],
                components[2] * other.components[2],
                components[3] * other.components[3]
        );
    }

    public static Vector4f multiply(Vector4f first, Vector4f second) {
        return new Vector4f(
                first.components[0] * second.components[0],
                first.components[1] * second.components[1],
                first.components[2] * second.components[2],
                first.components[3] * second.components[3]
        );
    }

    public Vector3f normalizeTo3f() {
        return new Vector3f(
                components[0] / components[3],
                components[1] / components[3],
                components[2] / components[3]
        );
    }
}

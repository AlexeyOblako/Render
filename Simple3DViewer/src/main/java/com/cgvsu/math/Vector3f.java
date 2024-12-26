package com.cgvsu.math;

public class Vector3f extends Vector {
    public Vector3f(float x, float y, float z) {
        super(new float[]{x, y, z});
    }

    public Vector3f(float[] points) {
        super(points);
        if (points.length != 3) {
            throw new IllegalArgumentException("Вектор должен быть размерности 3");
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

    @Override
    public float length() {
        return (float) Math.sqrt(components[0] * components[0] + components[1] * components[1] + components[2] * components[2]);
    }

    @Override
    public Vector3f normalize() {
        float len = length();
        return new Vector3f(components[0] / len, components[1] / len, components[2] / len);
    }

    @Override
    public Vector3f add(Vector other) {
        return new Vector3f(components[0] + other.get(0), components[1] + other.get(1), components[2] + other.get(2));
    }

    @Override
    public Vector3f deduct(Vector other) {
        return new Vector3f(components[0] - other.get(0), components[1] - other.get(1), components[2] - other.get(2));
    }

    public static Vector3f deduct(Vector3f other1, Vector3f other2) {
        return new Vector3f(other1.getX() - other2.getX(), other1.getY() - other2.getY(), other1.getZ() - other2.getZ());
    }

    @Override
    protected Vector3f createInstance(float[] components) {
        return new Vector3f(components);
    }

    public Vector3f crossProduct(Vector3f other) {
        return new Vector3f(
                components[1] * other.components[2] - components[2] * other.components[1],
                components[2] * other.components[0] - components[0] * other.components[2],
                components[0] * other.components[1] - components[1] * other.components[0]
        );
    }

    public static Vector3f crossProduct(Vector3f first, Vector3f second) {
        return new Vector3f(
                first.components[1] * second.components[2] - first.components[2] * second.components[1],
                first.components[2] * second.components[0] - first.components[0] * second.components[2],
                first.components[0] * second.components[1] - first.components[1] * second.components[0]
        );
    }

    public void setX(float x) {
        components[0] = x;
    }

    public void setY(float y) {
        components[1] = y;
    }

    public void setZ(float z) {
        components[2] = z;
    }

    public void add(Vector3f other) {
        components[0] += other.components[0];
        components[1] += other.components[1];
        components[2] += other.components[2];
    }

    public static Vector3f add(Vector3f first, Vector3f second) {
        return new Vector3f(
                first.components[0] + second.components[0],
                first.components[1] + second.components[1],
                first.components[2] + second.components[2]
        );
    }

    public static Vector2f vertex3fToVector2f(final Vector3f vertex, final int width, final int height) {
        return new Vector2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }

    public Vector3f multiply(float scalar) {
        return new Vector3f(
                components[0] * scalar,
                components[1] * scalar,
                components[2] * scalar
        );
    }

    public static Vector3f multiply(Vector3f vector, float scalar) {
        return new Vector3f(
                vector.components[0] * scalar,
                vector.components[1] * scalar,
                vector.components[2] * scalar
        );
    }

    public Vector3f multiply(Vector3f other) {
        return new Vector3f(
                components[0] * other.components[0],
                components[1] * other.components[1],
                components[2] * other.components[2]
        );
    }

    public static Vector3f multiply(Vector3f first, Vector3f second) {
        return new Vector3f(
                first.components[0] * second.components[0],
                first.components[1] * second.components[1],
                first.components[2] * second.components[2]
        );
    }
}

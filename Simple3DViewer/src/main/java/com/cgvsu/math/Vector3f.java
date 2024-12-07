package com.cgvsu.math;

public class Vector3f {
    // TODO: 26.11.2024 сделать приватными переменные
    public float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(float[] points) {
        if (points.length != 3) {
            throw new IllegalArgumentException("Vector should have 3 points");
        }
        this.x = points[0];
        this.y = points[1];
        this.z = points[2];
    }

    public Vector3f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
        }
        throw new IllegalArgumentException("Index out of bounds");
    }

    public Vector3f add(Vector3f other) {
        return new Vector3f(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public static Vector3f add(Vector3f first, Vector3f second) {
        return new Vector3f(
                first.x + second.x,
                first.y + second.y,
                first.z + second.z
        );
    }

    //Вычитание векторов
    public Vector3f deduct(Vector3f other) {
        return new Vector3f(
                this.x - other.x,
                this.y - other.y,
                this.z - other.z
        );
    }

    public static Vector3f deduct(Vector3f first, Vector3f second) {
        return new Vector3f(
                first.x - second.x,
                first.y - second.y,
                first.z - second.z
        );
    }

    //Умножение на скаляр
    public Vector3f multiply(float scalar) {
        return new Vector3f(
                this.x * scalar,
                this.y * scalar,
                this.z * scalar
        );
    }

    //Деление на скаляр
    public Vector3f divide(float scalar) {
        if (scalar == 0) {
            throw new ArithmeticException("Dividing by zero is undefined and not allowed");
        }
        return new Vector3f(
                this.x / scalar,
                this.y / scalar,
                this.z / scalar
        );
    }

    //Вычисление длины вектора
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    //Нормализация вектора
    public Vector3f normalize() {
        float normalization = length() > 0 ? 1 / length() : 0;
        return new Vector3f(
                this.x * normalization,
                this.y * normalization,
                this.z * normalization
        );
    }

    //Скалярное произведение векторов
    public float dot(Vector3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    // Векторное произведение векторов
    public Vector3f crossProduct(Vector3f other) {
        return new Vector3f(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }

    public static Vector3f crossProduct(Vector3f first, Vector3f second) {
        return new Vector3f(
                first.y * second.z - first.z * second.y,
                first.z * second.x - first.x * second.z,
                first.x * second.y - first.y * second.x
        );
    }

    public boolean equals(Vector3f vector3f) {
        return Math.abs(this.x - vector3f.x) <= 10e-6 &&
                Math.abs(this.y - vector3f.y) <= 10e-6 &&
                Math.abs(this.z - vector3f.z) <= 10e-6;
    }

    public String toString() {
        return "Vector3f: x = " + this.x + ", y = " + this.y + ", z = " + this.z;
    }

    public static Vector2f vertex3fToVector2f(final Vector3f vertex, final int width, final int height) {
        return new Vector2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }
}

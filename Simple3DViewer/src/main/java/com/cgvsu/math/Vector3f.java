package com.cgvsu.math;

public class Vector3f {
    public float x, y, z;

    // Конструктор для инициализации координат вектора
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Метод для сложения двух векторов
    public void add(Vector3f other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
    }

    // Метод для вычитания векторов
    public void sub(Vector3f other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
    }

    // Векторное произведение двух векторов
    public void cross(Vector3f v1, Vector3f v2) {
        this.x = v1.y * v2.z - v1.z * v2.y;
        this.y = v1.z * v2.x - v1.x * v2.z;
        this.z = v1.x * v2.y - v1.y * v2.x;
    }

    // Метод для нормализации вектора (приведение длины к 1)
    public void normalize() {
        float length = length();
        if (length != 0) {
            this.x /= length;
            this.y /= length;
            this.z /= length;
        }
    }

    // Метод для вычисления длины вектора
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    // Сравнение векторов с точностью до заданного эпсилона
    public boolean equals(Vector3f other) {
        final float eps = 1e-7f;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps && Math.abs(z - other.z) < eps;
    }

    // Метод для получения строкового представления вектора
    @Override
    public String toString() {
        return "Vector3f(" + x + ", " + y + ", " + z + ")";
    }
}

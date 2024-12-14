package com.cgvsu.math;

public class Vector4f {
    private float x;
    private float y;
    private float z;
    private float w;

    public Vector4f (float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f(float[] points) {
        if (points.length != 4) {
            throw new IllegalArgumentException("Вектор должен быть размерности 4");
        }
        this.x = points[0];
        this.y = points[1];
        this.z = points[2];
        this.w = points[3];
    }

    public Vector4f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }
    public float getX() {
        return this.x;
    }

    public float getY () {
        return y;
    }

    public float getZ () {
        return z;
    }

    public float getW () {
        return w;
    }
    public float get(int index) {
        switch (index){
            case 0: return x;
            case 1: return y;
            case 2: return z;
            case 3: return w;
        }
        throw new IllegalArgumentException("Недопустимо, индекс выходит за пределы допустимого");
    }
    // сложение
    public Vector4f add(Vector4f other) {
        return new Vector4f(this.x + other.x, this.y + other.y, this.z + other.z, this.w + other.w);
    }

    public static Vector4f add(Vector4f first, Vector4f second) {
        return new Vector4f(
                first.x + second.x,
                first.y + second.y,
                first.z + second.z,
                first.w + second.z
        );
    }

    // вычитание
    public Vector4f deduct(Vector4f other) {
        return new Vector4f(this.x - other.x, this.y - other.y, this.z - other.z, this.w - other.w);
    }

    public static Vector4f deduct(Vector4f first, Vector4f second) {
        return new Vector4f(
                first.x - second.x,
                first.y - second.y,
                first.z - second.z,
                first.w - second.z
        );
    }

    // умножение на скаляр
    public Vector4f multiply(float scalar) {
        return new Vector4f(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar);
    }

    // деление на скаляр
    public Vector4f divide(float scalar){
        if (scalar == 0){
            throw new ArithmeticException("Недопустимо, деление на ноль");
        }
        return (new Vector4f(this.x/scalar, this.y/scalar, this.z/scalar, this.w/scalar));
    }
    //вычисление длины
    public float length(){
        return (float) Math.sqrt(x*x + y*y + z*z + w*w);
    }
    //нормализация
    public Vector4f normalize(){
        float normalization = length() != 0 ? 1 / length() : 0;
        return new Vector4f(
                this.x * normalization,
                this.y * normalization,
                this.z * normalization,
                this.w * normalization
        );
    }

    public Vector3f normalizeTo3f() {
        return new Vector3f(
                this.x / this.w,
                this.y / this.w,
                this.z / this.w
        );
    }
    // скалярное произведение
    public float dot(Vector4f other){
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    public boolean equals(Vector4f vector4f) {
        return Math.abs(this.x - vector4f.x) <= 10e-6 &&
                Math.abs(this.y - vector4f.y) <= 10e-6 &&
                Math.abs(this.z - vector4f.z) <= 10e-6 &&
                Math.abs(this.w - vector4f.w) <= 10e-6;
    }

    public String toString() {
        return "Vector4f: x = " + this.x + ", y = " + this.y + ", z = " + this.z + ", w = " + this.w;
    }
}
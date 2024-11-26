package com.cgvsu.math;

public class Vector2f{
    private float x;
    private float y;

    public Vector2f (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(float[] points) {
        if (points.length != 2) {
            throw new IllegalArgumentException("Vector should have 2 points");
        }
        this.x = points[0];
        this.y = points[1];
    }

    public Vector2f () {
        this.x = 0;
        this.y = 0;
    }

    public float getX () {
        return x;
    }

    public float getY () {
        return y;
    }

    public float get(int index){
        switch (index){
            case 0: return x;
            case 1: return y;
        }
        throw new IllegalArgumentException("Index out of bounds");
    }

    //Сложение векторов
    public Vector2f add(Vector2f other){
        return(new Vector2f(this.x + other.x, this.y + other.y));
    }

    public static Vector2f add(Vector2f first, Vector2f second) {
        return new Vector2f(
                first.x + second.x,
                first.y + second.y
        );
    }
    //Вычитание векторов
    public Vector2f deduct(Vector2f other){
        return(new Vector2f(this.x - other.x, this.y - other.y));
    }

    public static Vector2f deduct(Vector2f first, Vector2f second) {
        return new Vector2f(
                first.x - second.x,
                first.y - second.y
        );
    }
    //Умножение на скаляр
    public Vector2f multiply(float scalar){
        return(new Vector2f(this.x * scalar, this.y * scalar));
    }
    //Деление на скаляр
    public Vector2f divide(float scalar){
        if (scalar == 0){
            throw new ArithmeticException("Dividing by zero is undefined and not allowed");
        }
        return (new Vector2f(this.x/scalar, this.y/scalar));
    }
    //Вычисление длины вектора
    public float length(){
        return (float) Math.sqrt(x*x + y*y);
    }

    //Нормализация вектора
    public Vector2f normalize(){
        float normalization = length() != 0 ? 1 / length() : 0;
        return new Vector2f(
                this.x * normalization,
                this.y * normalization
        );
    }
    //Скалярное произведение векторов
    public float dot(Vector2f other){
        return this.x * other.x + this.y * other.y;
    }

    public boolean equals(Vector2f vector2f) {
        return Math.abs(this.x - vector2f.x) <= 10e-6 &&
                Math.abs(this.y - vector2f.y) <= 10e-6;
    }

    public String toString() {
        return "Vector2f: x = " + this.x + ", y = " + this.y;
    }
    public Vector2f to(Vector2f end) {
        return new Vector2f(end.x - x, end.y - y);
    }
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public float crossMagnitude(Vector2f other) {
        return this.x * other.y - this.y * other.x;
    }

}
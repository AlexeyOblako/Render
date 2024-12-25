package com.cgvsu;

public interface Transformable {
    void scale(float scaleX, float scaleY, float scaleZ);
    void rotate(float angleX, float angleY, float angleZ);
    void translate(float translationX, float translationY, float translationZ);
}

package com.cgvsu.render_engine;

public class ModelTransform {
    private float scaleX = 1, scaleY = 1, scaleZ = 1;
    private float rotationX = 0, rotationY = 0, rotationZ = 0;
    private float translationX = 0, translationY = 0, translationZ = 0;

    public void setScale(float scaleX, float scaleY, float scaleZ) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    public void setRotation(float rotationX, float rotationY, float rotationZ) {
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
    }

    public void setTranslation(float translationX, float translationY, float translationZ) {
        this.translationX = translationX;
        this.translationY = translationY;
        this.translationZ = translationZ;
    }

    // Методы для получения текущих параметров
    public float getScaleX() { return scaleX; }
    public float getScaleY() { return scaleY; }
    public float getScaleZ() { return scaleZ; }
    public float getRotationX() { return rotationX; }
    public float getRotationY() { return rotationY; }
    public float getRotationZ() { return rotationZ; }
    public float getTranslationX() { return translationX; }
    public float getTranslationY() { return translationY; }
    public float getTranslationZ() { return translationZ; }
}

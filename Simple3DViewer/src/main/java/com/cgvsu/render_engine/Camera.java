package com.cgvsu.render_engine;

import com.cgvsu.math.Vector3f;
import com.cgvsu.math.matrix.Matrix4f;

public class Camera {

    public Camera(
            final Vector3f position,
            final Vector3f target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;

        // Источник света привязывается к камере
        this.lightPosition = new Vector3f(position.getX(), position.getY(), position.getZ());
    }

    public Vector3f getLightPosition() {
        return lightPosition;
    }

    public void setLightPosition(Vector3f lightPosition) {
        this.lightPosition = lightPosition;
    }

    public void movePositionAndTarget(final Vector3f translation) {
        this.position.add(translation);
        this.target.add(translation);
        this.lightPosition.add(translation); // Обновляем позицию источника света
    }

    public void setPosition(final Vector3f position) {
        this.position = position;
    }

    public void setTarget(final Vector3f target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getTarget() {
        return target;
    }

    public void movePosition(final Vector3f translation) {
        this.position.add(translation);
    }

    public void moveTarget(final Vector3f translation) {
        this.target.add(translation);
    }

    public Matrix4f getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    public Matrix4f getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    /////////мышь
    public void rotateAroundTarget(float yaw, float pitch) {
        // Вращение вокруг оси Y (yaw)
        Vector3f direction = target.deduct(target, position);
        float yawRad = (float) Math.toRadians(yaw);
        float cosYaw = (float) Math.cos(yawRad);
        float sinYaw = (float) Math.sin(yawRad);
        float newX = direction.getX() * cosYaw - direction.getZ() * sinYaw;
        float newZ = direction.getX() * sinYaw + direction.getZ() * cosYaw;
        direction.setX(newX);
        direction.setZ(newZ);

        // Вращение вокруг оси X (pitch)
        float pitchRad = (float) Math.toRadians(pitch);
        float cosPitch = (float) Math.cos(pitchRad);
        float sinPitch = (float) Math.sin(pitchRad);
        float newY = direction.getY() * cosPitch - direction.getZ() * sinPitch;
        float newZ2 = direction.getY() * sinPitch + direction.getZ() * cosPitch;
        direction.setY(newY);
        direction.setZ(newZ2);

        // Обновляем позицию камеры
        position = Vector3f.deduct(target, direction);
    }
    ////////мышь

    private Vector3f lightPosition; // Позиция источника света
    private Vector3f position;
    private Vector3f target;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;
}
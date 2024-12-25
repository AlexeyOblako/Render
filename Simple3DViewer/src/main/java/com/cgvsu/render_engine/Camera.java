package com.cgvsu.render_engine;

import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;
import com.cgvsu.math.matrix.Matrix4f;
import static com.cgvsu.render_engine.GraphicConveyor.*;

public class Camera {
    private Vector3f position;
    private Vector3f target;
    private Vector3f initialDirection;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;
    private float distanceToTarget;

    public Camera(
            final Vector3f position,
            final Vector3f target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.initialDirection = target.deduct(position).normalize();
        this.distanceToTarget = position.deduct(target).length();
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
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

    public void movePositionAndTarget(final Vector3f translation) {
        this.position.add(translation);
        this.target.add(translation);
    }

    public Matrix4f getViewMatrix() {
        return lookAt(position, target);
    }

    public Matrix4f getProjectionMatrix() {
        return perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    public void rotateAroundPosition(float yaw, float pitch) {
        Vector3f direction = target.deduct(position).normalize();

        Matrix4f yawMatrix = rotateY(yaw);
        Matrix4f pitchMatrix = rotateX(pitch);

        Matrix4f rotationMatrix = pitchMatrix.multiply(yawMatrix);
        Vector4f direction4f = new Vector4f(direction.getX(), direction.getY(), direction.getZ(), 0.0f);
        Vector4f rotatedDirection4f = rotationMatrix.multiply(direction4f);
        Vector3f rotatedDirection = new Vector3f(rotatedDirection4f.getX(), rotatedDirection4f.getY(), rotatedDirection4f.getZ());
        rotatedDirection.normalize();

        Vector3f newPosition = new Vector3f(
                target.getX() + rotatedDirection.getX() * -distanceToTarget,
                target.getY() + rotatedDirection.getY() * -distanceToTarget,
                target.getZ() + rotatedDirection.getZ() * -distanceToTarget
        );
        position = newPosition;

        initialDirection = rotatedDirection;
    }
}

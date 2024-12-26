package com.cgvsu;

import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;
import com.cgvsu.math.matrix.Matrix4f;

public class Transformations {
    public static Vector3f applyTransformations(Vector3f vertex, Vector3f scale, Vector3f rotation, Vector3f translation) {
        Matrix4f scaleMatrix = createScaleMatrix(scale);
        Vector4f scaledVertex = scaleMatrix.multiply(new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1.0f));

        Matrix4f rotationMatrix = createRotationMatrix(rotation);
        Vector4f rotatedVertex = rotationMatrix.multiply(scaledVertex);

        Matrix4f translationMatrix = createTranslationMatrix(translation);
        Vector4f transformedVertex = translationMatrix.multiply(rotatedVertex);

        return new Vector3f(transformedVertex.getX(), transformedVertex.getY(), transformedVertex.getZ());
    }

    private static Matrix4f createScaleMatrix(Vector3f scale) {
        return new Matrix4f(new float[][]{
                {scale.getX(), 0, 0, 0},
                {0, scale.getY(), 0, 0},
                {0, 0, scale.getZ(), 0},
                {0, 0, 0, 1}
        });
    }

    private static Matrix4f createRotationMatrix(Vector3f rotation) {
        Matrix4f xRotation = createRotationXMatrix(rotation.getX());
        Matrix4f yRotation = createRotationYMatrix(rotation.getY());
        Matrix4f zRotation = createRotationZMatrix(rotation.getZ());

        return xRotation.multiply(yRotation).multiply(zRotation);
    }

    private static Matrix4f createRotationXMatrix(float angle) {
        float rad = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(rad);
        float sin = (float) Math.sin(rad);
        return new Matrix4f(new float[][]{
                {1, 0, 0, 0},
                {0, cos, -sin, 0},
                {0, sin, cos, 0},
                {0, 0, 0, 1}
        });
    }

    private static Matrix4f createRotationYMatrix(float angle) {
        float rad = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(rad);
        float sin = (float) Math.sin(rad);
        return new Matrix4f(new float[][]{
                {cos, 0, sin, 0},
                {0, 1, 0, 0},
                {-sin, 0, cos, 0},
                {0, 0, 0, 1}
        });
    }

    private static Matrix4f createRotationZMatrix(float angle) {
        float rad = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(rad);
        float sin = (float) Math.sin(rad);
        return new Matrix4f(new float[][]{
                {cos, -sin, 0, 0},
                {sin, cos, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    private static Matrix4f createTranslationMatrix(Vector3f translation) {
        return new Matrix4f(new float[][]{
                {1, 0, 0, translation.getX()},
                {0, 1, 0, translation.getY()},
                {0, 0, 1, translation.getZ()},
                {0, 0, 0, 1}
        });
    }
}

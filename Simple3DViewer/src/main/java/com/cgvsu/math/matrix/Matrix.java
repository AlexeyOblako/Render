package com.cgvsu.math.matrix;

public abstract class Matrix {
    protected float[][] matrix;

    public Matrix(float[][] data) {
        this.matrix = data;
    }

    public Matrix(int size) {
        this.matrix = new float[size][size];
    }

    public float[][] getMatrix() {
        return matrix;
    }

    public void setValue(int i, int j, float value) {
        matrix[i][j] = value;
    }

    public float getValue(int i, int j) {
        return matrix[i][j];
    }

    public abstract Matrix add(Matrix other);
    public abstract Matrix deduct(Matrix other);
    public abstract Matrix multiply(Matrix other);
    public abstract Matrix transpose();
    public abstract Matrix inverse();
    public abstract float determinant();
    public abstract Matrix minor();
    public abstract boolean equals(Matrix other);

    public Matrix single() {
        float[][] result = new float[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i == j) {
                    result[i][j] = 1;
                } else {
                    result[i][j] = 0;
                }
            }
        }
        return createInstance(result);
    }

    public Matrix zero() {
        float[][] result = new float[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i][j] = 0;
            }
        }
        return createInstance(result);
    }

    protected abstract Matrix createInstance(float[][] data);
}

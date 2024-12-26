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
    public abstract float determinate();
    public abstract Matrix minor();
    public abstract boolean equals(Matrix other);

    protected abstract Matrix createInstance(float[][] data);
}

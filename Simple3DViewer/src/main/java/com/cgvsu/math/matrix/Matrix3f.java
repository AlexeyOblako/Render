package com.cgvsu.math.matrix;

import com.cgvsu.math.Vector3f;

public class Matrix3f {
    private float[][] matrix = new float[3][3];

    public Matrix3f(float[][] data) {
        if (data.length != 3 || data[0].length != 3) {
            throw new IllegalArgumentException("Matrix should be 3x3");
        }
        this.matrix = data;
    }

    public Matrix3f() {
        this.matrix = new float[][]
                {
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
    }

    public float[][] getMatrix() {
        return matrix;
    }

    // Сложение матриц
    public Matrix3f add(Matrix3f other) {
        if (other == null) {
            throw new NullPointerException("Matrix can't be null");
        }
        float[][] result = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[i][j] + other.matrix[i][j];
            }
        }
        return new Matrix3f(result);
    }

    // Вычитание матриц
    public Matrix3f deduct(Matrix3f other) {
        if (other == null) {
            throw new NullPointerException("Matrix can't be null");
        }
        float[][] result = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[i][j] - other.matrix[i][j];
            }
        }
        return new Matrix3f(result);
    }

    // Умножение на вектор
    public Vector3f multiply(Vector3f vector) {
        if (vector == null) {
            throw new NullPointerException("Vector can't be null");
        }
        float[] result = new float[3];
        for (int i = 0; i < 3; i++) {
            result[i] = 0;
            for (int j = 0; j < 3; j++) {
                result[i] += this.matrix[i][j] * vector.get(j);
            }
        }
        return new Vector3f(result);
    }

    public static Vector3f multiply(Matrix3f matrix3f, Vector3f vector3f) {
        if (vector3f == null) {
            throw new NullPointerException("Vector can't be null");
        }
        if (matrix3f == null) {
            throw new NullPointerException("Matrix can't be null");
        }

        float[] result = new float[3];
        for (int i = 0; i < 3; i++) {
            result[i] = 0;
            for (int j = 0; j < 3; j++) {
                result[i] += matrix3f.matrix[i][j] * vector3f.get(j);
            }
        }
        return new Vector3f(result);
    }

    // Умножение на матрицу
    public Matrix3f multiply(Matrix3f other) {
        if (other == null) {
            throw new NullPointerException("Matrix can't be null");
        }
        float[][] result = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    result[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return new Matrix3f(result);
    }

    public static Matrix3f multiply(Matrix3f first, Matrix3f second) {
        if (first == null || second == null) {
            throw new NullPointerException("Matrix can't be null");
        }
        float[][] result = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    result[i][j] += first.matrix[i][k] * second.matrix[k][j];
                }
            }
        }
        return new Matrix3f(result);
    }

    // Транспонирование
    public Matrix3f transpose() {
        float[][] result = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[j][i];
            }
        }
        return new Matrix3f(result);
    }

    // Быстрое задание единичной матрицы
    public static Matrix3f unit() {
        float[][] unitMatrix = new float[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        return new Matrix3f(unitMatrix);
    }

    // Вычисление детерминанта
    public float determinate() {
        float a = matrix[0][0];
        float b = matrix[0][1];
        float c = matrix[0][2];
        float d = matrix[1][0];
        float e = matrix[1][1];
        float f = matrix[1][2];
        float g = matrix[2][0];
        float h = matrix[2][1];
        float i = matrix[2][2];

        return a * (e * i - f * h) - b * (d * i - f * g) + c * (d * h - e * g);
    }

    public Matrix3f inverse() {
        float det = this.determinate();
        if (det == 0) {
            throw new NullPointerException("There is no inverse matrix, since the determinant is zero");
        }
        float[][] minorMatrix = this.minor().getMatrix();
        float[][] cofactorMatrix = new float[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cofactorMatrix[i][j] = minorMatrix[i][j] * (float) Math.pow(-1, i + j);
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cofactorMatrix[i][j] = cofactorMatrix[i][j] / det;
            }
        }

        Matrix3f result = new Matrix3f(cofactorMatrix);
        result = result.transpose();

        return result;
    }

    public Matrix3f minor() {
        float[][] minor = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                float[][] temp = new float[2][2];

                int kI = 0; // индексы k: объявлены отдельно, чтобы исключить строку и столбец текущего элемента
                for (int k = 0; k < 2; k++) {
                    if (kI == i) {
                        kI++;
                    }
                    int qJ = 0; // индексы q: объявлены отдельно, чтобы исключить строку и столбец текущего элемента

                    for (int q = 0; q < 2; q++) {
                        if (qJ == j) {
                            qJ++;
                        }
                        temp[k][q] = this.matrix[kI][qJ];
                        qJ++;
                    }
                    kI++;
                }
                minor[i][j] = temp[0][0] * temp[1][1] - temp[0][1] * temp[1][0];
            }
        }
        return new Matrix3f(minor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Matrix3f matrix3f = (Matrix3f) obj;
        int length = this.matrix.length;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (Math.abs(this.matrix[i][j] - matrix3f.matrix[i][j]) > 10e-6) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result = 31 * result + Float.floatToIntBits(matrix[i][j]);
            }
        }
        return result;
    }
}

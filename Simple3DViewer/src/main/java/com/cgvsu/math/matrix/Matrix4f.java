package com.cgvsu.math.matrix;

import com.cgvsu.math.Vector4f;

public class Matrix4f extends Matrix {
    public Matrix4f(float[][] data) {
        super(data);
        if (data.length != 4 || data[0].length != 4) {
            throw new IllegalArgumentException("Матрица должна быть размерностью 4на4");
        }
    }

    public Matrix4f() {
        super(4);
    }

    @Override
    public Matrix4f add(Matrix other) {
        if (other == null) {
            throw new NullPointerException("Матрица не может быть нулевой");
        }
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.matrix[i][j] + other.getMatrix()[i][j];
            }
        }
        return new Matrix4f(result);
    }

    @Override
    public Matrix4f deduct(Matrix other) {
        if (other == null) {
            throw new NullPointerException("Матрица не может быть нулевой");
        }
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.matrix[i][j] - other.getMatrix()[i][j];
            }
        }
        return new Matrix4f(result);
    }

    @Override
    public Matrix4f multiply(Matrix other) {
        if (other == null) {
            throw new NullPointerException("Матрица не может быть нулевой");
        }
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    result[i][j] += this.matrix[i][k] * other.getMatrix()[k][j];
                }
            }
        }
        return new Matrix4f(result);
    }

    public static Matrix4f multiply(Matrix4f first, Matrix4f second) {
        if (first == null || second == null) {
            throw new NullPointerException("Матрица не может быть нулевой");
        }
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    result[i][j] += first.getMatrix()[i][k] * second.getMatrix()[k][j];
                }
            }
        }
        return new Matrix4f(result);
    }

    public Vector4f multiply(Vector4f vector) {
        if (vector == null) {
            throw new NullPointerException("Вектор не может быть нулевым");
        }
        float[] result = new float[4];
        for (int i = 0; i < 4; i++) {
            result[i] = 0;
            for (int j = 0; j < 4; j++) {
                result[i] += this.matrix[i][j] * vector.get(j);
            }
        }
        return new Vector4f(result);
    }

    @Override
    public Matrix4f transpose() {
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.matrix[j][i];
            }
        }
        return new Matrix4f(result);
    }

    @Override
    public Matrix4f inverse() {
        float det = this.determinate();
        if (det == 0) {
            throw new NullPointerException("Обратной матрицы не существует, определитель равен ноль");
        }
        float[][] matrix = this.minor().getMatrix();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = matrix[i][j] / det;
                if ((i == 0 && j == 1) || (i == 1 && j == 0) || (i == 1 && j == 2) || (i == 2 && j == 1) || (i == 0 && j == 3) || (i == 3 && j == 0) || (i == 3 && j == 2) || (i == 2 && j == 3)) {
                    matrix[i][j] *= -1;
                }
            }
        }
        Matrix4f result = new Matrix4f(matrix);
        result = result.transpose();

        return result;
    }

    @Override
    public float determinate() {
        float[][] data1 = new float[3][3];
        float[][] data2 = new float[3][3];
        float[][] data3 = new float[3][3];
        float[][] data4 = new float[3][3];

        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (j != 0) {
                    data1[i - 1][j - 1] = matrix[i][j];
                }
                if (j != 1) {
                    if (j == 0) {
                        data2[i - 1][j] = matrix[i][j];
                    } else {
                        data2[i - 1][j - 1] = matrix[i][j];
                    }
                }
                if (j != 2) {
                    if (j == 0 || j == 1) {
                        data3[i - 1][j] = matrix[i][j];
                    } else {
                        data3[i - 1][j - 1] = matrix[i][j];
                    }
                }
                if (j != 3) {
                    data4[i - 1][j] = matrix[i][j];
                }
            }
        }

        Matrix3f m1 = new Matrix3f(data1);
        Matrix3f m2 = new Matrix3f(data2);
        Matrix3f m3 = new Matrix3f(data3);
        Matrix3f m4 = new Matrix3f(data4);

        return (matrix[0][0] * m1.determinate() - matrix[0][1] * m2.determinate() + matrix[0][2] * m3.determinate() - matrix[0][3] * m4.determinate());
    }

    @Override
    public Matrix4f minor() {
        float[][] minor = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float[][] temp = new float[3][3];

                int kI = 0; // индексы k: объявлены отдельно, чтобы исключить строку и столбец текущего элемента
                for (int k = 0; k < 3; k++) {
                    if (kI == i) {
                        kI++;
                    }
                    int qJ = 0; // индексы q: объявлены отдельно, чтобы исключить строку и столбец текущего элемента

                    for (int q = 0; q < 3; q++) {
                        if (qJ == j) {
                            qJ++;
                        }
                        temp[k][q] = this.matrix[kI][qJ];
                        qJ++;
                    }
                    kI++;
                }
                Matrix3f tempM = new Matrix3f(temp);
                minor[i][j] = tempM.determinate();
            }
        }
        return new Matrix4f(minor);
    }

    @Override
    public boolean equals(Matrix other) {
        if (other == null || other.getMatrix().length != this.matrix.length) {
            return false;
        }
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix.length; j++) {
                if (Math.abs(this.matrix[i][j] - other.getMatrix()[i][j]) > 10e-6) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected Matrix createInstance(float[][] data) {
        return new Matrix4f(data);
    }

    public static Matrix4f rotateMatrix4f(float angleX, float angleY, float angleZ) {
        Matrix4f rotationX = rotateX(angleX);
        Matrix4f rotationY = rotateY(angleY);
        Matrix4f rotationZ = rotateZ(angleZ);
        return rotationZ.multiply(rotationY).multiply(rotationX);
    }

    public static Matrix4f rotateX(float angleX) {
        float cos = (float) Math.cos(Math.toRadians(angleX));
        float sin = (float) Math.sin(Math.toRadians(angleX));
        float[][] matrix = new float[][]
                {
                        {1, 0, 0, 0},
                        {0, cos, sin, 0},
                        {0, -sin, cos, 0},
                        {0, 0, 0, 1}
                };
        return new Matrix4f(matrix);
    }

    public static Matrix4f rotateY(float angleY) {
        float cos = (float) Math.cos(Math.toRadians(angleY));
        float sin = (float) Math.sin(Math.toRadians(angleY));
        float[][] matrix = new float[][]
                {
                        {cos, 0, -sin, 0},
                        {0, 1, 0, 0},
                        {sin, 0, cos, 0},
                        {0, 0, 0, 1}
                };
        return new Matrix4f(matrix);
    }

    public static Matrix4f rotateZ(float angleZ) {
        float cos = (float) Math.cos(Math.toRadians(angleZ));
        float sin = (float) Math.sin(Math.toRadians(angleZ));
        float[][] matrix = new float[][]
                {
                        {cos, sin, 0, 0},
                        {-sin, cos, 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 1}
                };
        return new Matrix4f(matrix);
    }
}

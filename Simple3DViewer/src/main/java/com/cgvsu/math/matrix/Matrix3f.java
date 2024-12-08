package com.cgvsu.math.matrix;
import com.cgvsu.math.Vector3f;

public class Matrix3f{
    private float[][] matrix = new float[3][3];

    public Matrix3f (float[][] data) {
        if (data.length != 3 || data[0].length != 3){
            throw new IllegalArgumentException("Матрица должна быть размерностью 3на3");
        }
        this.matrix = data;
    }

    public Matrix3f () {
        this.matrix = new float[][]
                {
                        {0,0,0},
                        {0,0,0},
                        {0,0,0}
                };
    }

    public float[][] getMatrix() {
        return matrix;
    }

    //сложение матриц
    public Matrix3f add(Matrix3f other){
        if (other == null) {
            throw new NullPointerException("Матрица не можетт быть нулевая");
        }
        float [][] result = new float[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                result[i][j] = this.matrix[i][j] + other.matrix[i][j];
            }
        }
        return new Matrix3f(result);
    }
    //вычитание
    public Matrix3f deduct(Matrix3f other){
        if (other == null) {
            throw new NullPointerException("Матрица не может быть нулевая");
        }
        float [][] result = new float[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                result[i][j] = this.matrix[i][j] - other.matrix[i][j];
            }
        }
        return new Matrix3f(result);
    }

    //умножение на вектор
    public Vector3f multiply(Vector3f vector){
        if (vector == null) {
            throw new NullPointerException("Вектор не может быть нулевым");
        }
        float [] result = new float[3];
        for (int i = 0; i < 3; i++){
            result[i] = 0;
            for (int j = 0; j < 3; j++){
                result[i] += this.matrix[i][j] * vector.get(j);
            }
        }
        return new Vector3f(result);
    }

    public static Vector3f multiply(Matrix3f matrix3f, Vector3f vector3f) {
        if (vector3f == null) {
            throw new NullPointerException("Вектор не может быть нулевым");
        }
        if (matrix3f == null) {
            throw new NullPointerException("Вектор не может быть нулевым");
        }

        float [] result = new float[3];
        for (int i = 0; i < 3; i++){
            result[i] = 0;
            for (int j = 0; j < 3; j++){
                result[i] += matrix3f.matrix[i][j] * vector3f.get(j);
            }
        }
        return new Vector3f(result);
    }

    //умножение на матрицу
    public Matrix3f multiply(Matrix3f other) {
        if (other == null) {
            throw new NullPointerException("Матрица не может быть нулевой");
        }
        float[][] result = new float[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                result[i][j] = 0;
                for (int k = 0; k < 3; k++){
                    result[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return new Matrix3f(result);
    }

    public static Matrix3f multiply(Matrix3f first, Matrix3f second) {
        if (first == null || second == null) {
            throw new NullPointerException("Матрица не может быть нулевой");
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

    // транспонирование матриц
    public Matrix3f transpose(){
        float [][] result = new float[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                result[i][j] = this.matrix[j][i];
            }
        }
        return new Matrix3f(result);
    }

    // единичная
    public static Matrix3f unit(){
        float[][] unitMatrix = new float[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        return new Matrix3f(unitMatrix);
    }
    // нахождение детерминанта
    public float determinate(){
        return (matrix[0][0]*matrix[1][1]*matrix[2][2]-(matrix[0][2]*matrix[1][1]*matrix[2][0]) +matrix[0][1]*matrix[1][2]*matrix[2][0]-(matrix[0][1]*matrix[1][0]*matrix[2][2]) +matrix[0][2]*matrix[1][0]*matrix[2][1]-(matrix[0][0]*matrix[1][2]*matrix[2][1]));
    }

    public Matrix3f inverse(){
        float det = this.determinate();
        if (det == 0){
            throw new NullPointerException("Обратной матрицы не существует, определитель равен ноль");
        }
        float[][] matrix = this.minor().getMatrix();

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = matrix[i][j]/det;
                if ((i + j) % 2 != 0){
                    matrix[i][j] *= -1;
                }
            }
        }

        Matrix3f result = new Matrix3f(matrix);
        result = result.transpose();

        return result;

    }

    public Matrix3f minor(){
        float [][] minor = new float[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                float [][] temp = new float[2][2];

                int kI = 0; // индексы k: объявлены отдельно, чтобы исключить строку и столбец текущего элемента
                for (int k = 0; k < 2; k++){
                    if (kI == i){
                        kI++;
                    }
                    int qJ = 0; // индексы q: объявлены отдельно, чтобы исключить строку и столбец текущего элемента

                    for(int q = 0; q < 2; q++){
                        if (qJ == j){
                            qJ++;
                        }
                        temp[k][q]=this.matrix[kI][qJ];
                        qJ++;
                    }
                    kI++;
                }
                minor[i][j] = temp[0][0]*temp[1][1] - temp[0][1]*temp[1][0];
            }
        }
        return new Matrix3f(minor);
    }

    public boolean equals(Matrix3f matrix3f) {
        int length = this.matrix.length;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if(Math.abs(this.matrix[i][j] - matrix3f.matrix[i][j]) > 10e-6) {
                    return false;
                }
            }
        }
        return true;
    }
}
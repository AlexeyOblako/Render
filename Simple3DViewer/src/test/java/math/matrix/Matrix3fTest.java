package math.matrix;

import static org.junit.jupiter.api.Assertions.*;

import com.cgvsu.math.Vector;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.matrix.Matrix;
import com.cgvsu.math.matrix.Matrix3f;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Matrix3fTest {
    private  Matrix3f defM;

    @BeforeEach
    void setUp() {
        final float[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        defM = new Matrix3f(matrix);

    }


    @Test
    void sumWithPositiveNum() {
        final float[][] matrixSum = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix3f mSum = new Matrix3f(matrixSum);
        final  float[][] expectedResult = {
                {2, 4, 6},
                {8, 10, 12},
                {14, 16, 18}
        };
        Matrix3f expectedRes = new Matrix3f(expectedResult);


        Matrix result = defM.add(mSum);

        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }
    }

    @Test
    void sumWithNegativeNun() {
        final float [][] matrixSum = {
                {-1, -2, -3},
                {-8, -10, -12},
                {-7, -16, -9}
        };
        Matrix3f mSum = new Matrix3f(matrixSum);
        final float [][] expectedResult = {
                {0, 0, 0},
                {-4, -5, -6},
                {0, -8, 0}
        };
        Matrix3f expectedRes = new Matrix3f(expectedResult);


        Matrix result = defM.add(mSum);

        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }
    }

    @Test
    void deductWithPositiveNum() {
        final  float[][] matrixSub = {
                {1, 2, 10},
                {4, 8, 6},
                {4, 8, 9}
        };
        Matrix3f mSub = new Matrix3f(matrixSub);
        final float[][] expectedResult = {
                {0, 0, -7},
                {0, -3, 0},
                {3, 0, 0}
        };
        Matrix3f expectedRes = new Matrix3f(expectedResult);


        Matrix result = defM.deduct(mSub);

        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }

    }

    @Test
    void deductWithNegativeNum() {
        final  float[][] matrixSub = {
                {-1, -2, -10},
                {-4, -8, -6},
                {-4, -8, -9}
        };
        Matrix3f mSub = new Matrix3f(matrixSub);
        final  float[][] expectedResult = {
                {2, 4, 13},
                {8, 13, 12},
                {11, 16, 18}
        };
        Matrix3f expectedRes = new Matrix3f(expectedResult);


        Matrix result = defM.deduct(mSub);

        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }

    }

    @Test
    void multiplyWithPositiveNum() {

        final float[][] matrix2 = {
                {1, 1, 1},
                {2, 2, 2},
                {3, 3, 3}
        };

        Matrix3f m2 = new Matrix3f(matrix2);

        final float [][] expectedResult = {
                {14, 14, 14},
                {32, 32, 32},
                {50, 50, 50},

        };
        Matrix3f expectedRes = new Matrix3f(expectedResult);


        Matrix result = defM.multiply(m2);

        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }

    }

    @Test
    void multiplyWithNegativeNum() {

        final float[][] matrix2 = {
                {-1, -1, -1},
                {-2, -8, -2},
                {-3, -3, -13}
        };

        Matrix3f m2 = new Matrix3f(matrix2);

        final float[][] expectedResult = {
                {-14, -26, -44},
                {-32, -62, -92},
                {-50, -98, -140},

        };
        Matrix3f expectedRes = new Matrix3f(expectedResult);


        Matrix result = defM.multiply(m2);

        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }

    }


    @Test
    void transpose() {

        final float[][] expectedResult = {
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };

        Matrix3f expectedRes = new Matrix3f(expectedResult);

        Matrix result = defM.transpose();


        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }
    }

    @Test
    void single() {
        final float[][] expectedResult = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        Matrix3f expectedRes = new Matrix3f(expectedResult);

        Matrix result = defM.single();

        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }
    }

    @Test
    void zero() {
        final float[][] expectedResult = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        Matrix3f expectedRes = new Matrix3f(expectedResult);

        Matrix result = defM.zero();

        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }
    }
}
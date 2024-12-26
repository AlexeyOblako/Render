
package math.matrix;

import com.cgvsu.math.matrix.Matrix;
import com.cgvsu.math.matrix.Matrix4f;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Matrix4fTest {
    private Matrix4f defM;

    @BeforeEach
    void setUp() {
        final float[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        defM = new Matrix4f(matrix);

    }


    @Test
    void addWithPositiveNum() {
        final float[][] matrixadd = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Matrix4f madd = new Matrix4f(matrixadd);
        final float[][] expectedResult = {
                {2, 4, 6, 8},
                {10, 12, 14, 16},
                {18, 20, 22, 24},
                {26, 28, 30, 32}
        };
        Matrix4f expectedRes = new Matrix4f(expectedResult);


        Matrix result = defM.add(madd);

        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }
    }

    @Test
    void addWithNegativeNum() {
        final float[][] matrixadd = {
                {-1, -2, -3, -4},
                {-10, -16, -1, -14.5F},
                {-1, -23, -((float) 1 / 2), -10},
                {-130, -52, -0, -44.5F}
        };
        Matrix4f madd = new Matrix4f(matrixadd);
        final float[][] expectedResult = {
                {0, 0, 0, 0},
                {-5, -10, 6, -6.5F},
                {8, -13, 10.5F, 2},
                {-117, -38, 15, -28.5F}
        };
        Matrix4f expectedRes = new Matrix4f(expectedResult);


        Matrix result = defM.add(madd);

        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }
    }

    @Test
    void deductWithPositiveNum() {
        final float[][] matrixSub = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {5, 10, 11, 15},
                {13, 14.5F, 17, 16}
        };
        Matrix4f mSub = new Matrix4f(matrixSub);
        final float[][] expectedResult = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {4, 0, 0, -3},
                {0, -0.5F, -2, 0}
        };
        Matrix4f expectedRes = new Matrix4f(expectedResult);


        Matrix result = defM.deduct(mSub);

        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }

    }
    @Test
    void multiplyWithPositiveMum() {

        final float[][] matrix2 = {
                {1, 1, 1, 0.5F},
                {2, 2, 2, 2},
                {3, 3, 3, 3},
                {4, 2, 4, 4}
        };

        Matrix4f m2 = new Matrix4f(matrix2);

        final float[][] expectedResult = {
                {30, 22, 30, 29.5F},
                {70, 54, 70, 67.5F},
                {110, 86, 110, 105.5F},
                {150, 118, 150, 143.5F}
        };
        Matrix4f expectedRes = new Matrix4f(expectedResult);


        Matrix result = defM.multiply(m2);

        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }

    }

    @Test
    void multiplyWithNegativeMum() {

        final float[][] matrix2 = {
                {-1, -10, 0, -0.5F},
                {-2, -2, -2, -2},
                {-300, -15, -155.0F, -3},
                {-44, -0.3F, -6, -4}
        };

        Matrix4f m2 = new Matrix4f(matrix2);

        final float[][] expectedResult = {
                {-1081, -60.2F, -493, -29.5F},
                {-2469, -169.4F, -1145, -67.5F},
                {-3857, -278.6F, -1797, -105.5F},
                {-5245, -387.8F, -2449, -143.5F}
        };
        Matrix4f expectedRes = new Matrix4f(expectedResult);


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
                {1, 5, 9, 13},
                {2, 6, 10, 14},
                {3, 7, 11, 15},
                {4, 8, 12, 16}
        };

        Matrix4f expectedRes = new Matrix4f(expectedResult);

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
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        Matrix4f expectedRes = new Matrix4f(expectedResult);

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
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0,}
        };
        Matrix4f expectedRes = new Matrix4f(expectedResult);

        Matrix result = defM.zero();

        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult.length; j++) {
                assertEquals(expectedRes.getMatrix()[i][j], result.getMatrix()[i][j]);
            }
        }
    }
}
package math.matrix;

import com.cgvsu.math.Vector4f;
import com.cgvsu.math.matrix.Matrix4f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Matrix4fTest {

    @Test
    public void testConstructorWithData() {
        float[][] data = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Matrix4f matrix = new Matrix4f(data);
        assertArrayEquals(data, matrix.getMatrix());
    }

    @Test
    public void testConstructorWithoutData() {
        Matrix4f matrix = new Matrix4f();
        float[][] expected = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        assertArrayEquals(expected, matrix.getMatrix());
    }

    @Test
    public void testSetValue() {
        Matrix4f matrix = new Matrix4f();
        matrix.setValue(1, 1, 5);
        assertEquals(5, matrix.getValue(1, 1));
    }

    @Test
    public void testAdd() {
        float[][] data1 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        float[][] data2 = {
                {16, 15, 14, 13},
                {12, 11, 10, 9},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        };
        Matrix4f matrix1 = new Matrix4f(data1);
        Matrix4f matrix2 = new Matrix4f(data2);
        Matrix4f result = matrix1.add(matrix2);
        float[][] expected = {
                {17, 17, 17, 17},
                {17, 17, 17, 17},
                {17, 17, 17, 17},
                {17, 17, 17, 17}
        };
        assertArrayEquals(expected, result.getMatrix());
    }

    @Test
    public void testDeduct() {
        float[][] data1 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        float[][] data2 = {
                {16, 15, 14, 13},
                {12, 11, 10, 9},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        };
        Matrix4f matrix1 = new Matrix4f(data1);
        Matrix4f matrix2 = new Matrix4f(data2);
        Matrix4f result = matrix1.deduct(matrix2);
        float[][] expected = {
                {-15, -13, -11, -9},
                {-7, -5, -3, -1},
                {1, 3, 5, 7},
                {9, 11, 13, 15}
        };
        assertArrayEquals(expected, result.getMatrix());
    }

    @Test
    public void testMultiplyVector() {
        float[][] data = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Matrix4f matrix = new Matrix4f(data);
        Vector4f vector = new Vector4f(new float[]{1, 2, 3, 4});
        Vector4f result = matrix.multiply(vector);
        float[] expected = {30, 70, 110, 150};

        assertEquals(expected[0], result.getX(), 1e-6);
        assertEquals(expected[1], result.getY(), 1e-6);
        assertEquals(expected[2], result.getZ(), 1e-6);
        assertEquals(expected[3], result.getW(), 1e-6);
    }

    @Test
    public void testMultiplyMatrix() {
        float[][] data1 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        float[][] data2 = {
                {16, 15, 14, 13},
                {12, 11, 10, 9},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        };
        Matrix4f matrix1 = new Matrix4f(data1);
        Matrix4f matrix2 = new Matrix4f(data2);
        Matrix4f result = matrix1.multiply(matrix2);
        float[][] expected = {
                {80, 70, 60, 50},
                {240, 214, 188, 162},
                {400, 358, 316, 274},
                {560, 502, 444, 386}
        };
        assertArrayEquals(expected, result.getMatrix());
    }

    @Test
    public void testTranspose() {
        float[][] data = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Matrix4f matrix = new Matrix4f(data);
        Matrix4f result = matrix.transpose();
        float[][] expected = {
                {1, 5, 9, 13},
                {2, 6, 10, 14},
                {3, 7, 11, 15},
                {4, 8, 12, 16}
        };
        assertArrayEquals(expected, result.getMatrix());
    }

    @Test
    public void testUnit() {
        Matrix4f unitMatrix = Matrix4f.unit();
        float[][] expected = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        assertArrayEquals(expected, unitMatrix.getMatrix());
    }

    @Test
    public void testDeterminate() {
        float[][] data = {
                {1, 2, 3, 4},
                {0, 5, 6, 7},
                {0, 0, 8, 9},
                {0, 0, 0, 10}
        };
        Matrix4f matrix = new Matrix4f(data);
        float det = matrix.determinate();
        assertEquals(400, det, 1e-6);
    }


    @Test
    public void testMinor() {
        float[][] data = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Matrix4f matrix = new Matrix4f(data);
        Matrix4f minor = matrix.minor();
        float[][] expected = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        assertArrayEquals(expected, minor.getMatrix());
    }

    @Test
    public void testEquals() {
        float[][] data1 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        float[][] data2 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Matrix4f matrix1 = new Matrix4f(data1);
        Matrix4f matrix2 = new Matrix4f(data2);
        assertTrue(matrix1.equals(matrix2));
    }

    @Test
    public void testNotEquals() {
        float[][] data1 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        float[][] data2 = {
                {16, 15, 14, 13},
                {12, 11, 10, 9},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        };
        Matrix4f matrix1 = new Matrix4f(data1);
        Matrix4f matrix2 = new Matrix4f(data2);
        assertFalse(matrix1.equals(matrix2));
    }

    private boolean assertArrayEqualsWithTolerance(float[][] expected, float[][] actual, double tolerance) {
        if (expected.length != actual.length) {
            return false;
        }
        for (int i = 0; i < expected.length; i++) {
            if (expected[i].length != actual[i].length) {
                return false;
            }
            for (int j = 0; j < expected[i].length; j++) {
                if (Math.abs(expected[i][j] - actual[i][j]) > tolerance) {
                    return false;
                }
            }
        }
        return true;
    }
}

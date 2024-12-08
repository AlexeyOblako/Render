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
    public void testConstructorWithInvalidData() {
        float[][] invalidData = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        assertThrows(IllegalArgumentException.class, () -> new Matrix4f(invalidData));
    }

    @Test
    public void testDefaultConstructor() {
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
        Vector4f vector = new Vector4f(1, 1, 1, 1);
        Matrix4f matrix = new Matrix4f(data);
        Vector4f result = matrix.multiply(vector);
        Vector4f expected = new Vector4f(10, 26, 42, 58);
        assertEquals(expected.getX(), result.getX(), 1e-6);
        assertEquals(expected.getY(), result.getY(), 1e-6);
        assertEquals(expected.getZ(), result.getZ(), 1e-6);
        assertEquals(expected.getW(), result.getW(), 1e-6);
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
                {0, 1, 4, 2},
                {5, 6, 0, 1},
                {7, 8, 9, 0}
        };
        Matrix4f matrix = new Matrix4f(data);
        float result = matrix.determinate();
        assertEquals(116, result, 1e-6);
    }

    @Test
    public void testInverse() {
        float[][] data = {
                {1, 2, 3, 4},
                {0, 1, 4, 2},
                {5, 6, 0, 1},
                {7, 8, 9, 0}
        };
        Matrix4f matrix = new Matrix4f(data);
        Matrix4f inverse = matrix.inverse();

        float[][] expected = {
                {131f / 116, -111f / 58, -20f / 29, 55f / 116},
                {-59f / 58, 48f / 29, 22f / 29, -23f / 58},
                {3f / 116, 1f / 58, -4f / 29, 11f / 116},
                {53f / 116, -21f / 58, -3f / 29, 1f / 116}
        };

        // Используем допустимую погрешность для сравнения
        float delta = 1e-6f; // допустимая погрешность
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                assertEquals(expected[i][j], inverse.getMatrix()[i][j], delta, "Mismatch at [" + i + "][" + j + "]");
            }
        }
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
}

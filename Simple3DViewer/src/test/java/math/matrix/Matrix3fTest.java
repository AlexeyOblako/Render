package math.matrix;

import com.cgvsu.math.Vector3f;
import com.cgvsu.math.matrix.Matrix3f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Matrix3fTest {

    @Test
    public void testConstructorWithData() {
        float[][] data = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix3f matrix = new Matrix3f(data);
        assertArrayEquals(data, matrix.getMatrix());
    }

    @Test
    public void testConstructorWithInvalidData() {
        float[][] invalidData = {
                {1, 2},
                {3, 4}
        };
        assertThrows(IllegalArgumentException.class, () -> new Matrix3f(invalidData));
    }

    @Test
    public void testDefaultConstructor() {
        Matrix3f matrix = new Matrix3f();
        float[][] expected = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        assertArrayEquals(expected, matrix.getMatrix());
    }

    @Test
    public void testAdd() {
        float[][] data1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        float[][] data2 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        Matrix3f matrix1 = new Matrix3f(data1);
        Matrix3f matrix2 = new Matrix3f(data2);
        Matrix3f result = matrix1.add(matrix2);
        float[][] expected = {
                {10, 10, 10},
                {10, 10, 10},
                {10, 10, 10}
        };
        assertArrayEquals(expected, result.getMatrix());
    }

    @Test
    public void testDeduct() {
        float[][] data1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        float[][] data2 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        Matrix3f matrix1 = new Matrix3f(data1);
        Matrix3f matrix2 = new Matrix3f(data2);
        Matrix3f result = matrix1.deduct(matrix2);
        float[][] expected = {
                {-8, -6, -4},
                {-2, 0, 2},
                {4, 6, 8}
        };
        assertArrayEquals(expected, result.getMatrix());
    }

    @Test
    public void testMultiplyVector() {
        float[][] data = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Vector3f vector = new Vector3f(1, 1, 1);
        Matrix3f matrix = new Matrix3f(data);
        Vector3f result = matrix.multiply(vector);
        Vector3f expected = new Vector3f(6, 15, 24);
        assertEquals(expected.getX(), result.getX(), 1e-6);
        assertEquals(expected.getY(), result.getY(), 1e-6);
        assertEquals(expected.getZ(), result.getZ(), 1e-6);
    }

    @Test
    public void testMultiplyMatrix() {
        float[][] data1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        float[][] data2 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        Matrix3f matrix1 = new Matrix3f(data1);
        Matrix3f matrix2 = new Matrix3f(data2);
        Matrix3f result = matrix1.multiply(matrix2);
        float[][] expected = {
                {30, 24, 18},
                {84, 69, 54},
                {138, 114, 90}
        };
        assertArrayEquals(expected, result.getMatrix());
    }

    @Test
    public void testTranspose() {
        float[][] data = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix3f matrix = new Matrix3f(data);
        Matrix3f result = matrix.transpose();
        float[][] expected = {
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };
        assertArrayEquals(expected, result.getMatrix());
    }

    @Test
    public void testUnit() {
        Matrix3f unitMatrix = Matrix3f.unit();
        float[][] expected = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        assertArrayEquals(expected, unitMatrix.getMatrix());
    }

    @Test
    public void testDeterminate() {
        float[][] data = {
                {1, 2, 3},
                {0, 1, 4},
                {5, 6, 0}
        };
        Matrix3f matrix = new Matrix3f(data);
        float result = matrix.determinate();
        assertEquals(-22, result, 1e-6);
    }

    @Test
    public void testInverse() {
        float[][] data = {
                {1, 2, 3},
                {0, 1, 4},
                {5, 6, 0}
        };
        Matrix3f matrix = new Matrix3f(data);
        Matrix3f inverse = matrix.inverse();
        Matrix3f product = matrix.multiply(inverse);
        float[][] expected = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        assertArrayEquals(expected, product.getMatrix());
    }

    @Test
    public void testEquals() {
        float[][] data1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        float[][] data2 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix3f matrix1 = new Matrix3f(data1);
        Matrix3f matrix2 = new Matrix3f(data2);
        assertTrue(matrix1.equals(matrix2));
    }
}

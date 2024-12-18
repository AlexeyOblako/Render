package math.matrix;

import com.cgvsu.math.Vector3f;
import com.cgvsu.math.matrix.Matrix3f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class Matrix3fTest {

    @Test
    public void testConstructor() {
        float[][] data = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix3f matrix = new Matrix3f(data);
        Assertions.assertArrayEquals(data, matrix.getMatrix());
    }

    @Test
    public void testDefaultConstructor() {
        Matrix3f matrix = new Matrix3f();
        float[][] expected = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        Assertions.assertArrayEquals(expected, matrix.getMatrix());
    }

    @Test
    public void testAdd() {
        Matrix3f matrix1 = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Matrix3f matrix2 = new Matrix3f(new float[][]{
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        });
        Matrix3f expected = new Matrix3f(new float[][]{
                {10, 10, 10},
                {10, 10, 10},
                {10, 10, 10}
        });
        Assertions.assertTrue(expected.equals(matrix1.add(matrix2)));
    }

    @Test
    public void testDeduct() {
        Matrix3f matrix1 = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Matrix3f matrix2 = new Matrix3f(new float[][]{
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        });
        Matrix3f expected = new Matrix3f(new float[][]{
                {-8, -6, -4},
                {-2, 0, 2},
                {4, 6, 8}
        });
        Assertions.assertTrue(expected.equals(matrix1.deduct(matrix2)));
    }

    @Test
    public void testMultiplyMatrix() {
        Matrix3f matrix1 = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Matrix3f matrix2 = new Matrix3f(new float[][]{
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        });
        Matrix3f expected = new Matrix3f(new float[][]{
                {30, 24, 18},
                {84, 69, 54},
                {138, 114, 90}
        });
        Assertions.assertTrue(expected.equals(matrix1.multiply(matrix2)));
    }

    @Test
    public void testMultiplyVector() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Vector3f vector = new Vector3f(1, 2, 3);
        Vector3f expected = new Vector3f(14, 32, 50);
        Assertions.assertTrue(expected.equals(matrix.multiply(vector)));
    }

    @Test
    public void testTranspose() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Matrix3f expected = new Matrix3f(new float[][]{
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        });
        Assertions.assertTrue(expected.equals(matrix.transpose()));
    }

    @Test
    public void testUnit() {
        Matrix3f expected = new Matrix3f(new float[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
        Assertions.assertTrue(expected.equals(Matrix3f.unit()));
    }

    @Test
    public void testDeterminate() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Assertions.assertEquals(0, matrix.determinate());
    }

    @Test
    public void testInverse() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {1, 2, 3},
                {0, 1, 4},
                {5, 6, 0}
        });
        Matrix3f expected = new Matrix3f(new float[][]{
                {-24, 18, 5},
                {20, -15, -4},
                {-5, 4, 1}
        });
        Assertions.assertTrue(expected.equals(matrix.inverse()));
    }

    @Test
    public void testMinor() {
        Matrix3f matrix = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Matrix3f expected = new Matrix3f(new float[][]{
                {-3, -6, -3},
                {-6, -12, -6},
                {-3, -6, -3}
        });
        Assertions.assertTrue(expected.equals(matrix.minor()));
    }

    @Test
    public void testEquals() {
        Matrix3f matrix1 = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Matrix3f matrix2 = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Assertions.assertTrue(matrix1.equals(matrix2));
    }

    @Test
    public void testNotEquals() {
        Matrix3f matrix1 = new Matrix3f(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Matrix3f matrix2 = new Matrix3f(new float[][]{
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        });
        Assertions.assertFalse(matrix1.equals(matrix2));
    }
}
package math;

import com.cgvsu.math.Vector;
import com.cgvsu.math.Vector3f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vector3fTest {

    @Test
    void sum() {
        final float[] m1 = {1, 2, 4};
        final float[] m2 = {3, 4, 5};
        Vector v1 = new Vector3f(m1);
        Vector v2 = new Vector3f(m2);
        final float[] mRes = {4, 6, 9};
        Vector expectedResult = new Vector3f(mRes);

        Vector result = v1.add(v2);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.get(i), result.get(i));
        }
    }

    @Test
    void subtract() {
        final float[] m1 = {1, 4, 6};
        final float[] m2 = {3, 2, 0};
        Vector v1 = new Vector3f(m1);
        Vector v2 = new Vector3f(m2);
        final float[] mRes = {-2, 2, 6};
        Vector expectedResult = new Vector3f(mRes);

        Vector result = v1.deduct(v2);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.get(i), result.get(i));
        }
    }

    @Test
    void multiply() {
        final float[] m1 = {1, 2, 8};
        Vector v1 = new Vector3f(m1);
        final float scalar = 5;
        final float[] mRes = {5, 10, 40};
        Vector expectedResult = new Vector3f(mRes);

        Vector result = v1.multiply(scalar);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.get(i), result.get(i));
        }

    }

    @Test
    void length() {
        final float[] m1 = {2, 1, 2};
        Vector v1 = new Vector3f(m1);
        final float expectedResult = 3;

        final float result = v1.length();

        assertEquals(expectedResult, result);
    }

    @Test
    void normalize() {
        final float[] m1 = {2, 1, 2};
        Vector v1 = new Vector3f(m1);
        final float[] mRes = {0.666F, 0.333F, 0.666F};
        Vector expectedResult = new Vector3f(mRes);

        Vector result = v1.normalize();

        for (int i = 0; i < expectedResult.length(); i++) {
            assertEquals(expectedResult.get(i), result.get(i), 0.001);
        }
    }

    @Test
    void scalarProduct() {
        final float[] m1 = {1, 2, 7};
        final float[] m2 = {4, 5, 6};
        Vector3f v1 = new Vector3f(m1);
        Vector3f v2 = new Vector3f(m2);
        final float expectedResult = 56;

        final float result = v1.multiply(v2).getX() + v1.multiply(v2).getY() + v1.multiply(v2).getZ();

        assertEquals(expectedResult, result, 1e-6);
    }

    @Test
    void vectorProduct() {
        final float[] m1 = {1, 2, 7};
        final float[] m2 = {4, 5, 6};
        Vector3f v1 = new Vector3f(m1);
        Vector3f v2 = new Vector3f(m2);
        final float[] mRes = {-23, 22, -3};
        Vector expectedResult = new Vector3f(mRes);

        Vector3f result = v1.crossProduct(v2);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.get(i), result.get(i));
        }
    }
}

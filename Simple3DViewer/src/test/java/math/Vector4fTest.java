package math;

import com.cgvsu.math.Vector;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;
import com.cgvsu.math.Vector4f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vector4fTest {

    @Test
    void sum() {
        final float[] m1 = {1, 2, 4, 0};
        final float[] m2 = {3, 4, 5, 7};
        Vector v1 = new Vector4f(m1);
        Vector v2 = new Vector4f(m2);
        final float[] mRes = {4, 6, 9, 7};
        Vector expectedResult = new Vector4f(mRes);

        Vector result = v1.add(v2);
        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.get(i), result.get(i));
        }
    }

    @Test
    void subtract() {
        final float[] m1 = {1, 4, 6, 0};
        final float[] m2 = {3, 2, 0, 7};
        Vector v1 = new Vector4f(m1);
        Vector v2 = new Vector4f(m2);
        final float[] mRes = {-2, 2, 6, -7};
        Vector expectedResult = new Vector4f(mRes);

        Vector result = v1.deduct(v2);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.get(i), result.get(i));
        }
    }

    @Test
    void multiplyOnScalar() {
        final float[] m1 = {1, -2, 8, 0};
        Vector v1 = new Vector4f(m1);
        final float scalar = 5;
        final float[] mRes = {5, -10, 40, 0};
        Vector expectedResult = new Vector4f(mRes);

        Vector result = v1.multiply(scalar);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.get(i), result.get(i));
        }
    }

    @Test
    void length() {
        final float[] m1 = {1, 4, 2, 2};
        Vector v1 = new Vector4f(m1);
        final float expectedResult = 5;

        final float result = v1.length();

        assertEquals(expectedResult, result);
    }

    @Test
    void normalize() {
        final float[] m1 = {1, 4, 2, 2};
        Vector v1 = new Vector4f(m1);
        final float[] mRes = {0.2F, 0.8F, 0.4F, 0.4F};
        Vector expectedResult = new Vector4f(mRes);

        Vector result = v1.normalize();

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.get(i), result.get(i));
        }
    }

    @Test
    void crossProduct() {
        final float[] m1 = {1, 2, 7, 4};
        final float[] m2 = {4, 5, 6, 0};
        Vector4f v1 = new Vector4f(m1);
        Vector4f v2 = new Vector4f(m2);
        final float[] expectedResult = {-23, 22, -3};
        Vector3f expectedVector = new Vector3f(expectedResult);

        Vector3f result = Vector4f.crossProduct(v1, v2);

        assertEquals(expectedVector.getX(), result.getX(), 1e-6);
        assertEquals(expectedVector.getY(), result.getY(), 1e-6);
        assertEquals(expectedVector.getZ(), result.getZ(), 1e-6);
    }
}

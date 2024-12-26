package math;

import com.cgvsu.math.Vector;
import com.cgvsu.math.Vector2f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vector2fTest {

    @Test
    void add() {
        final float[] m1 = {1, 2};
        final float[] m2 = {3, 4};

        Vector v1 = new Vector2f(m1);
        Vector v2 = new Vector2f(m2);

        final float[] mRes = {4, 6};
        Vector expectedResult = new Vector2f(mRes);

        Vector result = v1.add(v2);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.get(i), result.get(i));
        }
    }


    @Test
    void deduct() {
        final float[] m1 = {1, 4};
        final float[] m2 = {3, 2};

        Vector v1 = new Vector2f(m1);
        Vector v2 = new Vector2f(m2);

        final float[] mRes = {-2, 2};
        Vector expectedResult = new Vector2f(mRes);

        Vector result = v1.deduct(v2);
        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.get(i), result.get(i));
        }
    }

    @Test
    void multiplyOnScalar() {
        final float[] m1 = {1, 2};
        Vector v1 = new Vector2f(m1);
        final float scalar = 5;
        final float[] mRes = {5, 10};
        Vector expectedResult = new Vector2f(mRes);

        Vector result = v1.multiply(scalar);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.get(i), result.get(i));
        }
    }

    @Test
    void length() {
        final float[] m1 = {3, 4};
        Vector v1 = new Vector2f(m1);
        final float expectedResult = 5;

        final float result = v1.length();

        assertEquals(expectedResult, result);

    }

    @Test
    void normalize() {
        final float[] m1 = {3, 4};
        Vector v1 = new Vector2f(m1);
        final float[] mRes = {0.6F, 0.8F};
        Vector expectedResult = new Vector2f(mRes);

        Vector result = v1.normalize();


        for (int i = 0; i < expectedResult.length(); i++) {
            assertEquals(expectedResult.get(i), result.get(i));
        }
    }

    @Test
    void scalarProduct() {
        final float[] m1 = {1, 2};
        final float[] m2 = {4, 5};
        Vector2f v1 = new Vector2f(m1);
        Vector2f v2 = new Vector2f(m2);
        final float expectedResult = 14;

        final float result = v1.multiply(v2).getX() + v1.multiply(v2).getY();

        assertEquals(expectedResult, result);
    }
}
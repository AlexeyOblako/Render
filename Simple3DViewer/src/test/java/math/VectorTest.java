package math;

import com.cgvsu.math.Vector;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VectorTest {

    @Test
    public void testAdd() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(3, 4);
        Vector2f result = vector1.add(vector2);
        assertEquals(4, result.getX());
        assertEquals(6, result.getY());

        Vector3f vector3 = new Vector3f(1, 2, 3);
        Vector3f vector4 = new Vector3f(4, 5, 6);
        Vector3f result3 = vector3.add(vector4);
        assertEquals(5, result3.getX());
        assertEquals(7, result3.getY());
        assertEquals(9, result3.getZ());

        Vector4f vector5 = new Vector4f(1, 2, 3, 4);
        Vector4f vector6 = new Vector4f(5, 6, 7, 8);
        Vector4f result4 = vector5.add(vector6);
        assertEquals(6, result4.getX());
        assertEquals(8, result4.getY());
        assertEquals(10, result4.getZ());
        assertEquals(12, result4.getW());
    }

    @Test
    public void testDeduct() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(3, 4);
        Vector2f result = vector1.deduct(vector2);
        assertEquals(-2, result.getX());
        assertEquals(-2, result.getY());

        Vector3f vector3 = new Vector3f(1, 2, 3);
        Vector3f vector4 = new Vector3f(4, 5, 6);
        Vector3f result3 = vector3.deduct(vector4);
        assertEquals(-3, result3.getX());
        assertEquals(-3, result3.getY());
        assertEquals(-3, result3.getZ());

        Vector4f vector5 = new Vector4f(1, 2, 3, 4);
        Vector4f vector6 = new Vector4f(5, 6, 7, 8);
        Vector4f result4 = vector5.deduct(vector6);
        assertEquals(-4, result4.getX());
        assertEquals(-4, result4.getY());
        assertEquals(-4, result4.getZ());
        assertEquals(-4, result4.getW());
    }

    @Test
    public void testMultiply() {
        Vector2f vector = new Vector2f(1, 2);
        Vector2f result = vector.multiply(3);
        assertEquals(3, result.getX());
        assertEquals(6, result.getY());

        Vector3f vector3 = new Vector3f(1, 2, 3);
        Vector3f result3 = vector3.multiply(3);
        assertEquals(3, result3.getX());
        assertEquals(6, result3.getY());
        assertEquals(9, result3.getZ());

        Vector4f vector4 = new Vector4f(1, 2, 3, 4);
        Vector4f result4 = vector4.multiply(3);
        assertEquals(3, result4.getX());
        assertEquals(6, result4.getY());
        assertEquals(9, result4.getZ());
        assertEquals(12, result4.getW());
    }

    @Test
    public void testDivide() {
        Vector2f vector = new Vector2f(6, 9);
        Vector2f result = vector.divide(3);
        assertEquals(2, result.getX());
        assertEquals(3, result.getY());

        Vector3f vector3 = new Vector3f(6, 9, 12);
        Vector3f result3 = vector3.divide(3);
        assertEquals(2, result3.getX());
        assertEquals(3, result3.getY());
        assertEquals(4, result3.getZ());

        Vector4f vector4 = new Vector4f(6, 9, 12, 15);
        Vector4f result4 = vector4.divide(3);
        assertEquals(2, result4.getX());
        assertEquals(3, result4.getY());
        assertEquals(4, result4.getZ());
        assertEquals(5, result4.getW());
    }

    @Test
    public void testLength() {
        Vector2f vector = new Vector2f(3, 4);
        assertEquals(5, vector.length(), 1e-6);

        Vector3f vector3 = new Vector3f(3, 4, 5);
        assertEquals(Math.sqrt(50), vector3.length(), 1e-6);

        Vector4f vector4 = new Vector4f(1, 2, 3, 4);
        assertEquals(Math.sqrt(30), vector4.length(), 1e-6);
    }

    @Test
    public void testNormalize() {
        Vector2f vector = new Vector2f(3, 4);
        Vector2f result = vector.normalize();
        assertEquals(3 / 5.0, result.getX(), 1e-6);
        assertEquals(4 / 5.0, result.getY(), 1e-6);

        Vector3f vector3 = new Vector3f(3, 4, 5);
        Vector3f result3 = vector3.normalize();
        assertEquals(3 / Math.sqrt(50), result3.getX(), 1e-6);
        assertEquals(4 / Math.sqrt(50), result3.getY(), 1e-6);
        assertEquals(5 / Math.sqrt(50), result3.getZ(), 1e-6);

        Vector4f vector4 = new Vector4f(1, 2, 3, 4);
        Vector4f result4 = vector4.normalize();
        assertEquals(1 / Math.sqrt(30), result4.getX(), 1e-6);
        assertEquals(2 / Math.sqrt(30), result4.getY(), 1e-6);
        assertEquals(3 / Math.sqrt(30), result4.getZ(), 1e-6);
        assertEquals(4 / Math.sqrt(30), result4.getW(), 1e-6);
    }

    @Test
    public void testDot() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(3, 4);
        assertEquals(11, vector1.dot(vector2), 1e-6);

        Vector3f vector3 = new Vector3f(1, 2, 3);
        Vector3f vector4 = new Vector3f(4, 5, 6);
        assertEquals(32, vector3.dot(vector4), 1e-6);

        Vector4f vector5 = new Vector4f(1, 2, 3, 4);
        Vector4f vector6 = new Vector4f(5, 6, 7, 8);
        assertEquals(70, vector5.dot(vector6), 1e-6);
    }

    @Test
    public void testEquals() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(1, 2);
        assertTrue(vector1.equals(vector2));

        Vector3f vector3 = new Vector3f(1, 2, 3);
        Vector3f vector4 = new Vector3f(1, 2, 3);
        assertTrue(vector3.equals(vector4));

        Vector4f vector5 = new Vector4f(1, 2, 3, 4);
        Vector4f vector6 = new Vector4f(1, 2, 3, 4);
        assertTrue(vector5.equals(vector6));
    }

    @Test
    public void testToString() {
        Vector2f vector = new Vector2f(1, 2);
        assertEquals("Vector(1.0, 2.0)", vector.toString());

        Vector3f vector3 = new Vector3f(1, 2, 3);
        assertEquals("Vector(1.0, 2.0, 3.0)", vector3.toString());

        Vector4f vector4 = new Vector4f(1, 2, 3, 4);
        assertEquals("Vector(1.0, 2.0, 3.0, 4.0)", vector4.toString());
    }
}

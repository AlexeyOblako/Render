package math;

import com.cgvsu.math.Vector4f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector4fTest {

    @Test
    public void testConstructorWithFloats() {
        Vector4f vector = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        assertEquals(1.0f, vector.getX());
        assertEquals(2.0f, vector.getY());
        assertEquals(3.0f, vector.getZ());
        assertEquals(4.0f, vector.getW());
    }

    @Test
    public void testConstructorWithArray() {
        Vector4f vector = new Vector4f(new float[]{1.0f, 2.0f, 3.0f, 4.0f});
        assertEquals(1.0f, vector.getX());
        assertEquals(2.0f, vector.getY());
        assertEquals(3.0f, vector.getZ());
        assertEquals(4.0f, vector.getW());
    }

    @Test
    public void testConstructorWithArrayInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> new Vector4f(new float[]{1.0f, 2.0f, 3.0f}));
    }

    @Test
    public void testDefaultConstructor() {
        Vector4f vector = new Vector4f();
        assertEquals(0.0f, vector.getX());
        assertEquals(0.0f, vector.getY());
        assertEquals(0.0f, vector.getZ());
        assertEquals(0.0f, vector.getW());
    }

    @Test
    public void testGet() {
        Vector4f vector = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        assertEquals(1.0f, vector.get(0));
        assertEquals(2.0f, vector.get(1));
        assertEquals(3.0f, vector.get(2));
        assertEquals(4.0f, vector.get(3));
        assertThrows(IllegalArgumentException.class, () -> vector.get(4));
    }

    @Test
    public void testAdd() {
        Vector4f vector1 = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f vector2 = new Vector4f(5.0f, 6.0f, 7.0f, 8.0f);
        Vector4f result = vector1.add(vector2);
        assertEquals(6.0f, result.getX());
        assertEquals(8.0f, result.getY());
        assertEquals(10.0f, result.getZ());
        assertEquals(12.0f, result.getW());
    }

    @Test
    public void testStaticAdd() {
        Vector4f vector1 = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f vector2 = new Vector4f(5.0f, 6.0f, 7.0f, 8.0f);
        Vector4f result = Vector4f.add(vector1, vector2);
        assertEquals(6.0f, result.getX());
        assertEquals(8.0f, result.getY());
        assertEquals(10.0f, result.getZ());
        assertEquals(12.0f, result.getW());
    }

    @Test
    public void testDeduct() {
        Vector4f vector1 = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f vector2 = new Vector4f(5.0f, 6.0f, 7.0f, 8.0f);
        Vector4f result = vector1.deduct(vector2);
        assertEquals(-4.0f, result.getX());
        assertEquals(-4.0f, result.getY());
        assertEquals(-4.0f, result.getZ());
        assertEquals(-4.0f, result.getW());
    }

    @Test
    public void testStaticDeduct() {
        Vector4f vector1 = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f vector2 = new Vector4f(5.0f, 6.0f, 7.0f, 8.0f);
        Vector4f result = Vector4f.deduct(vector1, vector2);
        assertEquals(-4.0f, result.getX());
        assertEquals(-4.0f, result.getY());
        assertEquals(-4.0f, result.getZ());
        assertEquals(-4.0f, result.getW());
    }

    @Test
    public void testMultiply() {
        Vector4f vector = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f result = vector.multiply(2.0f);
        assertEquals(2.0f, result.getX());
        assertEquals(4.0f, result.getY());
        assertEquals(6.0f, result.getZ());
        assertEquals(8.0f, result.getW());
    }

    @Test
    public void testDivide() {
        Vector4f vector = new Vector4f(2.0f, 4.0f, 6.0f, 8.0f);
        Vector4f result = vector.divide(2.0f);
        assertEquals(1.0f, result.getX());
        assertEquals(2.0f, result.getY());
        assertEquals(3.0f, result.getZ());
        assertEquals(4.0f, result.getW());
    }

    @Test
    public void testDivideByZero() {
        Vector4f vector = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        assertThrows(ArithmeticException.class, () -> vector.divide(0.0f));
    }

    @Test
    public void testLength() {
        Vector4f vector = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        float expectedLength = (float) Math.sqrt(30.0f);
        assertEquals(expectedLength, vector.length(), 1e-5f);
    }

    @Test
    public void testNormalize() {
        Vector4f vector = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f result = vector.normalize();
        float length = vector.length();
        assertEquals(1.0f / length, result.getX(), 1e-5f);
        assertEquals(2.0f / length, result.getY(), 1e-5f);
        assertEquals(3.0f / length, result.getZ(), 1e-5f);
        assertEquals(4.0f / length, result.getW(), 1e-5f);
    }

    @Test
    public void testDot() {
        Vector4f vector1 = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f vector2 = new Vector4f(5.0f, 6.0f, 7.0f, 8.0f);
        assertEquals(70.0f, vector1.dot(vector2));
    }

    @Test
    public void testEquals() {
        Vector4f vector1 = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f vector2 = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        assertTrue(vector1.equals(vector2));
    }

    @Test
    public void testToString() {
        Vector4f vector = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        assertEquals("Vector4f: x = 1.0, y = 2.0, z = 3.0, w = 4.0", vector.toString());
    }
}

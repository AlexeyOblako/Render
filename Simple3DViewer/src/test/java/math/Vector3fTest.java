package math;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector3fTest {

    @Test
    public void testConstructorWithFloats() {
        Vector3f vector = new Vector3f(1.0f, 2.0f, 3.0f);
        assertEquals(1.0f, vector.getX());
        assertEquals(2.0f, vector.getY());
        assertEquals(3.0f, vector.getZ());
    }

    @Test
    public void testConstructorWithArray() {
        Vector3f vector = new Vector3f(new float[]{1.0f, 2.0f, 3.0f});
        assertEquals(1.0f, vector.getX());
        assertEquals(2.0f, vector.getY());
        assertEquals(3.0f, vector.getZ());
    }

    @Test
    public void testConstructorWithArrayInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> new Vector3f(new float[]{1.0f, 2.0f}));
    }

    @Test
    public void testDefaultConstructor() {
        Vector3f vector = new Vector3f();
        assertEquals(0.0f, vector.getX());
        assertEquals(0.0f, vector.getY());
        assertEquals(0.0f, vector.getZ());
    }

    @Test
    public void testGet() {
        Vector3f vector = new Vector3f(1.0f, 2.0f, 3.0f);
        assertEquals(1.0f, vector.get(0));
        assertEquals(2.0f, vector.get(1));
        assertEquals(3.0f, vector.get(2));
        assertThrows(IllegalArgumentException.class, () -> vector.get(3));
    }

    @Test
    public void testAdd() {
        Vector3f vector1 = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f vector2 = new Vector3f(4.0f, 5.0f, 6.0f);
        Vector3f result = Vector3f.add(vector1, vector2); // Используем статический метод
        assertEquals(5.0f, result.getX());
        assertEquals(7.0f, result.getY());
        assertEquals(9.0f, result.getZ());
    }

    @Test
    public void testStaticAdd() {
        Vector3f vector1 = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f vector2 = new Vector3f(4.0f, 5.0f, 6.0f);
        Vector3f result = Vector3f.add(vector1, vector2);
        assertEquals(5.0f, result.getX());
        assertEquals(7.0f, result.getY());
        assertEquals(9.0f, result.getZ());
    }

    @Test
    public void testDeduct() {
        Vector3f vector1 = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f vector2 = new Vector3f(4.0f, 5.0f, 6.0f);
        Vector3f result = vector1.deduct(vector2);
        assertEquals(-3.0f, result.getX());
        assertEquals(-3.0f, result.getY());
        assertEquals(-3.0f, result.getZ());
    }

    @Test
    public void testStaticDeduct() {
        Vector3f vector1 = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f vector2 = new Vector3f(4.0f, 5.0f, 6.0f);
        Vector3f result = Vector3f.deduct(vector1, vector2);
        assertEquals(-3.0f, result.getX());
        assertEquals(-3.0f, result.getY());
        assertEquals(-3.0f, result.getZ());
    }

    @Test
    public void testMultiply() {
        Vector3f vector = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f result = vector.multiply(3.0f);
        assertEquals(3.0f, result.getX());
        assertEquals(6.0f, result.getY());
        assertEquals(9.0f, result.getZ());
    }

    @Test
    public void testDivide() {
        Vector3f vector = new Vector3f(6.0f, 8.0f, 10.0f);
        Vector3f result = vector.divide(2.0f);
        assertEquals(3.0f, result.getX());
        assertEquals(4.0f, result.getY());
        assertEquals(5.0f, result.getZ());
    }

    @Test
    public void testDivideByZero() {
        Vector3f vector = new Vector3f(1.0f, 2.0f, 3.0f);
        assertThrows(ArithmeticException.class, () -> vector.divide(0.0f));
    }

    @Test
    public void testLength() {
        Vector3f vector = new Vector3f(3.0f, 4.0f, 5.0f);
        float expectedLength = (float) Math.sqrt(50.0f);
        assertEquals(expectedLength, vector.length(), 1e-5f);
    }

    @Test
    public void testNormalize() {
        Vector3f vector = new Vector3f(3.0f, 4.0f, 5.0f);
        Vector3f result = vector.normalize();
        float length = vector.length();
        assertEquals(3.0f / length, result.getX(), 1e-5f);
        assertEquals(4.0f / length, result.getY(), 1e-5f);
        assertEquals(5.0f / length, result.getZ(), 1e-5f);
    }

    @Test
    public void testDot() {
        Vector3f vector1 = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f vector2 = new Vector3f(4.0f, 5.0f, 6.0f);
        assertEquals(32.0f, vector1.dot(vector2));
    }

    @Test
    public void testEquals() {
        Vector3f vector1 = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f vector2 = new Vector3f(1.0f, 2.0f, 3.0f);
        assertTrue(vector1.equals(vector2));
    }

    @Test
    public void testToString() {
        Vector3f vector = new Vector3f(1.0f, 2.0f, 3.0f);
        assertEquals("Vector3f: x = 1.0, y = 2.0, z = 3.0", vector.toString());
    }

    @Test
    public void testVertex3fToVector2f() {
        Vector3f vertex = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector2f result = Vector3f.vertex3fToVector2f(vertex, 100, 100);
        assertEquals(150.0f, result.getX(), 1e-5f);
        assertEquals(-150.0f, result.getY(), 1e-5f);
    }

}

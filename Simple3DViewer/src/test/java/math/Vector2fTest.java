package math;

import com.cgvsu.math.Vector2f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2fTest {

    @Test
    public void testConstructorWithComponents() {
        Vector2f vector = new Vector2f(1, 2);
        assertEquals(1, vector.getX());
        assertEquals(2, vector.getY());
    }

    @Test
    public void testConstructorWithArray() {
        Vector2f vector = new Vector2f(new float[]{1, 2});
        assertEquals(1, vector.getX());
        assertEquals(2, vector.getY());
    }

    @Test
    public void testConstructorDefault() {
        Vector2f vector = new Vector2f();
        assertEquals(0, vector.getX());
        assertEquals(0, vector.getY());
    }

    @Test
    public void testGet() {
        Vector2f vector = new Vector2f(1, 2);
        assertEquals(1, vector.get(0));
        assertEquals(2, vector.get(1));
    }

    @Test
    public void testGetInvalidIndex() {
        Vector2f vector = new Vector2f(1, 2);
        assertThrows(IllegalArgumentException.class, () -> vector.get(2));
    }

    @Test
    public void testAdd() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(3, 4);
        Vector2f result = vector1.add(vector2);
        assertEquals(4, result.getX());
        assertEquals(6, result.getY());
    }

    @Test
    public void testStaticAdd() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(3, 4);
        Vector2f result = Vector2f.add(vector1, vector2);
        assertEquals(4, result.getX());
        assertEquals(6, result.getY());
    }

    @Test
    public void testDeduct() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(3, 4);
        Vector2f result = vector1.deduct(vector2);
        assertEquals(-2, result.getX());
        assertEquals(-2, result.getY());
    }

    @Test
    public void testStaticDeduct() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(3, 4);
        Vector2f result = Vector2f.deduct(vector1, vector2);
        assertEquals(-2, result.getX());
        assertEquals(-2, result.getY());
    }

    @Test
    public void testMultiply() {
        Vector2f vector = new Vector2f(1, 2);
        Vector2f result = vector.multiply(3);
        assertEquals(3, result.getX());
        assertEquals(6, result.getY());
    }

    @Test
    public void testDivide() {
        Vector2f vector = new Vector2f(6, 9);
        Vector2f result = vector.divide(3);
        assertEquals(2, result.getX());
        assertEquals(3, result.getY());
    }

    @Test
    public void testDivideByZero() {
        Vector2f vector = new Vector2f(6, 9);
        assertThrows(ArithmeticException.class, () -> vector.divide(0));
    }

    @Test
    public void testLength() {
        Vector2f vector = new Vector2f(3, 4);
        assertEquals(5, vector.length(), 1e-6);
    }

    @Test
    public void testNormalize() {
        Vector2f vector = new Vector2f(3, 4);
        Vector2f result = vector.normalize();
        assertEquals(3/5.0, result.getX(), 1e-6);
        assertEquals(4/5.0, result.getY(), 1e-6);
    }

    @Test
    public void testDot() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(3, 4);
        assertEquals(11, vector1.dot(vector2), 1e-6);
    }

    @Test
    public void testEquals() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(1, 2);
        assertTrue(vector1.equals(vector2));
    }

    @Test
    public void testNotEquals() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(3, 4);
        assertFalse(vector1.equals(vector2));
    }

    @Test
    public void testToString() {
        Vector2f vector = new Vector2f(1, 2);
        assertEquals("Vector2f: x = 1.0, y = 2.0", vector.toString());
    }

    @Test
    public void testTo() {
        Vector2f start = new Vector2f(1, 2);
        Vector2f end = new Vector2f(4, 6);
        Vector2f result = start.to(end);
        assertEquals(3, result.getX());
        assertEquals(4, result.getY());
    }

    @Test
    public void testSet() {
        Vector2f vector = new Vector2f(1, 2);
        vector.set(3, 4);
        assertEquals(3, vector.getX());
        assertEquals(4, vector.getY());
    }

    @Test
    public void testCrossMagnitude() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(3, 4);
        assertEquals(-2, vector1.crossMagnitude(vector2), 1e-6);
    }
}

package math;

import com.cgvsu.math.Vector2f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2fTest {

    @Test
    public void testConstructorWithFloats() {
        Vector2f vector = new Vector2f(1.0f, 2.0f);
        assertEquals(1.0f, vector.getX());
        assertEquals(2.0f, vector.getY());
    }

    @Test
    public void testConstructorWithArray() {
        Vector2f vector = new Vector2f(new float[]{1.0f, 2.0f});
        assertEquals(1.0f, vector.getX());
        assertEquals(2.0f, vector.getY());
    }

    @Test
    public void testConstructorWithArrayInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> new Vector2f(new float[]{1.0f}));
    }

    @Test
    public void testDefaultConstructor() {
        Vector2f vector = new Vector2f();
        assertEquals(0.0f, vector.getX());
        assertEquals(0.0f, vector.getY());
    }

    @Test
    public void testGet() {
        Vector2f vector = new Vector2f(1.0f, 2.0f);
        assertEquals(1.0f, vector.get(0));
        assertEquals(2.0f, vector.get(1));
        assertThrows(IllegalArgumentException.class, () -> vector.get(2));
    }

    @Test
    public void testAdd() {
        Vector2f vector1 = new Vector2f(1.0f, 2.0f);
        Vector2f vector2 = new Vector2f(3.0f, 4.0f);
        Vector2f result = vector1.add(vector2);
        assertEquals(4.0f, result.getX());
        assertEquals(6.0f, result.getY());
    }

    @Test
    public void testStaticAdd() {
        Vector2f vector1 = new Vector2f(1.0f, 2.0f);
        Vector2f vector2 = new Vector2f(3.0f, 4.0f);
        Vector2f result = Vector2f.add(vector1, vector2);
        assertEquals(4.0f, result.getX());
        assertEquals(6.0f, result.getY());
    }

    @Test
    public void testDeduct() {
        Vector2f vector1 = new Vector2f(1.0f, 2.0f);
        Vector2f vector2 = new Vector2f(3.0f, 4.0f);
        Vector2f result = vector1.deduct(vector2);
        assertEquals(-2.0f, result.getX());
        assertEquals(-2.0f, result.getY());
    }

    @Test
    public void testStaticDeduct() {
        Vector2f vector1 = new Vector2f(1.0f, 2.0f);
        Vector2f vector2 = new Vector2f(3.0f, 4.0f);
        Vector2f result = Vector2f.deduct(vector1, vector2);
        assertEquals(-2.0f, result.getX());
        assertEquals(-2.0f, result.getY());
    }

    @Test
    public void testMultiply() {
        Vector2f vector = new Vector2f(1.0f, 2.0f);
        Vector2f result = vector.multiply(3.0f);
        assertEquals(3.0f, result.getX());
        assertEquals(6.0f, result.getY());
    }

    @Test
    public void testDivide() {
        Vector2f vector = new Vector2f(6.0f, 8.0f);
        Vector2f result = vector.divide(2.0f);
        assertEquals(3.0f, result.getX());
        assertEquals(4.0f, result.getY());
    }

    @Test
    public void testDivideByZero() {
        Vector2f vector = new Vector2f(1.0f, 2.0f);
        assertThrows(ArithmeticException.class, () -> vector.divide(0.0f));
    }

    @Test
    public void testLength() {
        Vector2f vector = new Vector2f(3.0f, 4.0f);
        assertEquals(5.0f, vector.length());
    }

    @Test
    public void testNormalize() {
        Vector2f vector = new Vector2f(3.0f, 4.0f);
        Vector2f result = vector.normalize();
        assertEquals(3.0f / 5.0f, result.getX());
        assertEquals(4.0f / 5.0f, result.getY());
    }

    @Test
    public void testDot() {
        Vector2f vector1 = new Vector2f(1.0f, 2.0f);
        Vector2f vector2 = new Vector2f(3.0f, 4.0f);
        assertEquals(11.0f, vector1.dot(vector2));
    }

    @Test
    public void testEquals() {
        Vector2f vector1 = new Vector2f(1.0f, 2.0f);
        Vector2f vector2 = new Vector2f(1.0f, 2.0f);
        assertTrue(vector1.equals(vector2));
    }

    @Test
    public void testToString() {
        Vector2f vector = new Vector2f(1.0f, 2.0f);
        assertEquals("Vector2f: x = 1.0, y = 2.0", vector.toString());
    }

    @Test
    public void testTo() {
        Vector2f start = new Vector2f(1.0f, 2.0f);
        Vector2f end = new Vector2f(4.0f, 6.0f);
        Vector2f result = start.to(end);
        assertEquals(3.0f, result.getX());
        assertEquals(4.0f, result.getY());
    }

    @Test
    public void testSet() {
        Vector2f vector = new Vector2f();
        vector.set(1.0f, 2.0f);
        assertEquals(1.0f, vector.getX());
        assertEquals(2.0f, vector.getY());
    }

    @Test
    public void testCrossMagnitude() {
        Vector2f vector1 = new Vector2f(1.0f, 2.0f);
        Vector2f vector2 = new Vector2f(3.0f, 4.0f);
        assertEquals(-2.0f, vector1.crossMagnitude(vector2));
    }
}

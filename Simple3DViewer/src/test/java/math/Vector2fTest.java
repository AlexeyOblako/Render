package math;

import com.cgvsu.math.Vector2f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2fTest {

    @Test
    public void testCrossMagnitude() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(3, 4);
        assertEquals(-2, vector1.crossMagnitude(vector2), 1e-6);
    }

    @Test
    public void testTo() {
        Vector2f start = new Vector2f(1, 2);
        Vector2f end = new Vector2f(4, 6);
        Vector2f result = start.to(end);
        assertEquals(3, result.getX());
        assertEquals(4, result.getY());
    }
}

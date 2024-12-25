package math;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector3fTest {

    @Test
    public void testCrossProduct() {
        Vector3f vector1 = new Vector3f(1, 2, 3);
        Vector3f vector2 = new Vector3f(4, 5, 6);
        Vector3f result = vector1.crossProduct(vector2);
        assertEquals(-3, result.getX(), 1e-5f);
        assertEquals(6, result.getY(), 1e-5f);
        assertEquals(-3, result.getZ(), 1e-5f);
    }

    @Test
    public void testVertex3fToVector2f() {
        Vector3f vertex = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector2f result = Vector3f.vertex3fToVector2f(vertex, 100, 100);
        assertEquals(150.0f, result.getX(), 1e-5f);
        assertEquals(-150.0f, result.getY(), 1e-5f);
    }
}

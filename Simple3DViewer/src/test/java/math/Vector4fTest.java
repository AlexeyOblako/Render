package math;

import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector4fTest {

    @Test
    public void testNormalizeTo3f() {
        Vector4f vector = new Vector4f(1, 2, 3, 4);
        Vector3f result = vector.normalizeTo3f();
        assertEquals(1 / 4.0, result.getX(), 1e-5f);
        assertEquals(2 / 4.0, result.getY(), 1e-5f);
        assertEquals(3 / 4.0, result.getZ(), 1e-5f);
    }
}

package render_engine;

import com.cgvsu.render_engine.ModelTransform;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTransformTest {

    @Test
    public void testSetScale() {
        ModelTransform transform = new ModelTransform();
        transform.setScale(2.0f, 3.0f, 4.0f);

        assertEquals(2.0f, transform.getScaleX(), 1e-6);
        assertEquals(3.0f, transform.getScaleY(), 1e-6);
        assertEquals(4.0f, transform.getScaleZ(), 1e-6);
    }

    @Test
    public void testSetRotation() {
        ModelTransform transform = new ModelTransform();
        transform.setRotation(45.0f, 90.0f, 180.0f);

        assertEquals(45.0f, transform.getRotationX(), 1e-6);
        assertEquals(90.0f, transform.getRotationY(), 1e-6);
        assertEquals(180.0f, transform.getRotationZ(), 1e-6);
    }

    @Test
    public void testSetTranslation() {
        ModelTransform transform = new ModelTransform();
        transform.setTranslation(1.0f, 2.0f, 3.0f);

        assertEquals(1.0f, transform.getTranslationX(), 1e-6);
        assertEquals(2.0f, transform.getTranslationY(), 1e-6);
        assertEquals(3.0f, transform.getTranslationZ(), 1e-6);
    }
}

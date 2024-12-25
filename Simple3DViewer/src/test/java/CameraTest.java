import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.cgvsu.math.Vector3f;
import com.cgvsu.render_engine.Camera;
import org.junit.Before;
import org.junit.Test;

public class CameraTest {
    private Camera camera;
    private Vector3f initialPosition;
    private Vector3f target;

    @Before
    public void setUp() {
        initialPosition = new Vector3f(0, 0, 100);
        target = new Vector3f(0, 0, 0);
        camera = new Camera(initialPosition, target, 1.0f, 1, 0.01f, 100);
    }

    @Test
    public void testRotateAroundPositionYaw0() {
        camera.rotateAroundPosition(0, 0); // Поворот на 0 градусов по yaw
        Vector3f expectedPosition = new Vector3f(0.0f, 0.0f, 100.0f);
        Vector3f expectedDirection = new Vector3f(0.0f, 0.0f, -1.0f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw5() {
        camera.rotateAroundPosition(5, 0); // Поворот на 5 градусов по yaw
        Vector3f expectedPosition = new Vector3f(8.715574f, 0.0f, 99.61947f);
        Vector3f expectedDirection = new Vector3f(-0.087155744f, 0.0f, -0.9961947f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw10() {
        camera.rotateAroundPosition(10, 0); // Поворот на 10 градусов по yaw
        Vector3f expectedPosition = new Vector3f(17.364818f, 0.0f, 98.480775f);
        Vector3f expectedDirection = new Vector3f(-0.17364818f, 0.0f, -0.98480775f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw15() {
        camera.rotateAroundPosition(15, 0); // Поворот на 15 градусов по yaw
        Vector3f expectedPosition = new Vector3f(25.881904f, 0.0f, 95.693195f);
        Vector3f expectedDirection = new Vector3f(-0.25881904f, 0.0f, -0.96592583f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw20() {
        camera.rotateAroundPosition(20, 0); // Поворот на 20 градусов по yaw
        Vector3f expectedPosition = new Vector3f(34.202014f, 0.0f, 92.231854f);
        Vector3f expectedDirection = new Vector3f(-0.34202014f, 0.0f, -0.93969262f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw25() {
        camera.rotateAroundPosition(25, 0); // Поворот на 25 градусов по yaw
        Vector3f expectedPosition = new Vector3f(42.261826f, 0.0f, 88.235294f);
        Vector3f expectedDirection = new Vector3f(-0.42261826f, 0.0f, -0.90630779f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw30() {
        camera.rotateAroundPosition(30, 0); // Поворот на 30 градусов по yaw
        Vector3f expectedPosition = new Vector3f(50.0f, 0.0f, 83.867055f);
        Vector3f expectedDirection = new Vector3f(-0.5f, 0.0f, -0.8660254f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw35() {
        camera.rotateAroundPosition(35, 0); // Поворот на 35 градусов по yaw
        Vector3f expectedPosition = new Vector3f(57.357643f, 0.0f, 79.209037f);
        Vector3f expectedDirection = new Vector3f(-0.57357644f, 0.0f, -0.81915204f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw40() {
        camera.rotateAroundPosition(40, 0); // Поворот на 40 градусов по yaw
        Vector3f expectedPosition = new Vector3f(64.27876f, 0.0f, 74.31448f);
        Vector3f expectedDirection = new Vector3f(-0.6427876f, 0.0f, -0.76604444f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw45() {
        camera.rotateAroundPosition(45, 0); // Поворот на 45 градусов по yaw
        Vector3f expectedPosition = new Vector3f(70.710678f, 0.0f, 69.29289f);
        Vector3f expectedDirection = new Vector3f(-0.70710678f, 0.0f, -0.70710678f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw50() {
        camera.rotateAroundPosition(50, 0); // Поворот на 50 градусов по yaw
        Vector3f expectedPosition = new Vector3f(76.604444f, 0.0f, 64.27876f);
        Vector3f expectedDirection = new Vector3f(-0.76604444f, 0.0f, -0.6427876f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw55() {
        camera.rotateAroundPosition(55, 0); // Поворот на 55 градусов по yaw
        Vector3f expectedPosition = new Vector3f(81.915204f, 0.0f, 59.299457f);
        Vector3f expectedDirection = new Vector3f(-0.81915204f, 0.0f, -0.57357644f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw60() {
        camera.rotateAroundPosition(60, 0); // Поворот на 60 градусов по yaw
        Vector3f expectedPosition = new Vector3f(86.60254f, 0.0f, 54.306805f);
        Vector3f expectedDirection = new Vector3f(-0.8660254f, 0.0f, -0.5f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw65() {
        camera.rotateAroundPosition(65, 0); // Поворот на 65 градусов по yaw
        Vector3f expectedPosition = new Vector3f(90.630779f, 0.0f, 49.580223f);
        Vector3f expectedDirection = new Vector3f(-0.90630779f, 0.0f, -0.42261826f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw70() {
        camera.rotateAroundPosition(70, 0); // Поворот на 70 градусов по yaw
        Vector3f expectedPosition = new Vector3f(94.0f, 0.0f, 44.948974f);
        Vector3f expectedDirection = new Vector3f(-0.93969262f, 0.0f, -0.34202014f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw75() {
        camera.rotateAroundPosition(75, 0); // Поворот на 75 градусов по yaw
        Vector3f expectedPosition = new Vector3f(96.630779f, 0.0f, 40.557106f);
        Vector3f expectedDirection = new Vector3f(-0.96592583f, 0.0f, -0.25881904f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw80() {
        camera.rotateAroundPosition(80, 0); // Поворот на 80 градусов по yaw
        Vector3f expectedPosition = new Vector3f(98.480775f, 0.0f, 36.481273f);
        Vector3f expectedDirection = new Vector3f(-0.98480775f, 0.0f, -0.17364818f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw85() {
        camera.rotateAroundPosition(85, 0); // Поворот на 85 градусов по yaw
        Vector3f expectedPosition = new Vector3f(99.61947f, 0.0f, 32.740386f);
        Vector3f expectedDirection = new Vector3f(-0.9961947f, 0.0f, -0.087155744f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw90() {
        camera.rotateAroundPosition(90, 0); // Поворот на 90 градусов по yaw
        Vector3f expectedPosition = new Vector3f(100.0f, 0.0f, 29.393876f);
        Vector3f expectedDirection = new Vector3f(-1.0f, 0.0f, 0.0f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw95() {
        camera.rotateAroundPosition(95, 0); // Поворот на 95 градусов по yaw
        Vector3f expectedPosition = new Vector3f(99.61947f, 0.0f, 26.419753f);
        Vector3f expectedDirection = new Vector3f(-0.9961947f, 0.0f, 0.087155744f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw100() {
        camera.rotateAroundPosition(100, 0); // Поворот на 100 градусов по yaw
        Vector3f expectedPosition = new Vector3f(98.480775f, 0.0f, 23.876482f);
        Vector3f expectedDirection = new Vector3f(-0.98480775f, 0.0f, 0.17364818f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw105() {
        camera.rotateAroundPosition(105, 0); // Поворот на 105 градусов по yaw
        Vector3f expectedPosition = new Vector3f(96.630779f, 0.0f, 21.759612f);
        Vector3f expectedDirection = new Vector3f(-0.96592583f, 0.0f, 0.25881904f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw110() {
        camera.rotateAroundPosition(110, 0); // Поворот на 110 градусов по yaw
        Vector3f expectedPosition = new Vector3f(94.0f, 0.0f, 19.993918f);
        Vector3f expectedDirection = new Vector3f(-0.93969262f, 0.0f, 0.34202014f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw115() {
        camera.rotateAroundPosition(115, 0); // Поворот на 115 градусов по yaw
        Vector3f expectedPosition = new Vector3f(90.630779f, 0.0f, 18.577382f);
        Vector3f expectedDirection = new Vector3f(-0.90630779f, 0.0f, 0.42261826f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw120() {
        camera.rotateAroundPosition(120, 0); // Поворот на 120 градусов по yaw
        Vector3f expectedPosition = new Vector3f(86.60254f, 0.0f, 17.507005f);
        Vector3f expectedDirection = new Vector3f(-0.8660254f, 0.0f, 0.5f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw125() {
        camera.rotateAroundPosition(125, 0); // Поворот на 125 градусов по yaw
        Vector3f expectedPosition = new Vector3f(81.915204f, 0.0f, 16.747919f);
        Vector3f expectedDirection = new Vector3f(-0.81915204f, 0.0f, 0.57357644f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw130() {
        camera.rotateAroundPosition(130, 0); // Поворот на 130 градусов по yaw
        Vector3f expectedPosition = new Vector3f(76.60254f, 0.0f, 16.30127f);
        Vector3f expectedDirection = new Vector3f(-0.76604444f, 0.0f, 0.6427876f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw135() {
        camera.rotateAroundPosition(135, 0); // Поворот на 135 градусов по yaw
        Vector3f expectedPosition = new Vector3f(70.710678f, 0.0f, 16.187106f);
        Vector3f expectedDirection = new Vector3f(-0.70710678f, 0.0f, 0.70710678f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw140() {
        camera.rotateAroundPosition(140, 0); // Поворот на 140 градусов по yaw
        Vector3f expectedPosition = new Vector3f(64.27876f, 0.0f, 16.387365f);
        Vector3f expectedDirection = new Vector3f(-0.6427876f, 0.0f, 0.76604444f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw145() {
        camera.rotateAroundPosition(145, 0); // Поворот на 145 градусов по yaw
        Vector3f expectedPosition = new Vector3f(57.357643f, 0.0f, 16.876424f);
        Vector3f expectedDirection = new Vector3f(-0.57357644f, 0.0f, 0.81915204f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw150() {
        camera.rotateAroundPosition(150, 0); // Поворот на 150 градусов по yaw
        Vector3f expectedPosition = new Vector3f(50.0f, 0.0f, 17.637059f);
        Vector3f expectedDirection = new Vector3f(-0.5f, 0.0f, 0.8660254f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw155() {
        camera.rotateAroundPosition(155, 0); // Поворот на 155 градусов по yaw
        Vector3f expectedPosition = new Vector3f(42.261826f, 0.0f, 18.632941f);
        Vector3f expectedDirection = new Vector3f(-0.42261826f, 0.0f, 0.90630779f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw160() {
        camera.rotateAroundPosition(160, 0); // Поворот на 160 градусов по yaw
        Vector3f expectedPosition = new Vector3f(34.202014f, 0.0f, 19.90381f);
        Vector3f expectedDirection = new Vector3f(-0.34202014f, 0.0f, 0.93969262f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw165() {
        camera.rotateAroundPosition(165, 0); // Поворот на 165 градусов по yaw
        Vector3f expectedPosition = new Vector3f(25.881904f, 0.0f, 21.428932f);
        Vector3f expectedDirection = new Vector3f(-0.25881904f, 0.0f, 0.96592583f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw170() {
        camera.rotateAroundPosition(170, 0); // Поворот на 170 градусов по yaw
        Vector3f expectedPosition = new Vector3f(17.364818f, 0.0f, 23.205081f);
        Vector3f expectedDirection = new Vector3f(-0.17364818f, 0.0f, 0.98480775f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw175() {
        camera.rotateAroundPosition(175, 0); // Поворот на 175 градусов по yaw
        Vector3f expectedPosition = new Vector3f(8.715574f, 0.0f, 25.181533f);
        Vector3f expectedDirection = new Vector3f(-0.087155744f, 0.0f, 0.9961947f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }

    @Test
    public void testRotateAroundPositionYaw180() {
        camera.rotateAroundPosition(180, 0); // Поворот на 180 градусов по yaw
        Vector3f expectedPosition = new Vector3f(0.0f, 0.0f, 27.322331f);
        Vector3f expectedDirection = new Vector3f(0.0f, 0.0f, 1.0f);

        assertEquals(expectedPosition.getX(), camera.getPosition().getX(), 1e-6);
        assertEquals(expectedPosition.getY(), camera.getPosition().getY(), 1e-6);
        assertEquals(expectedPosition.getZ(), camera.getPosition().getZ(), 1e-6);
        assertTrue(camera.getTarget().deduct(camera.getPosition()).normalize().equals(expectedDirection));
    }
}

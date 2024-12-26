package render_engine;

import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.render_engine.GraphicConveyor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraphicConveyorTest {

    @Test
    public void testScaleMatrix4f() {
        float scaleX = 2.0f;
        float scaleY = 3.0f;
        float scaleZ = 4.0f;
        Matrix4f scaleMatrix = GraphicConveyor.scaleMatrix4f(scaleX, scaleY, scaleZ);

        float[][] expected = {
                {scaleX, 0, 0, 0},
                {0, scaleY, 0, 0},
                {0, 0, scaleZ, 0},
                {0, 0, 0, 1}
        };

        assertArrayEquals(expected, scaleMatrix.getMatrix());
    }

    @Test
    public void testRotateX() {
        float angleX = 90.0f;
        Matrix4f rotateXMatrix = GraphicConveyor.rotateX(angleX);

        float cos = 0.0f;
        float sin = 1.0f;
        float[][] expected = {
                {1, 0, 0, 0},
                {0, cos, sin, 0},
                {0, -sin, cos, 0},
                {0, 0, 0, 1}
        };

        float delta = 1e-6f; // допустимая погрешность
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                assertEquals(expected[i][j], rotateXMatrix.getMatrix()[i][j], delta, "Mismatch at [" + i + "][" + j + "]");
            }
        }
    }


    @Test
    public void testRotateY() {
        float angleY = 90.0f;
        Matrix4f rotateYMatrix = GraphicConveyor.rotateY(angleY);

        float cos = 0.0f;
        float sin = 1.0f;
        float[][] expected = {
                {cos, 0, sin, 0},
                {0, 1, 0, 0},
                {-sin, 0, cos, 0},
                {0, 0, 0, 1}
        };

        float delta = 1e-6f; // допустимая погрешность
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                assertEquals(expected[i][j], rotateYMatrix.getMatrix()[i][j], delta, "Mismatch at [" + i + "][" + j + "]");
            }
        }
    }


    @Test
    public void testRotateZ() {
        float angleZ = 90.0f;
        Matrix4f rotateZMatrix = GraphicConveyor.rotateZ(angleZ);

        float cos = 0.0f;
        float sin = 1.0f;
        float[][] expected = {
                {cos, sin, 0, 0},
                {-sin, cos, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };

        float delta = 1e-6f; // допустимая погрешность
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                assertEquals(expected[i][j], rotateZMatrix.getMatrix()[i][j], delta, "Mismatch at [" + i + "][" + j + "]");
            }
        }
    }


    @Test
    public void testRotateMatrix4f() {
        float angleX = 90.0f;
        float angleY = 90.0f;
        float angleZ = 90.0f;
        Matrix4f rotateMatrix = GraphicConveyor.rotateMatrix4f(angleX, angleY, angleZ);

        Matrix4f rotateXMatrix = GraphicConveyor.rotateX(angleX);
        Matrix4f rotateYMatrix = GraphicConveyor.rotateY(angleY);
        Matrix4f rotateZMatrix = GraphicConveyor.rotateZ(angleZ);

        Matrix4f expected = Matrix4f.multiply(rotateZMatrix, Matrix4f.multiply(rotateYMatrix, rotateXMatrix));

        assertArrayEquals(expected.getMatrix(), rotateMatrix.getMatrix());
    }

    @Test
    public void testTranslationMatrix4f() {
        float translationX = 1.0f;
        float translationY = 2.0f;
        float translationZ = 3.0f;
        Matrix4f translationMatrix = GraphicConveyor.translationMatrix4f(translationX, translationY, translationZ);

        float[][] expected = {
                {1, 0, 0, translationX},
                {0, 1, 0, translationY},
                {0, 0, 1, translationZ},
                {0, 0, 0, 1}
        };

        assertArrayEquals(expected, translationMatrix.getMatrix());
    }

    @Test
    public void testRotateScaleTranslate() {
        float scaleX = 2.0f;
        float scaleY = 3.0f;
        float scaleZ = 4.0f;
        float angleX = 90.0f;
        float angleY = 90.0f;
        float angleZ = 90.0f;
        float translationX = 1.0f;
        float translationY = 2.0f;
        float translationZ = 3.0f;
        Matrix4f rotateScaleTranslateMatrix = GraphicConveyor.rotateScaleTranslate(
                scaleX, scaleY, scaleZ,
                angleX, angleY, angleZ,
                translationX, translationY, translationZ);

        Matrix4f scaleMatrix = GraphicConveyor.scaleMatrix4f(scaleX, scaleY, scaleZ);
        Matrix4f rotateMatrix = GraphicConveyor.rotateMatrix4f(angleX, angleY, angleZ);
        Matrix4f translationMatrix = GraphicConveyor.translationMatrix4f(translationX, translationY, translationZ);

        Matrix4f expected = Matrix4f.multiply(translationMatrix, Matrix4f.multiply(rotateMatrix, scaleMatrix));

        assertArrayEquals(expected.getMatrix(), rotateScaleTranslateMatrix.getMatrix());
    }

    @Test
    public void testLookAt() {
        Vector3f eye = new Vector3f(0, 0, 1);
        Vector3f target = new Vector3f(0, 0, 0);
        Vector3f up = new Vector3f(0, 1, 0);
        Matrix4f lookAtMatrix = GraphicConveyor.lookAt(eye, target, up);

        Vector3f resultZ = Vector3f.deduct(target, eye).normalize();
        Vector3f resultX = Vector3f.crossProduct(up, resultZ).normalize();
        Vector3f resultY = Vector3f.crossProduct(resultZ, resultX).normalize();

        float[][] expected = {
                {resultX.getX(), resultY.getX(), resultZ.getX(), -resultX.dot(eye)},
                {resultX.getY(), resultY.getY(), resultZ.getY(), -resultY.dot(eye)},
                {resultX.getZ(), resultY.getZ(), resultZ.getZ(), -resultZ.dot(eye)},
                {0, 0, 0, 1}
        };

        assertArrayEquals(expected, lookAtMatrix.getMatrix());
    }

    @Test
    public void testPerspective() {
        float fov = (float) Math.toRadians(45.0);
        float aspectRatio = 1.0f;
        float nearPlane = 0.1f;
        float farPlane = 100.0f;
        Matrix4f perspectiveMatrix = GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);

        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        float[][] expected = {
                {tangentMinusOnDegree / aspectRatio, 0, 0, 0},
                {0, tangentMinusOnDegree, 0, 0},
                {0, 0, (farPlane + nearPlane) / (nearPlane - farPlane), (2 * farPlane * nearPlane) / (nearPlane - farPlane)},
                {0, 0, -1.0F, 0}
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], perspectiveMatrix.getMatrix()[i][j], 1e-6);
            }
        }
    }
}

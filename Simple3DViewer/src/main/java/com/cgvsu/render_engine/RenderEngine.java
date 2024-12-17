package com.cgvsu.render_engine;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector4f;
import javafx.scene.canvas.GraphicsContext;
import com.cgvsu.model.Model;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.matrix.Matrix4f;
import javax.vecmath.Point2f;
import static com.cgvsu.render_engine.GraphicConveyor.*;
import com.cgvsu.utils.triangles_utils.TriangleRasterization;
import com.cgvsu.utils.ZBuffer;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height
    ) {
        // Создаем Z-буфер
        ArrayList<ArrayList<Float>> zBuffer = ZBuffer.getDefaultPixelDepthMatrix(width, height);

        // Матрицы преобразования
        Matrix4f modelMatrix = rotateScaleTranslate(
                mesh.getScale().getX(), mesh.getScale().getY(), mesh.getScale().getZ(),
                mesh.getRotation().getX(), mesh.getRotation().getY(), mesh.getRotation().getZ(),
                mesh.getTranslation().getX(), mesh.getTranslation().getY(), mesh.getTranslation().getZ()
        );
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = Matrix4f.multiply(projectionMatrix, Matrix4f.multiply(viewMatrix, modelMatrix));

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            ArrayList<Vector3f> vertices3D = new ArrayList<>();

            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
                vertices3D.add(vertex);

                Vector4f vertexVecmath = new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1);
                Vector3f transformedVertex = Matrix4f.multiply(modelViewProjectionMatrix, vertexVecmath).normalizeTo3f();

                Point2f resultPoint = vertexToPoint(transformedVertex, width, height);
                resultPoints.add(resultPoint);
            }

            // Растеризация треугольников
            for (int i = 0; i < nVerticesInPolygon - 2; i++) {
                Point2f v1 = resultPoints.get(0);
                Point2f v2 = resultPoints.get(i + 1);
                Point2f v3 = resultPoints.get(i + 2);

                Vector3f v1_3d = vertices3D.get(0);
                Vector3f v2_3d = vertices3D.get(i + 1);
                Vector3f v3_3d = vertices3D.get(i + 2);

                // Используем Z-буфер для управления видимостью
                rasterizeTriangle(graphicsContext, v1, v2, v3, v1_3d, v2_3d, v3_3d, zBuffer, width, height, Color.BLUE);
            }
        }
    }

    private static void rasterizeTriangle(
            GraphicsContext gc,
            Point2f v1, Point2f v2, Point2f v3,
            Vector3f v1_3d, Vector3f v2_3d, Vector3f v3_3d,
            ArrayList<ArrayList<Float>> zBuffer,
            int width, int height,
            Color color
    ) {
        // Преобразуем точки в 2D
        ArrayList<Vector2f> triangle2D = new ArrayList<>();
        triangle2D.add(new Vector2f(v1.x, v1.y));
        triangle2D.add(new Vector2f(v2.x, v2.y));
        triangle2D.add(new Vector2f(v3.x, v3.y));

        // Вызываем растеризацию треугольника
        TriangleRasterization.drawTriangle(gc, triangle2D, color, color, color);

        // Обновляем Z-буфер
        updateZBuffer(v1, v2, v3, v1_3d, v2_3d, v3_3d, zBuffer, width, height);
    }

    private static void updateZBuffer(
            Point2f v1, Point2f v2, Point2f v3,
            Vector3f v1_3d, Vector3f v2_3d, Vector3f v3_3d,
            ArrayList<ArrayList<Float>> zBuffer,
            int width, int height
    ) {
        // Обновляем Z-буфер для каждого пикселя треугольника
        int minX = (int) Math.min(v1.x, Math.min(v2.x, v3.x));
        int maxX = (int) Math.max(v1.x, Math.max(v2.x, v3.x));
        int minY = (int) Math.min(v1.y, Math.min(v2.y, v3.y));
        int maxY = (int) Math.max(v1.y, Math.max(v2.y, v3.y));

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    // Вычисляем глубину пикселя
                    float depth = interpolateDepth(x, y, v1, v2, v3, v1_3d, v2_3d, v3_3d);

                    // Проверяем Z-буфер
                    if (ZBuffer.testBuffer(x, y, depth, zBuffer)) {
                        // Если пиксель видим, обновляем Z-буфер
                        zBuffer.get(x).set(y, depth);
                    }
                }
            }
        }
    }

    private static float interpolateDepth(
            int x, int y,
            Point2f v1, Point2f v2, Point2f v3,
            Vector3f v1_3d, Vector3f v2_3d, Vector3f v3_3d
    ) {
        // Интерполяция глубины пикселя
        float w1 = barycentric(x, y, v2, v3, v1);
        float w2 = barycentric(x, y, v1, v3, v2);
        float w3 = barycentric(x, y, v1, v2, v3);

        return w1 * v1_3d.getZ() + w2 * v2_3d.getZ() + w3 * v3_3d.getZ();
    }

    private static float barycentric(int x, int y, Point2f a, Point2f b, Point2f c) {
        float area = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        float s = ((x - a.x) * (c.y - a.y) - (y - a.y) * (c.x - a.x)) / area;
        return s;
    }
}
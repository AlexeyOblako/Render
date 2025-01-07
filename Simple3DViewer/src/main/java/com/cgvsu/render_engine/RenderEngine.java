package com.cgvsu.render_engine;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.model.Model;
import com.cgvsu.utils.ZBuffer;
import com.cgvsu.utils.triangles_utils.BufferedTriangleRasterization;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cgvsu.render_engine.GraphicConveyor.vertexToPoint;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final List<Model> models,
            final int width,
            final int height,
            final List<Integer> selectedVertices,
            final Color modelColor,
            final Color backgroundColor,
            final boolean isRasterizationEnabled // Новый параметр
    ) {
        graphicsContext.setStroke(modelColor);
        graphicsContext.setFill(backgroundColor);
        graphicsContext.fillRect(0, 0, width, height);

        // Инициализация Z-буфера
        ArrayList<ArrayList<Float>> zBuffer = ZBuffer.getDefaultPixelDepthMatrix(width, height);

        for (Model mesh : models) {
            Matrix4f modelMatrix = GraphicConveyor.rotateScaleTranslate(
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

                ArrayList<Vector2f> resultPoints = new ArrayList<>();
                ArrayList<Vector3f> vertices = new ArrayList<>();
                Map<Vector2f, Float> depthMap = new HashMap<>();

                for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
                    Vector4f vertexVecmath = new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1);
                    Vector2f resultPoint = vertexToPoint(
                            Matrix4f.multiply(modelViewProjectionMatrix, vertexVecmath).normalizeTo3f(),
                            width, height
                    );
                    resultPoints.add(resultPoint);
                    vertices.add(vertex);
                    depthMap.put(resultPoint, vertex.getZ()); // Сохраняем глубину для каждой вершины
                }

                // Если растеризация включена, заполняем полигоны
                if (isRasterizationEnabled && nVerticesInPolygon >= 3) {
                    for (int i = 1; i < nVerticesInPolygon - 1; i++) {
                        Vector2f v1 = resultPoints.get(0);
                        Vector2f v2 = resultPoints.get(i);
                        Vector2f v3 = resultPoints.get(i + 1);

                        // Используем метод растеризации треугольников
                        BufferedTriangleRasterization.drawTriangle(
                                graphicsContext,
                                depthMap,
                                v1, v2, v3, // Вершины треугольника
                                zBuffer,    // Z-буфер
                                modelColor  // Цвет треугольника
                        );
                    }
                }

                // Рисуем контуры полигонов
                for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    graphicsContext.strokeLine(
                            resultPoints.get(vertexInPolygonInd - 1).getX(),
                            resultPoints.get(vertexInPolygonInd - 1).getY(),
                            resultPoints.get(vertexInPolygonInd).getX(),
                            resultPoints.get(vertexInPolygonInd).getY()
                    );
                }
                if (nVerticesInPolygon > 0) {
                    graphicsContext.strokeLine(
                            resultPoints.get(nVerticesInPolygon - 1).getX(),
                            resultPoints.get(nVerticesInPolygon - 1).getY(),
                            resultPoints.get(0).getX(),
                            resultPoints.get(0).getY()
                    );
                }
            }

            if (!selectedVertices.isEmpty()) {
                highlightSelectedVertices(graphicsContext, mesh, modelViewProjectionMatrix, width, height, selectedVertices);
            }
        }
    }

    private static void highlightSelectedVertices(
            GraphicsContext gc,
            Model mesh,
            Matrix4f modelViewProjectionMatrix,
            int width,
            int height,
            List<Integer> selectedVertices
    ) {
        gc.setLineWidth(1);

        for (int vertexIndex : selectedVertices) {
            Vector3f vertex = mesh.vertices.get(vertexIndex);
            Vector4f vertexVecmath = new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1);
            Vector2f screenPoint = vertexToPoint(
                    Matrix4f.multiply(modelViewProjectionMatrix, vertexVecmath).normalizeTo3f(),
                    width, height
            );

            // Рисуем круг вокруг выбранной вершины
            gc.strokeOval(screenPoint.getX() - 5, screenPoint.getY() - 5, 10, 10);
        }
    }

    private static Color normalizeColor(Color color) {
        double red = Math.min(1.0, Math.max(0.0, color.getRed()));
        double green = Math.min(1.0, Math.max(0.0, color.getGreen()));
        double blue = Math.min(1.0, Math.max(0.0, color.getBlue()));
        return new Color(red, green, blue, 1.0);
    }
}
package com.cgvsu.render_engine;

import java.util.ArrayList;
import java.util.List;

import com.cgvsu.math.Vector4f;
import javafx.scene.canvas.GraphicsContext;
import com.cgvsu.model.Model;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.matrix.Matrix4f;
import javafx.scene.paint.Color;

import javax.vecmath.Point2f;
import static com.cgvsu.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final List<Model> models,
            final int width,
            final int height,
            final List<Integer> selectedVertices,
            final Color modelColor,
            final Color backgroundColor
    ) {
        graphicsContext.setStroke(modelColor);
        graphicsContext.setFill(backgroundColor);
        graphicsContext.fillRect(0, 0, width, height);

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

                ArrayList<Point2f> resultPoints = new ArrayList<>();
                for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                    Vector4f vertexVecmath = new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1);

                    Point2f resultPoint = GraphicConveyor.vertexToPoint(Matrix4f.multiply(modelViewProjectionMatrix, vertexVecmath).normalizeTo3f(), width, height);
                    resultPoints.add(resultPoint);
                }

                for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    graphicsContext.strokeLine(
                            resultPoints.get(vertexInPolygonInd - 1).x,
                            resultPoints.get(vertexInPolygonInd - 1).y,
                            resultPoints.get(vertexInPolygonInd).x,
                            resultPoints.get(vertexInPolygonInd).y);
                }

                if (nVerticesInPolygon > 0)
                    graphicsContext.strokeLine(
                            resultPoints.get(nVerticesInPolygon - 1).x,
                            resultPoints.get(nVerticesInPolygon - 1).y,
                            resultPoints.get(0).x,
                            resultPoints.get(0).y);
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
            Point2f screenPoint = vertexToPoint(Matrix4f.multiply(modelViewProjectionMatrix, vertexVecmath).normalizeTo3f(), width, height);

            /// круг
            gc.strokeOval(screenPoint.x - 5, screenPoint.y - 5, 10, 10);
        }
    }
}
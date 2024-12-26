package com.cgvsu.render_engine;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector4f;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.math.Vector3f;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.GraphicConveyor;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height
    ) {
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

            ArrayList<Vector2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                Vector4f vertexVecmath = new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1);

                Vector2f resultPoint = vertexToPoint(modelViewProjectionMatrix.multiply(vertexVecmath).normalizeTo3f(), width, height);
                resultPoints.add(resultPoint);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(
                        resultPoints.get(vertexInPolygonInd - 1).getX(),
                        resultPoints.get(vertexInPolygonInd - 1).getY(),
                        resultPoints.get(vertexInPolygonInd).getX(),
                        resultPoints.get(vertexInPolygonInd).getY());
            }

            if (nVerticesInPolygon > 0)
                graphicsContext.strokeLine(
                        resultPoints.get(nVerticesInPolygon - 1).getX(),
                        resultPoints.get(nVerticesInPolygon - 1).getY(),
                        resultPoints.get(0).getX(),
                        resultPoints.get(0).getY());
        }
    }

    private static Matrix4f rotateScaleTranslate(
            float scaleX, float scaleY, float scaleZ,
            float rotationX, float rotationY, float rotationZ,
            float translationX, float translationY, float translationZ
    ) {
        Matrix4f scaleMatrix = GraphicConveyor.scaleMatrix4f(scaleX, scaleY, scaleZ);
        Matrix4f rotationMatrix = GraphicConveyor.rotateMatrix4f(rotationX, rotationY, rotationZ);
        Matrix4f translationMatrix = GraphicConveyor.translationMatrix4f(translationX, translationY, translationZ);

        return Matrix4f.multiply(translationMatrix, Matrix4f.multiply(rotationMatrix, scaleMatrix));
    }

    private static Vector2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Vector2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }
}

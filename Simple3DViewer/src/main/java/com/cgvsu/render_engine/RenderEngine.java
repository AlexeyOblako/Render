package com.cgvsu.render_engine;

import java.util.ArrayList;

import com.cgvsu.math.Vector2f;
import com.cgvsu.model.Polygon;
import com.cgvsu.model.Triangle;
import com.cgvsu.utils.ZBuffer;
import com.cgvsu.utils.models_utils.ModelMeshDrawer;
import com.cgvsu.utils.models_utils.ModelRasterizer;
import com.cgvsu.utils.models_utils.Triangulation;
import com.cgvsu.utils.triangles_utils.TriangleRasterization;

import com.cgvsu.math.Vector4f;
import com.cgvsu.utils.models_utils.ModelRasterizer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import com.cgvsu.model.Model;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.matrix.Matrix4f;

import javax.vecmath.Point2f;

import static com.cgvsu.render_engine.GraphicConveyor.*;


public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height,
            final boolean isActive
    ) {

        Matrix4f modelMatrix = rotateScaleTranslate();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = Matrix4f.multiply(projectionMatrix, Matrix4f.multiply(viewMatrix, modelMatrix));

        ArrayList<ArrayList<Float>> buffer = ZBuffer.getDefaultPixelDepthMatrix(width,height);

//        ModelRasterizer.rasterizeModel(graphicsContext, mesh.triangulatedCopy, modelViewProjectionMatrix, width, height, Color.BISQUE, buffer);
//        ModelMeshDrawer.drawMesh(graphicsContext, mesh, modelViewProjectionMatrix, width, height, buffer);
        if (isActive) {
            graphicsContext.setStroke(new Color(0, 0.67, 0.71, 1));
        } else {
            graphicsContext.setStroke(Color.BLACK);
        }

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                // TODO: 25.12.2023 Убрать этот костыль
                Vector4f vertexVecmath = new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1);

                Point2f resultPoint = vertexToPoint(Matrix4f.multiply(modelViewProjectionMatrix, vertexVecmath).normalizeTo3f(), width, height);
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


    }
}
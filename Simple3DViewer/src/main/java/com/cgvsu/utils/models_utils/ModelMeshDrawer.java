package com.cgvsu.utils.models_utils;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.model.Model;
import com.cgvsu.utils.LineDrawer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.vecmath.Point2f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cgvsu.math.Vector3f.vertex3fToVector2f;

import static com.cgvsu.render_engine.GraphicConveyor.vertexToPoint;

public class ModelMeshDrawer {
    public static void drawMesh(GraphicsContext graphicsContext, Model mesh, Matrix4f modelViewProjectionMatrix, int width, int height, ArrayList<ArrayList<Float>> buffer) {
        final int nPolygons = mesh.polygons.size();
//        float[][] buffer = ZBuffer.getDefaultPixelDepthMatrix(width, height);
//        Color[][] frameBuffer = FrameBuffer.getDefaultPixelColorBuffer(width, height);
        Map<Vector2f, Float> depthMap = new HashMap<>();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Vector2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {

                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
                float depth = vertex.getZ();
                Vector4f vertexVecmath = new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1);

                Vector2f resultPoint = vertex3fToVector2f(Matrix4f.multiply(modelViewProjectionMatrix, vertexVecmath).normalizeTo3f(), width, height);
                resultPoints.add(resultPoint);
                depthMap.put(resultPoint, depth);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                LineDrawer.drawLineWithZBuffer(
                        resultPoints.get(vertexInPolygonInd - 1),

                        resultPoints.get(vertexInPolygonInd), graphicsContext, buffer,depthMap);
            }

            if (nVerticesInPolygon > 0)
                LineDrawer.drawLineWithZBuffer(
                        resultPoints.get(nVerticesInPolygon - 1),

                        resultPoints.get(0),
                         graphicsContext, buffer, depthMap);


        }
    }
}

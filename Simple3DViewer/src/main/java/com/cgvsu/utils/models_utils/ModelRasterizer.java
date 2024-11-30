package com.cgvsu.utils.models_utils;


import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.model.TriPolyModel;
import com.cgvsu.render_engine.Camera;

import com.cgvsu.utils.ZBuffer;
import com.cgvsu.utils.triangles_utils.BufferedTriangleRasterization;
import com.cgvsu.utils.triangles_utils.TriangleRasterization;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.cgvsu.math.Vector3f.vertex3fToVector2f;

import static com.cgvsu.render_engine.GraphicConveyor.rotateScaleTranslate;


public class ModelRasterizer {
    public static void rasterizeModel(GraphicsContext gc, TriPolyModel model, Matrix4f modelViewProjectionMatrix,int width, int height, Color color, ArrayList<ArrayList<Float>> buffer){

//        Color[][] frameBuffer = FrameBuffer.getDefaultPixelColorBuffer(width, height);
        Map<Vector2f, Float> depthMap = new HashMap<>();
//        if (!model.isTriangulated) model =ModelConverter.triPolyModelToModel(Triangulation.getTriangulatedModel(model));
        for (Polygon polygon: model.polygons
             ) {
            ArrayList<Vector3f> vertices = new ArrayList<>();
            ArrayList<Vector2f> points = new ArrayList<>();
            for (Integer ind: polygon.getVertexIndices()
                 ) {
                vertices.add(model.vertices.get(ind));
            }
            for (Vector3f vertex : vertices
            ) {
                float depth = vertex.getZ();
                Vector4f vertexVecmath = new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1);
                Vector2f point = vertex3fToVector2f(Matrix4f.multiply(modelViewProjectionMatrix, vertexVecmath).normalizeTo3f(), width, height);
                points.add(point);
                depthMap.put(point, depth);
            }

            BufferedTriangleRasterization.drawTriangle(gc, depthMap, points.get(0), points.get(1), points.get(2),
                    buffer, color);
//            TriangleRasterization.drawTriangle(gc, points, Color.RED);

        }
    }


}

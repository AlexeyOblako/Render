package com.cgvsu;

import com.cgvsu.math.Vector4f;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.math.Vector3f;
import com.cgvsu.render_engine.GraphicConveyor;

import java.util.ArrayList;

public class ObjWriter {

    public static String write(Model model) {
        return write(model, true);
    }

    public static String write(Model model, boolean applyTransformations) {
        StringBuilder sb = new StringBuilder();

        ArrayList<Vector3f> vertices = model.vertices;
        ArrayList<Polygon> polygons = model.polygons;

        if (applyTransformations) {
            for (Vector3f vertex : vertices) {
                Vector3f transformedVertex = applyTransformations(vertex, model.getScale(), model.getRotation(), model.getTranslation());
                sb.append("v ").append(transformedVertex.getX()).append(" ").append(transformedVertex.getY()).append(" ").append(transformedVertex.getZ()).append("\n");
            }
        } else {
            for (Vector3f vertex : vertices) {
                sb.append("v ").append(vertex.getX()).append(" ").append(vertex.getY()).append(" ").append(vertex.getZ()).append("\n");
            }
        }

        for (Polygon polygon : polygons) {
            sb.append("f");
            for (Integer vertexIndex : polygon.getVertexIndices()) {
                sb.append(" ").append(vertexIndex + 1);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private static Vector3f applyTransformations(Vector3f vertex, Vector3f scale, Vector3f rotation, Vector3f translation) {
        Matrix4f scaleMatrix = GraphicConveyor.scaleMatrix4f(scale.getX(), scale.getY(), scale.getZ());
        Vector4f scaledVertex = scaleMatrix.multiply(new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1.0f));

        Matrix4f rotationMatrix = GraphicConveyor.rotateMatrix4f(rotation.getX(), rotation.getY(), rotation.getZ());
        Vector4f rotatedVertex = rotationMatrix.multiply(scaledVertex);

        Matrix4f translationMatrix = GraphicConveyor.translationMatrix4f(translation.getX(), translation.getY(), translation.getZ());
        Vector4f transformedVertex = translationMatrix.multiply(rotatedVertex);

        return new Vector3f(transformedVertex.getX(), transformedVertex.getY(), transformedVertex.getZ());
    }
}

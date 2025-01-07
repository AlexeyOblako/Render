package com.cgvsu.objwriter;

import com.cgvsu.math.Vector4f;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.math.Vector3f;
import com.cgvsu.render_engine.GraphicConveyor;

import java.util.ArrayList;

public class ObjWriter {

    /**
     * Записывает модель в строку формата OBJ.
     *
     * @param model модель для записи
     * @return строка, представляющая модель в формате OBJ
     */
    public static String write(Model model) {
        return write(model, true);
    }

    /**
     * Записывает модель в строку формата OBJ с возможностью применения трансформаций.
     *
     * @param model              модель для записи
     * @param applyTransformations флаг, указывающий, применять ли трансформации
     * @return строка, представляющая модель в формате OBJ
     */
    public static String write(Model model, boolean applyTransformations) {
        if (model == null) {
            throw ObjWriterException.modelNull();
        }

        StringBuilder sb = new StringBuilder();

        ArrayList<Vector3f> vertices = model.vertices;
        ArrayList<Polygon> polygons = model.polygons;

        if (vertices == null || vertices.isEmpty()) {
            throw ObjWriterException.noVerticesFound(-1);
        }

        if (polygons == null || polygons.isEmpty()) {
            throw ObjWriterException.noPolygonsFound(-1);
        }

        if (applyTransformations) {
            // Применить трансформации к вершинам
            for (Vector3f vertex : vertices) {
                try {
                    Vector3f transformedVertex = applyTransformations(vertex, model.getScale(), model.getRotation(), model.getTranslation());
                    sb.append("v ").append(transformedVertex.getX()).append(" ")
                            .append(transformedVertex.getY()).append(" ")
                            .append(transformedVertex.getZ()).append("\n");
                } catch (Exception e) {
                    throw ObjWriterException.invalidTransformation("general", -1);
                }
            }
        } else {
            // Сохранить оригинальные вершины
            for (Vector3f vertex : vertices) {
                sb.append("v ").append(vertex.getX()).append(" ")
                        .append(vertex.getY()).append(" ")
                        .append(vertex.getZ()).append("\n");
            }
        }

        // Итерация по полигонам и добавление определений граней
        for (Polygon polygon : polygons) {
            sb.append("f");
            try {
                for (Integer vertexIndex : polygon.getVertexIndices()) {
                    if (vertexIndex < 0 || vertexIndex >= vertices.size()) {
                        throw ObjWriterException.indexOutOfBounds("Vertex", vertexIndex, vertices.size(), -1);
                    }
                    sb.append(" ").append(vertexIndex + 1);
                }
            } catch (ObjWriterException e) {
                throw e;  // Повторное выбрасывание пользовательского исключения
            } catch (Exception e) {
                throw ObjWriterException.invalidElementSize(-1);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Применяет трансформации к вершине.
     *
     * @param vertex    вершина для трансформации
     * @param scale     вектор масштаба
     * @param rotation  вектор вращения
     * @param translation вектор трансляции
     * @return трансформированная вершина
     */
    private static Vector3f applyTransformations(Vector3f vertex, Vector3f scale, Vector3f rotation, Vector3f translation) {
        if (vertex == null) {
            throw ObjWriterException.vertexNull(-1);
        }

        // Масштаб
        Matrix4f scaleMatrix = GraphicConveyor.scaleMatrix4f(scale.getX(), scale.getY(), scale.getZ());
        Vector4f scaledVertex = scaleMatrix.multiply(new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1.0f));

        // Вращениe
        Matrix4f rotationMatrix = GraphicConveyor.rotateMatrix4f(rotation.getX(), rotation.getY(), rotation.getZ());
        Vector4f rotatedVertex = rotationMatrix.multiply(scaledVertex);

        // Трансляция
        Matrix4f translationMatrix = GraphicConveyor.translationMatrix4f(translation.getX(), translation.getY(), translation.getZ());
        Vector4f transformedVertex = translationMatrix.multiply(rotatedVertex);

        // Обратно в Vector3f
        return new Vector3f(transformedVertex.getX(), transformedVertex.getY(), transformedVertex.getZ());
    }
}

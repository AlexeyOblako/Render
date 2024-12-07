package com.cgvsu.model;

import com.cgvsu.math.Vector4f;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import java.util.ArrayList;

public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Vector3f> originalVertices = new ArrayList<>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<>();
    public ArrayList<Vector3f> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();

    public void applyTransform(Matrix4f transform) {
        // Преобразование вершин
        ArrayList<Vector3f> transformedVertices = new ArrayList<>();
        for (Vector3f vertex : vertices) {
            // Добавляем четвёртую координату (w = 1) для преобразования
            Vector4f vertex4f = new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1.0f);
            Vector4f transformedVertex4f = transform.multiply(vertex4f);
            transformedVertices.add(new Vector3f(
                    transformedVertex4f.getX(),
                    transformedVertex4f.getY(),
                    transformedVertex4f.getZ()
            ));
        }
        vertices = transformedVertices;

        // Преобразование нормалей
        Matrix4f normalTransform = transform.inverse().transpose(); // Матрица нормалей
        for (int i = 0; i < normals.size(); i++) {
            Vector3f normal = normals.get(i);
            // Добавляем четвёртую координату (w = 0) для преобразования направления
            Vector4f normal4f = new Vector4f(normal.getX(), normal.getY(), normal.getZ(), 0.0f);
            Vector4f transformedNormal4f = normalTransform.multiply(normal4f);
            normals.set(i, new Vector3f(
                    transformedNormal4f.getX(),
                    transformedNormal4f.getY(),
                    transformedNormal4f.getZ()
            ).normalize()); // Нормализуем нормаль после преобразования
        }
    }


    /**
     * Устанавливает оригинальные вершины.
     */
    public void saveOriginalVertices() {
        originalVertices = new ArrayList<>(vertices);
    }

    /**
     * Экспортирует модель в формат .obj.
     *
     * @param useOriginal Учитывать ли оригинальные вершины.
     * @return Строка в формате .obj.
     */
    public String toObjFormat(boolean useOriginal) {
        StringBuilder objData = new StringBuilder();

        ArrayList<Vector3f> verticesToExport = useOriginal ? originalVertices : vertices;

        for (Vector3f vertex : verticesToExport) {
            objData.append(String.format("v %f %f %f%n", vertex.getX(), vertex.getY(), vertex.getZ()));
        }

        for (Vector2f textureVertex : textureVertices) {
            objData.append(String.format("vt %f %f%n", textureVertex.getX(), textureVertex.getY()));
        }

        for (Vector3f normal : normals) {
            objData.append(String.format("vn %f %f %f%n", normal.getX(), normal.getY(), normal.getZ()));
        }

        for (Polygon polygon : polygons) {
            objData.append("f");
            for (int i = 0; i < polygon.getVertexIndices().size(); i++) {
                int vertexIndex = polygon.getVertexIndices().get(i) + 1;
                String faceData = String.valueOf(vertexIndex);

                if (!polygon.getTextureVertexIndices().isEmpty()) {
                    int textureIndex = polygon.getTextureVertexIndices().get(i) + 1;
                    faceData += "/" + textureIndex;
                } else {
                    faceData += "/";
                }

                if (!polygon.getNormalIndices().isEmpty()) {
                    int normalIndex = polygon.getNormalIndices().get(i) + 1;
                    faceData += "/" + normalIndex;
                }

                objData.append(" ").append(faceData);
            }
            objData.append(System.lineSeparator());
        }

        return objData.toString();
    }
}

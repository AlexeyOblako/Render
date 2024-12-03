package com.cgvsu.model;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import java.util.*;

public class Model {

    // Массивы для хранения вершин, текстурных координат, нормалей и полигонов
    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    // Метод для вычисления нормалей модели
    public void calculateNormals() {
        // Инициализируем нормали для каждой вершины
        for (int i = 0; i < vertices.size(); i++) {
            normals.add(new Vector3f(0, 0, 0)); // Нормали начинаются с нулевых векторов
        }

        // Для каждой грани модели вычисляем нормаль
        for (Polygon polygon : polygons) {
            Vector3f normal = calculatePolygonNormal(polygon);

            // Добавляем нормаль к каждой вершине в полигоне
            for (int vertexIndex : polygon.getVertexIndices()) {
                Vector3f vertexNormal = normals.get(vertexIndex);
                vertexNormal.add(normal);
                normals.set(vertexIndex, vertexNormal);
            }
        }

        // Нормализуем нормали для каждой вершины
        for (int i = 0; i < normals.size(); i++) {
            normals.get(i).normalize();
        }
    }

    // Метод для вычисления нормали полигона (треугольника)
    private Vector3f calculatePolygonNormal(Polygon polygon) {
        // Получаем вершины полигона
        Vector3f v0 = vertices.get(polygon.getVertexIndices().get(0));
        Vector3f v1 = vertices.get(polygon.getVertexIndices().get(1));
        Vector3f v2 = vertices.get(polygon.getVertexIndices().get(2));

        // Вычисление векторов граней
        Vector3f edge1 = new Vector3f(v1.x - v0.x, v1.y - v0.y, v1.z - v0.z);
        Vector3f edge2 = new Vector3f(v2.x - v0.x, v2.y - v0.y, v2.z - v0.z);

        // Векторное произведение для нахождения нормали
        Vector3f normal = new Vector3f(
                edge1.y * edge2.z - edge1.z * edge2.y,
                edge1.z * edge2.x - edge1.x * edge2.z,
                edge1.x * edge2.y - edge1.y * edge2.x
        );

        // Нормализуем нормаль
        normal.normalize();

        return normal;
    }

}

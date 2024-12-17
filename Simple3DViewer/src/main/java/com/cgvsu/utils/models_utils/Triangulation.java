package com.cgvsu.utils.models_utils;

import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.math.Vector3f;

import java.util.*;

public class Triangulation {

    public static Model getTriangulatedModel(Model model) {
        assert !model.polygons.isEmpty() : "Empty model";

        // Создаем новую модель и копируем параметры преобразования
        Model triangulatedModel = new Model();
        triangulatedModel.textureVertices = model.textureVertices;
        triangulatedModel.vertices = model.vertices;
        triangulatedModel.normals = model.normals;
        triangulatedModel.setScale(model.getScale()); // Копируем масштаб
        triangulatedModel.setRotation(model.getRotation()); // Копируем поворот
        triangulatedModel.setTranslation(model.getTranslation()); // Копируем перемещение

        // Триангуляция полигонов
        HashMap<Polygon, ArrayList<Vector3f>> polyVertMap = getPolyVertMap(model);
        for (Map.Entry<Polygon, ArrayList<Vector3f>> entry : polyVertMap.entrySet()) {
            ArrayList<Vector3f> vertices = entry.getValue();
            List<List<Vector3f>> triangulatedPolygons = triangulate(vertices);

            for (List<Vector3f> triangle : triangulatedPolygons) {
                assert !triangle.isEmpty() : "Empty polygon";
                Polygon triangulatedPolygon = new Polygon();
                ArrayList<Integer> polyVert = new ArrayList<>();
                polyVert.add(model.vertices.indexOf(triangle.get(0)));
                polyVert.add(model.vertices.indexOf(triangle.get(1)));
                polyVert.add(model.vertices.indexOf(triangle.get(2)));
                triangulatedPolygon.setVertexIndices(polyVert);
                triangulatedPolygon.setNormalIndices(entry.getKey().getNormalIndices());
                triangulatedPolygon.setTextureVertexIndices(entry.getKey().getTextureVertexIndices());
                triangulatedModel.polygons.add(triangulatedPolygon);
            }
        }

        return triangulatedModel;
    }

    public static List<List<Vector3f>> triangulate(List<Vector3f> vertList) {
        List<List<Vector3f>> triangulatedPolygons = new ArrayList<>();
        if (vertList.size() < 3) {
            triangulatedPolygons.add(vertList);
            return triangulatedPolygons;
        }
        for (int i = 1; i < vertList.size() - 1; i++) {
            List<Vector3f> triangle = new ArrayList<>();
            triangle.add(vertList.get(0));
            triangle.add(vertList.get(i));
            triangle.add(vertList.get(i + 1));
            triangulatedPolygons.add(triangle);
        }
        return triangulatedPolygons;
    }

    private static HashMap<Polygon, ArrayList<Vector3f>> getPolyVertMap(Model model) {
        HashMap<Polygon, ArrayList<Vector3f>> polyVertMap = new HashMap<>();
        for (Polygon poly : model.polygons) {
            ArrayList<Vector3f> polyVertices = new ArrayList<>();
            for (Integer ind : poly.getVertexIndices()) {
                polyVertices.add(model.vertices.get(ind));
            }
            polyVertMap.put(poly, polyVertices);
        }
        return polyVertMap;
    }
}
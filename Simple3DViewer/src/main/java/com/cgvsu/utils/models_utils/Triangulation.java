package com.cgvsu.utils.models_utils;

import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.model.TriPolyModel;

import com.cgvsu.model.Triangle;

import com.cgvsu.math.Vector3f;


import java.util.*;

public class Triangulation {


    public static TriPolyModel getTriangulatedModel(Model model) {
        assert !model.polygons.isEmpty() : "Empty model";
        TriPolyModel triangulatedPolygonsModel = new TriPolyModel();
        triangulatedPolygonsModel.textureVertices = model.textureVertices;
        triangulatedPolygonsModel.vertices = model.vertices;
        triangulatedPolygonsModel.normals = model.normals;
        // triangulatedPolygonsModel.setName(model.getName());
        HashMap<Polygon, ArrayList<Vector3f>> polyVertMap = new HashMap<>(getPolyVertMap(model));
        for (Map.Entry<Polygon, ArrayList<Vector3f>> entry : polyVertMap.entrySet()
        ) {

            ArrayList<Vector3f> vertices = entry.getValue();
            List<List<Vector3f>> triangulatedPolygons;
            triangulatedPolygons = triangulate(vertices);

            for (List<Vector3f> triangle : triangulatedPolygons
            ) {
                assert !triangle.isEmpty() : "Empty polygon";
                Triangle triangulatedPolygon = new Triangle();
                ArrayList<Integer> polyVert = new ArrayList<>();
                polyVert.add(model.vertices.indexOf(triangle.get(0)));
                polyVert.add(model.vertices.indexOf(triangle.get(1)));
                polyVert.add(model.vertices.indexOf(triangle.get(2)));
                triangulatedPolygon.setVertexIndices(polyVert);
                triangulatedPolygon.setNormalIndices(entry.getKey().getNormalIndices());
                triangulatedPolygon.setTextureVertexIndices(entry.getKey().getTextureVertexIndices());
                triangulatedPolygonsModel.polygons.add(triangulatedPolygon);
            }


        }
        return triangulatedPolygonsModel;
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
        for (Polygon poly : model.polygons
        ) {
            ArrayList<Vector3f> polyVertices = new ArrayList<>();
            for (Integer ind : poly.getVertexIndices()
            ) {
                polyVertices.add(model.vertices.get(ind));
            }
            polyVertMap.put(poly, polyVertices);
        }
        return polyVertMap;
    }
}

package com.cgvsu.utils.models_utils;

import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.model.TriPolyModel;
import com.cgvsu.model.Triangle;

import java.util.ArrayList;

public class ModelConverter {
    public static Model triPolyModelToModel(TriPolyModel triPolyModel) {
        Model model = new Model();
        ArrayList<Polygon> triPolygons = new ArrayList<>();
        model.vertices = triPolyModel.vertices;
        model.normals = triPolyModel.normals;
        model.textureVertices = triPolyModel.textureVertices;
        model.setName(triPolyModel.getName());
        for (Triangle triangle : triPolyModel.polygons
        ) {
            model.polygons.add(triangleToPolygon(triangle));
        }
        return model;
    }


    public static Polygon triangleToPolygon(Triangle triangle) {
        Polygon polygon = new Polygon();
        polygon.setVertexIndices(triangle.getVertexIndices());
        polygon.setNormalIndices(triangle.getNormalIndices());
        polygon.setTextureVertexIndices(triangle.getTextureVertexIndices());
        return polygon;
    }
}

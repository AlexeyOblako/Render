package com.cgvsu.utils;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;

public class NormalUtils {

    private static Vector3f normalPolygon(Polygon polygon, ArrayList<Vector3f> vertices) {
        ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
        try {
            return Vector3f.crossProduct(
                    Vector3f.deduct(vertices.get(vertexIndices.get(0)), vertices.get(vertexIndices.get(1))),
                    Vector3f.deduct(vertices.get(vertexIndices.get(0)), vertices.get(vertexIndices.get(2))));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Polygon vertices amount < 3");
        }
    }

    public static ArrayList<Vector3f> normalsVertex(ArrayList<Vector3f> vertices, ArrayList<Polygon> polygons) {
        ArrayList<Vector3f> normalsVertex = new ArrayList<Vector3f>();
        ArrayList<Vector3f> normalsPolygon = new ArrayList<Vector3f>();

        Integer[] count = new Integer[vertices.size()];
        Vector3f[] normalSummaVertex = new Vector3f[vertices.size()];

        for (int indexPoligon = 0; indexPoligon < polygons.size(); indexPoligon++) {
            normalsPolygon.add(indexPoligon, normalPolygon(polygons.get(indexPoligon), vertices));
            ArrayList<Integer> vertexIndices = polygons.get(indexPoligon).getVertexIndices();

            polygons.get(indexPoligon).setNormalIndices(vertexIndices);

            for (Integer vertexIndex : vertexIndices) {
                if (normalSummaVertex[vertexIndex] == null) {
                    normalSummaVertex[vertexIndex] = normalsPolygon.get(indexPoligon);
                    count[vertexIndex] = 1;
                } else {
                    normalSummaVertex[vertexIndex] = Vector3f.add(normalSummaVertex[vertexIndex], normalsPolygon.get(indexPoligon));
                    count[vertexIndex]++;
                }
            }
        }
        for (int i = 0; i < count.length; i++) {
            normalsVertex.add(i, normalSummaVertex[i].normalize());
        }
        return normalsVertex;
    }

}

package com.cgvsu.model;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import java.util.*;

public class Model {
    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    // параметры для трансформации
    private Vector3f scale = new Vector3f(1, 1, 1);
    private Vector3f rotation = new Vector3f(0, 0, 0);
    private Vector3f translation = new Vector3f(0, 0, 0);
    private String name = "Unnamed Model";

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public String getName() {
        return this.name;
    }

    public void resetTransformations() {
        setScale(new Vector3f(1, 1, 1));
        setRotation(new Vector3f(0, 0, 0));
        setTranslation(new Vector3f(0, 0, 0));
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Vector3f> getVertices() {
        return vertices;
    }

    public Collection<Vector2f> getTextureVertices() {
        return textureVertices;
    }

    public Collection<Vector3f> getNormals() {
        return normals;
    }

    public Collection<Polygon> getPolygons() {
        return polygons;
    }

    public void addVertex(Vector3f vertex) {
        this.vertices.add(vertex);
    }

    public void addTextureVertex(Vector2f textureVertex) {
        this.textureVertices.add(textureVertex);
    }

    public void addNormal(Vector3f normal) {
        this.normals.add(normal);
    }

    public void addPolygon(Polygon polygon) {
        this.polygons.add(polygon);
    }

    public void removeVertices(List<Integer> vertexIndices) {
        for (int i = vertexIndices.size() - 1; i >= 0; i--) {
            int vertexIndex = vertexIndices.get(i);
            removeVertexAndUpdatePolygons(vertexIndex);
        }
    }
    public void removeVertexAndUpdatePolygons(int vertexIndexToRemove) {
        if (vertexIndexToRemove < 0 || vertexIndexToRemove >= vertices.size()) {
            throw new IllegalArgumentException("Invalid vertex index to remove");
        }
        vertices.remove(vertexIndexToRemove);
        updatePolygonIndicesAfterVertexRemoval(vertexIndexToRemove);
    }

    private void updatePolygonIndicesAfterVertexRemoval(int removedVertexIndex) {
        // Перебор полигонов
        for (Polygon polygon : polygons) {
            List<Integer> updatedVertexIndices = new ArrayList<>();
            for (int vertexIndex : polygon.getVertexIndices()) {
                if (vertexIndex < removedVertexIndex) {
                    updatedVertexIndices.add(vertexIndex);
                } else if (vertexIndex > removedVertexIndex) {
                    updatedVertexIndices.add(vertexIndex - 1);
                }
            }

            polygon.setVertexIndices((ArrayList<Integer>) updatedVertexIndices);
        }

        polygons.removeIf(polygon -> polygon.getVertexIndices().size() < 3);
    }
}

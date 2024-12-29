package com.cgvsu.objwriter;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ObjWriterTest {

    @Test
    public void testWriteModelNull() {
        try {
            ObjWriter.write(null);
            Assertions.fail("Expected ObjWriterException.modelNull() to be thrown");
        } catch (ObjWriterException e) {
            Assertions.assertEquals("The model is null.", e.getMessage());
        }
    }

    @Test
    public void testWriteNoVertices() {
        Model model = new Model();
        model.vertices = new ArrayList<>(); // No vertices
        model.polygons = new ArrayList<>();

        try {
            ObjWriter.write(model);
            Assertions.fail("Expected ObjWriterException.noVerticesFound() to be thrown");
        } catch (ObjWriterException e) {
            Assertions.assertEquals("Error parsing OBJ file on line: -1. OBJ file has no vertices.", e.getMessage());
        }
    }

    @Test
    public void testWriteNoPolygons() {
        Model model = new Model();
        model.vertices = new ArrayList<>();
        model.vertices.add(new Vector3f(1.0f, 1.0f, 1.0f));
        model.polygons = new ArrayList<>(); // No polygons

        try {
            ObjWriter.write(model);
            Assertions.fail("Expected ObjWriterException.noPolygonsFound() to be thrown");
        } catch (ObjWriterException e) {
            Assertions.assertEquals("Error parsing OBJ file on line: -1. OBJ file has no polygons.", e.getMessage());
        }
    }
}

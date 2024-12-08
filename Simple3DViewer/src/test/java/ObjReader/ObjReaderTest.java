package ObjReader;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.objreader.ObjReaderException;
import com.cgvsu.objreader.ObjReader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ObjReaderTest {

    @Test
    public void testParseVertex() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0"));
        Vector3f vertex = ObjReader.parseVertex(wordsInLine, 1);
        assertEquals(1.0f, vertex.getX(), 1e-6);
        assertEquals(2.0f, vertex.getY(), 1e-6);
        assertEquals(3.0f, vertex.getZ(), 1e-6);
    }

    @Test
    public void testParseVertexInvalidFormat() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("1.0", "invalid", "3.0"));
        assertThrows(ObjReaderException.class, () -> ObjReader.parseVertex(wordsInLine, 1));
    }

    @Test
    public void testParseVertexTooFewArguments() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        assertThrows(ObjReaderException.class, () -> ObjReader.parseVertex(wordsInLine, 1));
    }

    @Test
    public void testParseTextureVertex() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("0.5", "0.75"));
        Vector2f textureVertex = ObjReader.parseTextureVertex(wordsInLine, 1);
        assertEquals(0.5f, textureVertex.getX(), 1e-6);
        assertEquals(0.75f, textureVertex.getY(), 1e-6);
    }

    @Test
    public void testParseTextureVertexInvalidFormat() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("0.5", "invalid"));
        assertThrows(ObjReaderException.class, () -> ObjReader.parseTextureVertex(wordsInLine, 1));
    }

    @Test
    public void testParseTextureVertexTooFewArguments() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("0.5"));
        assertThrows(ObjReaderException.class, () -> ObjReader.parseTextureVertex(wordsInLine, 1));
    }

    @Test
    public void testParseNormal() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("0.0", "1.0", "0.0"));
        Vector3f normal = ObjReader.parseNormal(wordsInLine, 1);
        assertEquals(0.0f, normal.getX(), 1e-6);
        assertEquals(1.0f, normal.getY(), 1e-6);
        assertEquals(0.0f, normal.getZ(), 1e-6);
    }

    @Test
    public void testParseNormalInvalidFormat() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("0.0", "invalid", "0.0"));
        assertThrows(ObjReaderException.class, () -> ObjReader.parseNormal(wordsInLine, 1));
    }

    @Test
    public void testParseNormalTooFewArguments() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("0.0", "1.0"));
        assertThrows(ObjReaderException.class, () -> ObjReader.parseNormal(wordsInLine, 1));
    }

    @Test
    public void testParseFace() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("1/1/1", "2/2/2", "3/3/3"));
        Polygon polygon = ObjReader.parseFace(wordsInLine, 1);
        assertEquals(Arrays.asList(0, 1, 2), polygon.getVertexIndices());
        assertEquals(Arrays.asList(0, 1, 2), polygon.getTextureVertexIndices());
        assertEquals(Arrays.asList(0, 1, 2), polygon.getNormalIndices());
    }

    @Test
    public void testParseFaceInvalidFormat() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("1/invalid/1", "2/2/2", "3/3/3"));
        assertThrows(ObjReaderException.class, () -> ObjReader.parseFace(wordsInLine, 1));
    }

    @Test
    public void testParseFaceTooFewArguments() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("1/1/1", "2/2"));
        assertThrows(ObjReaderException.class, () -> ObjReader.parseFace(wordsInLine, 1));
    }


    @Test
    public void testRead() {
        String fileContent = "v 1.0 2.0 3.0\n" +
                "vt 0.5 0.75\n" +
                "vn 0.0 1.0 0.0\n" +
                "f 1/1/1 2/2/2 3/3/3\n";
        Model model = ObjReader.read(fileContent);

        assertEquals(1, model.vertices.size());
        assertEquals(1.0f, model.vertices.get(0).getX(), 1e-6);
        assertEquals(2.0f, model.vertices.get(0).getY(), 1e-6);
        assertEquals(3.0f, model.vertices.get(0).getZ(), 1e-6);

        assertEquals(1, model.textureVertices.size());
        assertEquals(0.5f, model.textureVertices.get(0).getX(), 1e-6);
        assertEquals(0.75f, model.textureVertices.get(0).getY(), 1e-6);

        assertEquals(1, model.normals.size());
        assertEquals(0.0f, model.normals.get(0).getX(), 1e-6);
        assertEquals(1.0f, model.normals.get(0).getY(), 1e-6);
        assertEquals(0.0f, model.normals.get(0).getZ(), 1e-6);

        assertEquals(1, model.polygons.size());
        Polygon polygon = model.polygons.get(0);
        assertEquals(Arrays.asList(0, 1, 2), polygon.getVertexIndices());
        assertEquals(Arrays.asList(0, 1, 2), polygon.getTextureVertexIndices());
        assertEquals(Arrays.asList(0, 1, 2), polygon.getNormalIndices());
    }
}

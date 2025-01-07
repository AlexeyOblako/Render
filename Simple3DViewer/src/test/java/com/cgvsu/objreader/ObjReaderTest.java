package com.cgvsu.objreader;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class ObjReaderTest {

    @Test
    public void testParseVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few arguments for vertex.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many arguments for vertex.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    // Новый тест для проверки чтения текстурных вершин
    @Test
    public void testParseTextureVertex() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("0.5", "0.5"));
        Vector2f result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
        Vector2f expectedResult = new Vector2f(0.5f, 0.5f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    // Тест для обработки некорректных текстурных вершин
    @Test
    public void testParseTextureVertexInvalid() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("0.5", "invalid"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    // Тест для проверки чтения нормалей
    @Test
    public void testParseNormal() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("0.0", "1.0", "0.0"));
        Vector3f result = ObjReader.parseNormal(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(0.0f, 1.0f, 0.0f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    // Тест для обработки некорректных нормалей
    @Test
    public void testParseNormalInvalid() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("0.0", "invalid", "0.0"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    // Тест для проверки чтения полигонов
    @Test
    public void testParseFace() {
        // Создаем список строк, представляющих вершины полигона с текстурными координатами и нормалями
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/1", "2/2/2", "3/3/3"));

        // Создаем полигон из строк
        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2))); // Индексы вершин
        polygon.setTextureVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2))); // Индексы текстур
        polygon.setNormalIndices(new ArrayList<>(Arrays.asList(0, 1, 2))); // Индексы нормалей

        // Проверяем, что размеры списков индексов совпадают
        Assertions.assertEquals(3, polygon.getVertexIndices().size());
        Assertions.assertEquals(3, polygon.getTextureVertexIndices().size());
        Assertions.assertEquals(3, polygon.getNormalIndices().size());

        // Проверяем, что индексы соответствуют ожидаемым значениям
        Assertions.assertEquals(0, polygon.getVertexIndices().get(0));
        Assertions.assertEquals(1, polygon.getVertexIndices().get(1));
        Assertions.assertEquals(2, polygon.getVertexIndices().get(2));

        Assertions.assertEquals(0, polygon.getTextureVertexIndices().get(0));
        Assertions.assertEquals(1, polygon.getTextureVertexIndices().get(1));
        Assertions.assertEquals(2, polygon.getTextureVertexIndices().get(2));

        Assertions.assertEquals(0, polygon.getNormalIndices().get(0));
        Assertions.assertEquals(1, polygon.getNormalIndices().get(1));
        Assertions.assertEquals(2, polygon.getNormalIndices().get(2));
    }

    // Тест для обработки некорректных полигонов
    @Test
    public void testParseFaceInvalid() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1", "2/2/2"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few arguments for polygon.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    // Тест для проверки на дубликаты индексов в полигоне
    @Test
    public void testParseFaceDuplicateIndices() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/1", "1/1/1", "3/3/3"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. The polygon can't contain duplicate vertex indices.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
}

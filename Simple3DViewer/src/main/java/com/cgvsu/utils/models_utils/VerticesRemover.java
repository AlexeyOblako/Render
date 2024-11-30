package com.cgvsu.utils.models_utils;

import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VerticesRemover {
    public static void deleteVertexes(Model model, Integer[] vertices) throws IndexOutOfBoundsException {
        deleteVertexes(model, Arrays.asList(vertices));
    }

    public static void deleteVertexes(Model model, List<Integer> vertices) throws IndexOutOfBoundsException {
        Collections.sort(vertices);
        int offset = 0;

        // Иду по всем вершинам
        for (Integer index : vertices) {
            if (index - offset <= 0) {
                throw new IndexOutOfBoundsException("Index must be greater than 0");
            }
            // Сравниваю индекс - оффсет, оффсет отвечает за то, сколько мы вершин удалили до этого
            // Раз мы удалили вершину, то текущая вершина будет на 1 меньше по индексу, для этого нужен оффсет
            // Если индекс превышает имеющиеся, кидаю исключение
            if (index - offset > model.vertices.size()) {
                throw new IndexOutOfBoundsException("Index is greater than the number of vertices");
            }

            // Удаляю вершину
            deleteVertex(model, index - offset);
            indexOffset(model, index - offset);
            offset++;
        }
    }

    private static void deleteVertex(Model model, int vertexIndex) {
        deletePolygons(model, vertexIndex);
        model.vertices.remove(vertexIndex - 1);
    }


    private static void deletePolygons(Model model, int vertexIndex) {
        List<Integer> deleteList = new ArrayList<>();
        int c = 0;
        for (Polygon polygon : model.polygons) {
            for (Integer i : polygon.getVertexIndices()) {
                if (i == vertexIndex) {
                    deleteList.add(c);
                    break;
                }
            }
            c++;
        }

        Collections.sort(deleteList);
        int offSet = 0;
        for (Integer d : deleteList) {
            model.polygons.remove(d - offSet);
            offSet++;
        }
    }

    private static void indexOffset(Model model, int index) {
        for (Polygon polygon : model.polygons) {
            polygon.offset(index);
        }
    }
}

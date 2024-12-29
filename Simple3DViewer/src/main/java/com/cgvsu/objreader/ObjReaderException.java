package com.cgvsu.objreader;

public class ObjReaderException extends RuntimeException {

    // Конструктор с номером строки
    public ObjReaderException(String message, int lineInd) {
        super("Error parsing OBJ file on line: " + lineInd + ". " + message);
    }

    // Исключение: Слишком мало аргументов для элемента
    public static ObjReaderException tooFewArguments(String element, int lineInd) {
        return new ObjReaderException("Too few arguments for " + element + ".", lineInd);
    }

    // Исключение: Слишком много аргументов для элемента
    public static ObjReaderException tooManyArguments(String element, int lineInd) {
        return new ObjReaderException("Too many arguments for " + element + ".", lineInd);
    }

    // Исключение: Не удалось разобрать значение
    public static ObjReaderException parseFailed(String type, int lineInd) {
        return new ObjReaderException("Failed to parse " + type + " value.", lineInd);
    }

    // Исключение: Индекс выходит за пределы
    public static ObjReaderException indexOutOfBounds(String type, int index, int size, int lineInd) {
        return new ObjReaderException(
                type + " index " + index + " out of bounds. Valid range: 0 to " + (size - 1) + ".", lineInd);
    }

    // Исключение: Дублирующийся индекс вершины в грани полигона
    public static ObjReaderException duplicateVertexIndex(int lineInd) {
        return new ObjReaderException("The polygon can't contain duplicate vertex indices.", lineInd);
    }

    // Исключение: Неверный размер элемента
    public static ObjReaderException invalidElementSize(int lineInd) {
        return new ObjReaderException("Invalid element size in face definition.", lineInd);
    }

    // Исключение: Полигонов не найдено в файле
    public static ObjReaderException noPolygonsFound(int lineInd) {
        return new ObjReaderException("OBJ file has no polygons.", lineInd);
    }

    // Исключение: Вершин не найдено в файле
    public static ObjReaderException noVerticesFound(int lineInd) {
        return new ObjReaderException("OBJ file has no vertices.", lineInd);
    }

    // Исключение: Несоответствие текстур в полигонах
    public static ObjReaderException textureInconsistency(int lineInd) {
        return new ObjReaderException("Texture vertices are inconsistent in polygons.", lineInd);
    }

    // Исключение: Индекс выходит за пределы без номера строки
    public static ObjReaderException indexOutOfBounds() {
        return new ObjReaderException("Index out of bounds during parsing.", -1);
    }
}


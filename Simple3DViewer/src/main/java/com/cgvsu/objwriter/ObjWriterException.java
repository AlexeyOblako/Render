package com.cgvsu.objwriter;

public class ObjWriterException extends RuntimeException {

    // Общий конструктор
    public ObjWriterException(String message) {
        super(message);
    }

    // Конструктор с номером строки
    public ObjWriterException(String message, int lineInd) {
        super("Error parsing OBJ file on line: " + lineInd + ". " + message);
    }

    // Исключения для различных условий:

    // Случай: Модель равна null
    public static ObjWriterException modelNull() {
        return new ObjWriterException("The model is null.");
    }

    // Случай: Слишком мало аргументов для элемента (например, вершины или грани)
    public static ObjWriterException tooFewArguments(String element, int lineInd) {
        return new ObjWriterException("Too few arguments for " + element + ".", lineInd);
    }

    // Случай: Слишком много аргументов для элемента (например, вершины или грани)
    public static ObjWriterException tooManyArguments(String element, int lineInd) {
        return new ObjWriterException("Too many arguments for " + element + ".", lineInd);
    }

    // Случай: Не удалось разобрать значение (например, позицию вершины или данные о грани)
    public static ObjWriterException parseFailed(String type, int lineInd) {
        return new ObjWriterException("Failed to parse " + type + " value.", lineInd);
    }

    // Случай: Индекс выходит за пределы (для индексов вершин или полигонов)
    public static ObjWriterException indexOutOfBounds(String type, int index, int size, int lineInd) {
        return new ObjWriterException(
                type + " index " + index + " out of bounds. Valid range: 0 to " + (size - 1) + ".", lineInd);
    }

    // Случай: Дублирующийся индекс вершины в грани полигона
    public static ObjWriterException duplicateVertexIndex(int lineInd) {
        return new ObjWriterException("The polygon can't contain duplicate vertex indices.", lineInd);
    }

    // Случай: Неверный размер элемента (например, определение грани с недопустимыми вершинами)
    public static ObjWriterException invalidElementSize(int lineInd) {
        return new ObjWriterException("Invalid element size in face definition.", lineInd);
    }

    // Случай: Полигонов не найдено в модели
    public static ObjWriterException noPolygonsFound(int lineInd) {
        return new ObjWriterException("OBJ file has no polygons.", lineInd);
    }

    // Случай: Вершин не найдено в модели
    public static ObjWriterException noVerticesFound(int lineInd) {
        return new ObjWriterException("OBJ file has no vertices.", lineInd);
    }

    // Случай: Найдена несоответствие текстур в полигонах (если существует текстурная карта)
    public static ObjWriterException textureInconsistency(int lineInd) {
        return new ObjWriterException("Texture vertices are inconsistent in polygons.", lineInd);
    }

    // Случай: Индекс выходит за пределы без номера строки
    public static ObjWriterException indexOutOfBounds() {
        return new ObjWriterException("Index out of bounds during parsing.");
    }

    // Случай: Общая ошибка, когда вершина равна null (при трансформациях или разборе)
    public static ObjWriterException vertexNull(int lineInd) {
        return new ObjWriterException("Vertex is null during transformation.", lineInd);
    }

    // Случай: Неверная трансформация (при применении масштабирования, вращения или трансляции)
    public static ObjWriterException invalidTransformation(String transformationType, int lineInd) {
        return new ObjWriterException("Invalid " + transformationType + " transformation.", lineInd);
    }

    // Случай: Параметры трансформации равны null или недействительны
    public static ObjWriterException invalidTransformationParameters(int lineInd) {
        return new ObjWriterException("Transformation parameters are invalid or null.", lineInd);
    }
}


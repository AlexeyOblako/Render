package com.cgvsu;

import com.cgvsu.model.Model;

import java.io.FileWriter;
import java.io.IOException;

public class ObjWriter {

    /**
     * Сохраняет модель в файл в формате .obj.
     *
     * @param model       Модель для сохранения.
     * @param filePath    Путь к файлу для сохранения.
     * @param useOriginal Учитывать ли оригинальные данные.
     * @throws IOException Ошибка записи файла.
     */
    public static void writeModelToFile(Model model, String filePath, boolean useOriginal) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            String objData = model.toObjFormat(useOriginal);
            fileWriter.write(objData);
        }
    }
}

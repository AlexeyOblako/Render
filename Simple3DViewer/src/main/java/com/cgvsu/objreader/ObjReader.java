package com.cgvsu.objreader;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjReader {
	private static final String OBJ_VERTEX_TOKEN = "v";
	private static final String OBJ_TEXTURE_TOKEN = "vt";
	private static final String OBJ_NORMAL_TOKEN = "vn";
	private static final String OBJ_FACE_TOKEN = "f";

	/**
	 * Читает содержимое файла OBJ и создает объект Model.
	 *
	 * @param fileContent содержимое файла OBJ в виде строки
	 * @return объект Model, представляющий данные OBJ
	 * @throws Exception если возникает ошибка при чтении файла
	 */
	public static Model read(String fileContent) throws Exception {
		Model result = new Model();
		int lineInd = 0;
		Scanner scanner = new Scanner(fileContent);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList(line.split("\\s+")));
			if (wordsInLine.isEmpty()) continue;

			String token = wordsInLine.remove(0);
			lineInd++;

			switch (token) {
				//Если совпадают токены, то мы вызываем методы parse, передавая строке значения
				case OBJ_VERTEX_TOKEN -> result.vertices.add(parseVertex(wordsInLine, lineInd));
				case OBJ_TEXTURE_TOKEN -> result.textureVertices.add(parseTextureVertex(wordsInLine, lineInd));
				case OBJ_NORMAL_TOKEN -> result.normals.add(parseNormal(wordsInLine, lineInd));
				case OBJ_FACE_TOKEN -> {
					result.polygons.add(parseFace(wordsInLine, lineInd));
					ensureTextureConsistency(result.polygons, lineInd);
				}
				default -> {}
			}
		}


		validateModel(result, lineInd);
		resolveIndices(result);
		return result;
	}

	/**
	 * Парсит строку, представляющую вершину, и возвращает объект Vector3f.
	 *
	 * @param words   список строковых значений
	 * @param lineInd номер строки для обработки
	 * @return объект Vector3f, представляющий вершину
	 */
	static Vector3f parseVertex(ArrayList<String> words, int lineInd) {
		validateArgumentCount(words, 3, "vertex", lineInd);
		return new Vector3f(
				parseFloat(words.get(0), lineInd),
				parseFloat(words.get(1), lineInd),
				parseFloat(words.get(2), lineInd)
		);
	}

	/**
	 * Парсит строку, представляющую текстурную вершину, и возвращает объект Vector2f.
	 *
	 * @param words   список строковых значений
	 * @param lineInd номер строки для обработки
	 * @return объект Vector2f, представляющий текстурную вершину
	 */
	static Vector2f parseTextureVertex(ArrayList<String> words, int lineInd) {
		validateArgumentCount(words, 2, "texture vertex", lineInd);
		return new Vector2f(
				parseFloat(words.get(0), lineInd),
				parseFloat(words.get(1), lineInd)
		);
	}

	/**
	 * Парсит строку, представляющую нормаль, и возвращает объект Vector3f.
	 *
	 * @param words   список строковых значений
	 * @param lineInd номер строки для обработки
	 * @return объект Vector3f, представляющий нормаль
	 */
	static Vector3f parseNormal(ArrayList<String> words, int lineInd) {
		validateArgumentCount(words, 3, "normal", lineInd);
		return new Vector3f(
				parseFloat(words.get(0), lineInd),
				parseFloat(words.get(1), lineInd),
				parseFloat(words.get(2), lineInd)
		);
	}

	/**
	 * Парсит строку, представляющую полигон, и возвращает объект Polygon.
	 *
	 * @param stroka   список строковых значений
	 * @param lineInd номер строки для обработки
	 * @return объект Polygon, представляющий полигон
	 */
	static Polygon parseFace(ArrayList<String> stroka, int lineInd) {
		if (stroka.size() < 3) {
			throw ObjReaderException.tooFewArguments("polygon", lineInd);
		}

		ArrayList<Integer> vertexIndices = new ArrayList<>();
		ArrayList<Integer> textureIndices = new ArrayList<>();
		ArrayList<Integer> normalIndices = new ArrayList<>();

		for (String strok : stroka) {
			parseFaceWord(strok, vertexIndices, textureIndices, normalIndices, lineInd);
		}

		if (hasDuplicates(vertexIndices)) {
			throw ObjReaderException.duplicateVertexIndex(lineInd);
		}

		Polygon polygon = new Polygon();
		polygon.setVertexIndices(vertexIndices);
		polygon.setTextureVertexIndices(textureIndices);
		polygon.setNormalIndices(normalIndices);
		return polygon;
	}

	/**
	 * Парсит отдельное слово из полигона и добавляет индексы в соответствующие списки.
	 *
	 * @param stroka            строка, представляющая элемент полигона
	 * @param vertexIndices   список индексов вершин
	 * @param textureIndices  список индексов текстур
	 * @param normalIndices   список индексов нормалей
	 * @param lineInd        номер строки для обработки
	 */
	private static void parseFaceWord(
			String stroka,
			ArrayList<Integer> vertexIndices,
			ArrayList<Integer> textureIndices,
			ArrayList<Integer> normalIndices,
			int lineInd) {
		String[] parts = stroka.split("/");
		switch (parts.length) {
			case 1 -> vertexIndices.add(parseIndex(parts[0], lineInd));
			case 2 -> {
				vertexIndices.add(parseIndex(parts[0], lineInd));
				textureIndices.add(parseIndex(parts[1], lineInd));
			}
			case 3 -> {
				vertexIndices.add(parseIndex(parts[0], lineInd));
				if (!parts[1].isEmpty()) {
					textureIndices.add(parseIndex(parts[1], lineInd));
				}
				normalIndices.add(parseIndex(parts[2], lineInd));
			}
			default -> throw ObjReaderException.invalidElementSize(lineInd);
		}
	}

	/**
	 * Проверяет количество аргументов в строке.
	 *
	 * @param stroka        список строковых значений
	 * @param expected     ожидаемое количество аргументов
	 * @param element      имя элемента (вершина, текстурная вершина и т.д.)
	 * @param lineInd      номер строки для обработки
	 */
	private static void validateArgumentCount(ArrayList<String> stroka, int expected, String element, int lineInd) {
		if (stroka.size() > expected) {
			throw ObjReaderException.tooManyArguments(element, lineInd);
		}
		if (stroka.size() < expected) {
			throw ObjReaderException.tooFewArguments(element, lineInd);
		}
	}

	/**
	 * Проверяет согласованность текстур в полигонах.
	 *
	 * @param polygons список полигонов
	 * @param lineInd  номер строки для обработки
	 * @throws Exception если возникает ошибка
	 */
	private static void ensureTextureConsistency(ArrayList<Polygon> polygons, int lineInd) throws Exception {
		if (polygons.size() < 2) return;

		boolean lastHasTexture = !polygons.get(polygons.size() - 1).getTextureVertexIndices().isEmpty();
		boolean secondLastHasTexture = !polygons.get(polygons.size() - 2).getTextureVertexIndices().isEmpty();

		if (lastHasTexture != secondLastHasTexture) {
			throw ObjReaderException.textureInconsistency(lineInd);
		}
	}

	/**
	 * Проверяет модель на наличие вершин и полигонов.
	 *
	 * @param model  объект Model для проверки
	 * @param lineInd номер строки для обработки
	 */
	private static void validateModel(Model model, int lineInd) {
		if (model.vertices.isEmpty()) {
			throw ObjReaderException.noVerticesFound(lineInd);
		}
		if (model.polygons.isEmpty()) {
			throw ObjReaderException.noPolygonsFound(lineInd);
		}
	}

	/**
	 * Разрешает индексы в модели.
	 *
	 * @param model объект Model для обработки
	 * @throws Exception если возникает ошибка
	 */
	private static void resolveIndices(Model model) throws Exception {
		for (Polygon polygon : model.polygons) {
			resolvePolygonIndices(polygon.getVertexIndices(), model.vertices.size());
			resolvePolygonIndices(polygon.getTextureVertexIndices(), model.textureVertices.size());
			resolvePolygonIndices(polygon.getNormalIndices(), model.normals.size());
		}
	}

	/**
	 * Разрешает индексы полигона.
	 *
	 * @param indices список индексов для обработки
	 * @param size    размер списка вершин, текстур или нормалей
	 * @throws Exception если индекс выходит за пределы
	 */
	private static void resolvePolygonIndices(ArrayList<Integer> indices, int size) throws Exception {
		for (int i = 0; i < indices.size(); i++) {
			int index = indices.get(i);
			if (index < 0) {
				indices.set(i, size + index);
			}
			if (indices.get(i) < 0 || indices.get(i) >= size) {
				throw ObjReaderException.indexOutOfBounds();
			}
		}
	}

	/**
	 * Проверяет наличие дубликатов в списке индексов.
	 *
	 * @param indices список индексов для проверки
	 * @return true, если дубликаты найдены, иначе false
	 */
	private static boolean hasDuplicates(ArrayList<Integer> indices) {
		for (int i = 0; i < indices.size() - 1; i++) {
			for (int j = i + 1; j < indices.size(); j++) {
				if (indices.get(i).equals(indices.get(j))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Парсит строку в индекс вершины.
	 *
	 * @param value   строковое значение индекса
	 * @param lineInd номер строки для обработки
	 * @return индекс вершины
	 */
	private static int parseIndex(String value, int lineInd) {
		try {
			return Integer.parseInt(value) - 1;
		} catch (NumberFormatException e) {
			throw ObjReaderException.parseFailed("int", lineInd);
		}
	}

	/**
	 * Парсит строку в значение с плавающей запятой.
	 *
	 * @param value   строковое значение
	 * @param lineInd номер строки для обработки
	 * @return значение с плавающей запятой
	 */
	private static float parseFloat(String value, int lineInd) {
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException e) {
			throw ObjReaderException.parseFailed("float", lineInd);
		}
	}
}


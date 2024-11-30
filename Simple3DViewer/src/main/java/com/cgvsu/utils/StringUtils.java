package com.cgvsu.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    // TODO: fix bags
    public static String generateUniqueName(String input, List<String> existingNames) {
        if (!existingNames.contains(input)) {
            // Если данное название не существует, просто вернуть его
            return input;
        } else {
            // Если название уже существует, добавить цифру в конец
            int maxCount = findMaxCount(input, existingNames);
            return generateUniqueNameWithCount(input, maxCount + 1);
        }
    }

    private static int findMaxCount(String input, List<String> existingNames) {
        int maxCount = 0;
        String baseName = getBaseName(input);
        for (String existingName : existingNames) {
            if (existingName.startsWith(baseName)) {
                try {
                    // Попытаться получить цифру из имени
                    String countStr = existingName.substring(baseName.length());
                    Pattern pattern = Pattern.compile("\\s*\\((\\d+)\\)\\.obj");
                    Matcher matcher = pattern.matcher(countStr);

                    if (matcher.find()) {
                        int count = Integer.parseInt(matcher.group(1));
                        maxCount = Math.max(maxCount, count);
                    }
                } catch (NumberFormatException e) {
                    // Игнорировать исключение, если не удается преобразовать в число
                }
            }
        }
        return maxCount;
    }

    private static String getBaseName(String input) {
        // Получить базовое имя без цифры в скобках
        int index = input.lastIndexOf('(');
        if (index != -1) {
            return input.substring(0, index).trim();
        } else {
            return input.trim();
        }
    }

    private static String generateUniqueNameWithCount(String input, int count) {
        // Сгенерировать уникальное имя с цифрой в скобках
        String baseName = getBaseName(input);
        String extension = ".obj";
        return baseName + " (" + count + ")" + extension;
    }

}

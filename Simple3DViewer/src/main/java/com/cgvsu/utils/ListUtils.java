package com.cgvsu.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static List<Integer> stringToNumberList(List<String> list) {
        List<Integer> result = new ArrayList<>();
        for (String el : list) {
            result.add(Integer.parseInt(el));
        }

        return result;
    }
}

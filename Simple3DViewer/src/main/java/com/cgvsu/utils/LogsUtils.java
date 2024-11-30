package com.cgvsu.utils;

import com.cgvsu.log.Log;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class LogsUtils {
    public static Label generateLabelFromLog(Log log) {
        Label label = new Label(log.body());
        label.setTextFill(getLogColor(log));
        label.setWrapText(true);
        label.setPadding(new Insets(0, 0, 12, 0));
        return label;
    }

    public static Color getLogColor(Log log) {
        switch (log.status()) {
            case ERROR -> {
                return Color.RED;
            }
            case WARNING -> {
                return Color.ORANGE;
            }
            default -> {
                return Color.WHITE;
            }
        }
    }
}

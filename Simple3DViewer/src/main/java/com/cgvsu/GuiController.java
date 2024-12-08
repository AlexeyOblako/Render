package com.cgvsu;

import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.render_engine.ModelTransform;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

public class GuiController {

    final private float TRANSLATION = 0.5F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh = null;

    private Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private ModelTransform transform = new ModelTransform(); // Объект для управления трансформацией модели

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                // Изменил
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, transform, (int) width, (int) height, false);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            // todo: обработка ошибок
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
























//для сохраннеия модели
@FXML
private void saveOriginalModel() {
    if (mesh == null) {
        System.out.println("No model loaded to save.");
        return;
    }

    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
    fileChooser.setTitle("Save Original Model");

    File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
    if (file == null) {
        return;
    }

    try {
        // Используем оригинальные вершины для экспорта
        String modelData = mesh.toObjFormat(true); // Передаём true для сохранения оригинальных вершин
        Files.writeString(file.toPath(), modelData);
        System.out.println("Original model saved to " + file.getAbsolutePath());
    } catch (IOException exception) {
        System.out.println("Error saving model: " + exception.getMessage());
    }
}


    @FXML
    private void saveTransformedModel() {
        if (mesh == null) {
            System.out.println("No model loaded to save.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Transformed Model");

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        try {
            // Используем текущие (трансформированные) вершины для экспорта
            String modelData = mesh.toObjFormat(false); // Передаём false для сохранения трансформированных вершин
            Files.writeString(file.toPath(), modelData);
            System.out.println("Transformed model saved to " + file.getAbsolutePath());
        } catch (IOException exception) {
            System.out.println("Error saving model: " + exception.getMessage());
        }
    }






















    // Добавил методов для упр трнсфрц модлй
    @FXML
    public void handleScaleUp(ActionEvent actionEvent) {
        transform.setScale(transform.getScaleX() * 1.1F, transform.getScaleY() * 1.1F, transform.getScaleZ() * 1.1F);
    }

    @FXML
    public void handleScaleDown(ActionEvent actionEvent) {
        transform.setScale(transform.getScaleX() * 0.9F, transform.getScaleY() * 0.9F, transform.getScaleZ() * 0.9F);
    }

    @FXML
    public void handleRotateX(ActionEvent actionEvent) {
        transform.setRotation(transform.getRotationX() + 10, transform.getRotationY(), transform.getRotationZ());
    }

    @FXML
    public void handleRotateY(ActionEvent actionEvent) {
        transform.setRotation(transform.getRotationX(), transform.getRotationY() + 10, transform.getRotationZ());
    }

    @FXML
    public void handleRotateZ(ActionEvent actionEvent) {
        transform.setRotation(transform.getRotationX(), transform.getRotationY(), transform.getRotationZ() + 10);
    }

    @FXML
    public void handleTranslateX(ActionEvent actionEvent) {
        transform.setTranslation(transform.getTranslationX() + TRANSLATION, transform.getTranslationY(), transform.getTranslationZ());
    }

    @FXML
    public void handleTranslateY(ActionEvent actionEvent) {
        transform.setTranslation(transform.getTranslationX(), transform.getTranslationY() + TRANSLATION, transform.getTranslationZ());
    }

    @FXML
    public void handleTranslateZ(ActionEvent actionEvent) {
        transform.setTranslation(transform.getTranslationX(), transform.getTranslationY(), transform.getTranslationZ() + TRANSLATION);
    }

    // Методы управления камерой
    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }
}

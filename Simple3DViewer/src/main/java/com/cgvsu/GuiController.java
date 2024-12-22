package com.cgvsu;

import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

import com.cgvsu.math.Vector3f;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

public class GuiController {

    final private float TRANSLATION = 0.5F;
    final private float SCALE = 0.1F;
    final private float ROTATION = 10F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh = null;

    @FXML
    private ListView<String> modelListView; // Список моделей
    private ArrayList<Model> models = new ArrayList<>(); // Список моделей
    private int activeModelIndex = -1; // Индекс активной модели


    private Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;


    private double lastMouseX = 0;
    private double lastMouseY = 0;
    private boolean isMousePressed = false;


    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        // Инициализация списка моделей
        modelListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            setActiveModel(newVal.intValue());
        });

        // Инициализация таймлайна для рендеринга
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            //Рендеринг всех моделей
            for (Model model : models) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, model, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();

        canvas.setOnMousePressed(event -> handleMousePressed(event));
        canvas.setOnMouseDragged(event -> handleMouseDragged(event));
        canvas.setOnMouseReleased(event -> handleMouseReleased(event));
    }

    private void handleMousePressed(MouseEvent event) {
        lastMouseX = event.getSceneX();
        lastMouseY = event.getSceneY();
        isMousePressed = true;
    }

    private void handleMouseReleased(MouseEvent event) {
        isMousePressed = false;
    }
    private void updateCameraRotation(double deltaX, double deltaY) {
        float sensitivity = 0.1f; // Чувствительность мыши
        float yaw = (float) (-deltaX * sensitivity); // Инвертируем направление вращения по горизонтали
        float pitch = (float) (-deltaY * sensitivity);
        float roll = (float) (deltaY * sensitivity);

// Обновляем углы вращения камеры
        camera.rotateAroundTarget(yaw, pitch, roll);
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((anchorPane.getScene().getWindow()));
        if (file == null) {
            return;
        }


        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);

            Model newModel = ObjReader.read(fileContent);
            newModel.setName(file.getName()); // Установка имени модели
            models.add(newModel);
            updateModelList();
            setActiveModel(models.size() - 1); // Установка новой модели как активной
        } catch (IOException exception) {
            System.out.println("Ошибка загрузки модели: " + exception.getMessage());
        }
    }


    @FXML
    private void onSaveOriginalModelMenuItemClick() {
        if (mesh == null) {
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Original Model");

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {

            String originalModelContent = ObjWriter.write(mesh, false);
            Files.writeString(fileName, originalModelContent);
        } catch (IOException exception) {

        }
    }

    @FXML
    private void onSaveTransformedModelMenuItemClick() {
        if (mesh == null) {

            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Transformed Model");

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {

            String transformedModelContent = ObjWriter.write(mesh);
            Files.writeString(fileName, transformedModelContent);
        } catch (IOException exception) {

        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePositionAndTarget(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePositionAndTarget(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePositionAndTarget(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePositionAndTarget(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleModelScaleX(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f scale = mesh.getScale();
            mesh.setScale(new Vector3f(scale.getX() + SCALE, scale.getY(), scale.getZ()));
        }
    }

    @FXML
    public void handleModelScaleY(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f scale = mesh.getScale();
            mesh.setScale(new Vector3f(scale.getX(), scale.getY() + SCALE, scale.getZ()));
        }
    }

    @FXML
    public void handleModelScaleZ(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f scale = mesh.getScale();
            mesh.setScale(new Vector3f(scale.getX(), scale.getY(), scale.getZ() + SCALE));
        }
    }

    @FXML
    public void handleModelRotateX(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f rotation = mesh.getRotation();
            mesh.setRotation(new Vector3f(rotation.getX() + ROTATION, rotation.getY(), rotation.getZ()));
        }
    }

    @FXML
    public void handleModelRotateY(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f rotation = mesh.getRotation();
            mesh.setRotation(new Vector3f(rotation.getX(), rotation.getY() + ROTATION, rotation.getZ()));
        }
    }

    @FXML
    public void handleModelRotateZ(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f rotation = mesh.getRotation();
            mesh.setRotation(new Vector3f(rotation.getX(), rotation.getY(), rotation.getZ() + ROTATION));
        }
    }

    @FXML
    public void handleModelTranslateX(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f translation = mesh.getTranslation();
            mesh.setTranslation(new Vector3f(translation.getX() + TRANSLATION, translation.getY(), translation.getZ()));
        }
    }

    @FXML
    public void handleModelTranslateY(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f translation = mesh.getTranslation();
            mesh.setTranslation(new Vector3f(translation.getX(), translation.getY() + TRANSLATION, translation.getZ()));
        }
    }

    @FXML
    public void handleModelTranslateZ(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f translation = mesh.getTranslation();
            mesh.setTranslation(new Vector3f(translation.getX(), translation.getY(), translation.getZ() + TRANSLATION));
        }
    }

    @FXML
    public void handleModelScaleXNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f scale = mesh.getScale();
            mesh.setScale(new Vector3f(scale.getX() - SCALE, scale.getY(), scale.getZ()));
        }
    }

    @FXML
    public void handleModelScaleYNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f scale = mesh.getScale();
            mesh.setScale(new Vector3f(scale.getX(), scale.getY() - SCALE, scale.getZ()));
        }
    }

    @FXML
    public void handleModelScaleZNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f scale = mesh.getScale();
            mesh.setScale(new Vector3f(scale.getX(), scale.getY(), scale.getZ() - SCALE));
        }
    }

    @FXML
    public void handleModelRotateXNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f rotation = mesh.getRotation();
            mesh.setRotation(new Vector3f(rotation.getX() - ROTATION, rotation.getY(), rotation.getZ()));
        }
    }

    @FXML
    public void handleModelRotateYNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f rotation = mesh.getRotation();
            mesh.setRotation(new Vector3f(rotation.getX(), rotation.getY() - ROTATION, rotation.getZ()));
        }
    }

    @FXML
    public void handleModelRotateZNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f rotation = mesh.getRotation();
            mesh.setRotation(new Vector3f(rotation.getX(), rotation.getY(), rotation.getZ() - ROTATION));
        }
    }

    @FXML
    public void handleModelTranslateXNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f translation = mesh.getTranslation();
            mesh.setTranslation(new Vector3f(translation.getX() - TRANSLATION, translation.getY(), translation.getZ()));
        }
    }

    @FXML
    public void handleModelTranslateYNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f translation = mesh.getTranslation();
            mesh.setTranslation(new Vector3f(translation.getX(), translation.getY() - TRANSLATION, translation.getZ()));
        }
    }

    @FXML
    public void handleModelTranslateZNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3f translation = mesh.getTranslation();
            mesh.setTranslation(new Vector3f(translation.getX(), translation.getY(), translation.getZ() - TRANSLATION));
        }
    }


    private void handleMouseDragged(MouseEvent event) {
        float deltaX = (float) (event.getX() - lastMouseX);
        float deltaY = (float) (event.getY() - lastMouseY);

        Vector3f position = camera.getPosition();
        Vector3f target = camera.getTarget();

        // Calculate the direction from the camera position to the target
        Vector3f direction = target.deduct(position).normalize();

        // Calculate the right vector
        Vector3f right = Vector3f.crossProduct(direction, new Vector3f(0, 1, 0)).normalize();

        // Calculate the up vector
        Vector3f up = Vector3f.crossProduct(right, direction).normalize();

        // Update the target based on mouse movement
        target = Vector3f.add(target, right.multiply(deltaX * 0.01f));
        target = Vector3f.add(target, up.multiply(deltaY * 0.01f));

        camera.setTarget(target);

        lastMouseX = event.getX();
        lastMouseY = event.getY();
    }


    @FXML
    private void onRemoveModelButtonClick() {
        if (activeModelIndex != -1) {
            // Удаляем модель из списка
            models.remove(activeModelIndex);
            updateModelList();

            // Сбрасываем активную модель
            if (models.isEmpty()) {
                mesh = null; // Если список пуст, сбрасываем mesh
                activeModelIndex = -1; // Сбрасываем индекс активной модели
                System.out.println("Все модели удалены");
            } else {
                // Устанавливаем новую активную модель, если есть
                setActiveModel(Math.max(0, activeModelIndex - 1)); // Устанавливаем предыдущую модель
            }
        } else {
            System.out.println("Нет активной модели для удаления");
        }
    }

    private void updateModelList() {
        modelListView.getItems().clear();
        for (Model model : models) {
            modelListView.getItems().add(model.getName());
        }
    }

    private void setActiveModel(int index) {
        if (index >= 0 && index < models.size()) {
            activeModelIndex = index;
            mesh = models.get(index); // Устанавливаем mesh как активную модель
            System.out.println("Активная модель: " + mesh.getName());
        } else {
            activeModelIndex = -1;
            mesh = null; // Сбрасываем mesh, если нет активной модели
            System.out.println("Нет активной модели");
        }
    }
}

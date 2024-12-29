package com.cgvsu;

import com.cgvsu.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.ObjWriter;

public class GuiController {
    private double lastMouseX = 0;
    private double lastMouseY = 0;
    private boolean isMousePressed = false;
    final private float TRANSLATION = 0.5F;
    final private float SCALE = 0.1F;
    final private float ROTATION = 10F;
    final private float ZOOM_SENSITIVITY = 0.1F;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Canvas canvas;

    @FXML
    public VBox modelList;

    public List<Model> models = new ArrayList<>();
    public Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    public Timeline timeline;

    @FXML
    public void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            for (Model model : models) {
                if (isModelSelected(model)) {
                    RenderEngine.render(canvas.getGraphicsContext2D(), camera, model, (int) width, (int) height);
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();

        canvas.setOnMousePressed(event -> handleMousePressed(event));
        canvas.setOnMouseDragged(event -> handleMouseDragged(event));
        canvas.setOnMouseReleased(event -> handleMouseReleased(event));
        canvas.setOnScroll(event -> handleMouseScroll(event));
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        List<File> files = fileChooser.showOpenMultipleDialog((Stage) canvas.getScene().getWindow());
        if (files == null) {
            return;
        }

        for (File file : files) {
            Path fileName = Path.of(file.getAbsolutePath());

            try {
                String fileContent = Files.readString(fileName);
                Model model = ObjReader.read(fileContent);
                model.resetTransformations();
                models.add(model);

                CheckBox checkBox = new CheckBox(file.getName());
                checkBox.setUserData(model);
                checkBox.setSelected(true); // Select the model by default
                modelList.getChildren().add(checkBox);

            } catch (IOException exception) {
                // Handle exception
            }
        }
    }

    @FXML
    private void onSaveOriginalModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Original Model");

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            StringBuilder content = new StringBuilder();
            for (Model model : models) {
                if (isModelSelected(model)) {
                    content.append(ObjWriter.write(model, false)).append("\n");
                }
            }
            Files.writeString(fileName, content.toString());
        } catch (IOException exception) {
            // Handle exception
        }
    }

    @FXML
    private void onSaveTransformedModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Transformed Model");

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            StringBuilder content = new StringBuilder();
            for (Model model : models) {
                if (isModelSelected(model)) {
                    content.append(ObjWriter.write(model)).append("\n");
                }
            }
            Files.writeString(fileName, content.toString());
        } catch (IOException exception) {
            // Handle exception
        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        Vector3f direction = Vector3f.deduct(camera.getTarget(), camera.getPosition()).normalize();
        camera.movePosition(direction.multiply(TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        Vector3f direction = Vector3f.deduct(camera.getTarget(), camera.getPosition()).normalize();
        camera.movePosition(direction.multiply(-TRANSLATION));
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
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f scale = model.getScale();
                model.setScale(new Vector3f(scale.getX() + SCALE, scale.getY(), scale.getZ()));
            }
        }
    }

    @FXML
    public void handleModelScaleY(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f scale = model.getScale();
                model.setScale(new Vector3f(scale.getX(), scale.getY() + SCALE, scale.getZ()));
            }
        }
    }

    @FXML
    public void handleModelScaleZ(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f scale = model.getScale();
                model.setScale(new Vector3f(scale.getX(), scale.getY(), scale.getZ() + SCALE));
            }
        }
    }

    @FXML
    public void handleModelRotateX(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f rotation = model.getRotation();
                model.setRotation(new Vector3f(rotation.getX() + ROTATION, rotation.getY(), rotation.getZ()));
            }
        }
    }

    @FXML
    public void handleModelRotateY(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f rotation = model.getRotation();
                model.setRotation(new Vector3f(rotation.getX(), rotation.getY() + ROTATION, rotation.getZ()));
            }
        }
    }

    @FXML
    public void handleModelRotateZ(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f rotation = model.getRotation();
                model.setRotation(new Vector3f(rotation.getX(), rotation.getY(), rotation.getZ() + ROTATION));
            }
        }
    }

    @FXML
    public void handleModelTranslateX(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f translation = model.getTranslation();
                model.setTranslation(new Vector3f(translation.getX() + TRANSLATION, translation.getY(), translation.getZ()));
            }
        }
    }

    @FXML
    public void handleModelTranslateY(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f translation = model.getTranslation();
                model.setTranslation(new Vector3f(translation.getX(), translation.getY() + TRANSLATION, translation.getZ()));
            }
        }
    }

    @FXML
    public void handleModelTranslateZ(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f translation = model.getTranslation();
                model.setTranslation(new Vector3f(translation.getX(), translation.getY(), translation.getZ() + TRANSLATION));
            }
        }
    }

    @FXML
    public void handleModelScaleXNegative(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f scale = model.getScale();
                model.setScale(new Vector3f(scale.getX() - SCALE, scale.getY(), scale.getZ()));
            }
        }
    }

    @FXML
    public void handleModelScaleYNegative(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f scale = model.getScale();
                model.setScale(new Vector3f(scale.getX(), scale.getY() - SCALE, scale.getZ()));
            }
        }
    }

    @FXML
    public void handleModelScaleZNegative(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f scale = model.getScale();
                model.setScale(new Vector3f(scale.getX(), scale.getY(), scale.getZ() - SCALE));
            }
        }
    }

    @FXML
    public void handleModelRotateXNegative(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f rotation = model.getRotation();
                model.setRotation(new Vector3f(rotation.getX() - ROTATION, rotation.getY(), rotation.getZ()));
            }
        }
    }

    @FXML
    public void handleModelRotateYNegative(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f rotation = model.getRotation();
                model.setRotation(new Vector3f(rotation.getX(), rotation.getY() - ROTATION, rotation.getZ()));
            }
        }
    }

    @FXML
    public void handleModelRotateZNegative(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f rotation = model.getRotation();
                model.setRotation(new Vector3f(rotation.getX(), rotation.getY(), rotation.getZ() - ROTATION));
            }
        }
    }

    @FXML
    public void handleModelTranslateXNegative(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f translation = model.getTranslation();
                model.setTranslation(new Vector3f(translation.getX() - TRANSLATION, translation.getY(), translation.getZ()));
            }
        }
    }

    @FXML
    public void handleModelTranslateYNegative(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f translation = model.getTranslation();
                model.setTranslation(new Vector3f(translation.getX(), translation.getY() - TRANSLATION, translation.getZ()));
            }
        }
    }

    @FXML
    public void handleModelTranslateZNegative(ActionEvent actionEvent) {
        for (Model model : models) {
            if (isModelSelected(model)) {
                Vector3f translation = model.getTranslation();
                model.setTranslation(new Vector3f(translation.getX(), translation.getY(), translation.getZ() - TRANSLATION));
            }
        }
    }

    private void handleMouseScroll(ScrollEvent event) {
        double deltaY = event.getDeltaY();
        Vector3f direction = camera.getTarget().deduct(camera.getPosition()).normalize();
        camera.movePosition(direction.multiply((float) (deltaY * ZOOM_SENSITIVITY)));
    }

    private void handleMousePressed(MouseEvent event) {
        lastMouseX = event.getSceneX();
        lastMouseY = event.getSceneY();
        isMousePressed = true;
    }

    private void handleMouseDragged(MouseEvent event) {
        if (isMousePressed) {
            double deltaX = event.getSceneX() - lastMouseX;
            double deltaY = event.getSceneY() - lastMouseY;

            updateCameraRotation(deltaX, deltaY);

            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();
        }
    }

    private void handleMouseReleased(MouseEvent event) {
        isMousePressed = false;
    }

    private void updateCameraRotation(double deltaX, double deltaY) {
        float sensitivity = 0.1f;
        float yaw = (float) (-deltaX * sensitivity);
        float pitch = (float) (-deltaY * sensitivity);

        camera.rotateAroundTarget(yaw, pitch);
    }

    private boolean isModelSelected(Model model) {
        for (Object child : modelList.getChildren()) {
            if (child instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) child;
                if (checkBox.getUserData() == model && checkBox.isSelected()) {
                    return true;
                }
            }
        }
        return false;
    }
}

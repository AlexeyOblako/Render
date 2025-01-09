package com.cgvsu;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector4f;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.render_engine.CameraManager;
import com.cgvsu.render_engine.GraphicConveyor;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.utils.NormalUtils;
import com.cgvsu.utils.models_utils.Triangulation;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
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

public class GuiController {
    private double lastMouseX = 0;
    private double lastMouseY = 0;
    private boolean isMousePressed = false;
    final private float TRANSLATION = 0.5F;
    final private float SCALE = 0.1F;
    final private float ROTATION = 10F;
    final private float ZOOM_SENSITIVITY = 0.1F;
    private List<Integer> selectedVertices = new ArrayList<>();

    private boolean isTriangulationEnabled = false;
    private boolean isRasterizationEnabled = false;
    private boolean fillPolygons = false;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh = null;
    @FXML
    private ColorPicker modelColorPicker;

    @FXML
    private ListView<String> modelListView;
    private ArrayList<Model> models = new ArrayList<>();
    private int activeModelIndex = -1;

    private Timeline timeline;

    @FXML
    private Button addCameraButton;
    @FXML
    private Button removeCameraButton;
    @FXML
    private Button switchCameraButton;
    @FXML
    private Label activeCameraLabel;
    @FXML
    private ComboBox<String> cameraComboBox;

    private CameraManager cameraManager;

    @FXML
    private void initialize() {
        modelColorPicker.setValue(Color.BLACK);
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        modelListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            setActiveModel(newVal.intValue());
        });

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);

            Camera activeCamera = cameraManager.getActiveCamera();
            if (activeCamera != null) {
                activeCamera.setAspectRatio((float) (width / height));
            }

            RenderEngine.render(
                    canvas.getGraphicsContext2D(),
                    cameraManager,
                    models,
                    (int) width,
                    (int) height,
                    selectedVertices,
                    modelColorPicker.getValue(),
                    Color.WHITE,
                    isRasterizationEnabled
            );
        });

        cameraManager = new CameraManager();
        cameraManager.addCamera(new Camera(
                new Vector3f(0, 0, 100),
                new Vector3f(0, 0, 0),
                1.0F, 1, 0.01F, 100
        ));
        updateCameraComboBox();
        updateActiveCameraLabel();

        timeline.getKeyFrames().add(frame);
        timeline.play();

        canvas.setOnMousePressed(this::handleMousePressed1);
        canvas.setOnMouseDragged(this::handleMouseDragged1);
        canvas.setOnMouseReleased(this::handleMouseReleased1);
        canvas.setOnKeyPressed(this::handleKeyPressed);
        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);
        canvas.setOnMouseReleased(this::handleMouseReleased);
        canvas.setOnScroll(this::handleMouseScroll);
    }

    private void handleMousePressed1(MouseEvent event) {
        lastMouseX = event.getSceneX();
        lastMouseY = event.getSceneY();
        isMousePressed = true;

        boolean isShiftPressed = event.isShiftDown();

        double mouseX = event.getX();
        double mouseY = event.getY();

        Matrix4f modelMatrix = GraphicConveyor.rotateScaleTranslate(
                models.get(activeModelIndex).getScale().getX(),
                models.get(activeModelIndex).getScale().getY(),
                models.get(activeModelIndex).getScale().getZ(),
                models.get(activeModelIndex).getRotation().getX(),
                models.get(activeModelIndex).getRotation().getY(),
                models.get(activeModelIndex).getRotation().getZ(),
                models.get(activeModelIndex).getTranslation().getX(),
                models.get(activeModelIndex).getTranslation().getY(),
                models.get(activeModelIndex).getTranslation().getZ()
        );
        Matrix4f viewMatrix = cameraManager.getActiveCamera().getViewMatrix();
        Matrix4f projectionMatrix = cameraManager.getActiveCamera().getProjectionMatrix();
        Matrix4f modelViewProjectionMatrix = Matrix4f.multiply(projectionMatrix, Matrix4f.multiply(viewMatrix, modelMatrix));

        int closestVertexIndex = findClosestVertex(mouseX, mouseY, modelViewProjectionMatrix);

        if (closestVertexIndex != -1) {
            if (!isShiftPressed) {
                selectedVertices.clear();
                selectedVertices.add(closestVertexIndex);
            } else {
                if (!selectedVertices.contains(closestVertexIndex)) {
                    selectedVertices.add(closestVertexIndex);
                }
            }
        }
    }

    private int findClosestVertex(double mouseX, double mouseY, Matrix4f modelViewProjectionMatrix) {
        if (activeModelIndex == -1) return -1;

        Model activeModel = models.get(activeModelIndex);
        double minDistance = Double.MAX_VALUE;
        int closestVertexIndex = -1;

        for (int i = 0; i < activeModel.vertices.size(); i++) {
            Vector3f vertex = activeModel.vertices.get(i);

            Vector4f vertexVecmath = new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1);
            Vector2f screenPoint = GraphicConveyor.vertexToPoint(
                    Matrix4f.multiply(modelViewProjectionMatrix, vertexVecmath).normalizeTo3f(),
                    (int) canvas.getWidth(),
                    (int) canvas.getHeight()
            );

            double distance = Math.sqrt(Math.pow(screenPoint.getX() - mouseX, 2) + Math.pow(screenPoint.getY() - mouseY, 2));

            if (distance < minDistance) {
                minDistance = distance;
                closestVertexIndex = i;
            }
        }

        return closestVertexIndex;
    }

    private void handleMouseReleased1(MouseEvent event) {
        isMousePressed = false;
    }

    @FXML
    private void handleModelColorChange(ActionEvent event) {
        Color color = modelColorPicker.getValue();
        canvas.getGraphicsContext2D().setStroke(color);
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
            newModel.setName(file.getName());
            models.add(newModel);
            updateModelList();
            setActiveModel(models.size() - 1);
        } catch (IOException exception) {
            System.out.println("Ошибка загрузки модели: " + exception.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            System.out.println("Ошибка сохранения модели: " + exception.getMessage());
        }
    }

    @FXML
    private void onSaveTransformedModelMenuItemClick() {
        if (activeModelIndex != -1) {
            Model activeModel = models.get(activeModelIndex);
            saveModel(activeModel, true);
        } else {
            System.out.println("Нет активной модели для сохранения");
        }
    }

    private void saveModel(Model model, boolean applyTransformations) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Model");

        File file = fileChooser.showSaveDialog((anchorPane.getScene().getWindow()));
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String modelContent = ObjWriter.write(model, applyTransformations);
            Files.writeString(fileName, modelContent);
            System.out.println("Модель сохранена: " + fileName);
        } catch (IOException exception) {
            System.out.println("Ошибка сохранения модели: " + exception.getMessage());
        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        Camera activeCamera = cameraManager.getActiveCamera();
        if (activeCamera != null) {
            activeCamera.movePositionAndTarget(new Vector3f(0, 0, -TRANSLATION));
        }
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        Camera activeCamera = cameraManager.getActiveCamera();
        if (activeCamera != null) {
            activeCamera.movePositionAndTarget(new Vector3f(0, 0, TRANSLATION));
        }
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        Camera activeCamera = cameraManager.getActiveCamera();
        if (activeCamera != null) {
            activeCamera.movePositionAndTarget(new Vector3f(TRANSLATION, 0, 0));
        }
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        Camera activeCamera = cameraManager.getActiveCamera();
        if (activeCamera != null) {
            activeCamera.movePositionAndTarget(new Vector3f(-TRANSLATION, 0, 0));
        }
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

    private void handleMouseDragged1(MouseEvent event) {
        float deltaX = (float) (event.getX() - lastMouseX);
        float deltaY = (float) (event.getY() - lastMouseY);

        Camera activeCamera = cameraManager.getActiveCamera();
        if (activeCamera == null) {
            return;
        }

        Vector3f position = activeCamera.getPosition();
        Vector3f target = activeCamera.getTarget();

        Vector3f direction = target.deduct(position).normalize();
        Vector3f right = Vector3f.crossProduct(direction, new Vector3f(0, 1, 0)).normalize();
        Vector3f up = Vector3f.crossProduct(right, direction).normalize();

        target = Vector3f.add(target, right.multiply(deltaX * 0.01f));
        target = Vector3f.add(target, up.multiply(deltaY * 0.01f));

        activeCamera.setTarget(target);

        lastMouseX = event.getX();
        lastMouseY = event.getY();
    }

    @FXML
    private void onRemoveModelButtonClick() {
        if (activeModelIndex != -1) {
            models.remove(activeModelIndex);
            updateModelList();

            if (models.isEmpty()) {
                mesh = null;
                activeModelIndex = -1;
                System.out.println("Все модели удалены");
            } else {
                setActiveModel(Math.max(0, activeModelIndex - 1));
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
            mesh = models.get(index);
            System.out.println("Активная модель: " + mesh.getName());
        } else {
            activeModelIndex = -1;
            mesh = null;
            System.out.println("Нет активной модели");
        }
    }

    @FXML
    private void handleRemoveVerticesButtonClick(ActionEvent event) {
        if (activeModelIndex != -1) {
            Model activeModel = models.get(activeModelIndex);
            for (int vertexIndex : selectedVertices) {
                activeModel.removeVertexAndUpdatePolygons(vertexIndex);
            }
            selectedVertices.clear();
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            if (activeModelIndex != -1) {
                Model activeModel = models.get(activeModelIndex);
                activeModel.removeVertices(selectedVertices);
                selectedVertices.clear();
            }
        }
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

    private void handleMouseScroll(ScrollEvent event) {
        double deltaY = event.getDeltaY();
        Camera activeCamera = cameraManager.getActiveCamera();
        if (activeCamera != null) {
            Vector3f direction = activeCamera.getTarget().deduct(activeCamera.getPosition()).normalize();
            activeCamera.movePosition(direction.multiply((float) (deltaY * ZOOM_SENSITIVITY)));
        }
    }

    private void updateCameraRotation(double deltaX, double deltaY) {
        float sensitivity = 0.1f;
        float yaw = (float) (-deltaX * sensitivity);
        float pitch = (float) (-deltaY * sensitivity);

        Camera activeCamera = cameraManager.getActiveCamera();
        if (activeCamera != null) {
            activeCamera.rotateAroundTarget(yaw, pitch);
        }
    }

    private boolean isTriangulationApplied = false;
    private Model originalModel;

    @FXML
    private void handleTriangulate(ActionEvent event) {
        if (!isTriangulationApplied) {
            isTriangulationEnabled = true;
            System.out.println("Триангуляция включена");
            if (activeModelIndex != -1) {
                Model activeModel = models.get(activeModelIndex);
                originalModel = activeModel;
                models.set(activeModelIndex, Triangulation.getTriangulatedModel(activeModel));
                isTriangulationApplied = true;
                timeline.playFromStart();
            }
        } else {
            System.out.println("Триангуляция уже была применена");
        }
    }

    @FXML
    private void handleDisableTriangulate(ActionEvent event) {
        isTriangulationEnabled = false;
        isTriangulationApplied = false;
        System.out.println("Триангуляция отключена");
        if (activeModelIndex != -1) {
            models.set(activeModelIndex, loadOriginalModel());
            timeline.playFromStart();
        }
    }

    @FXML
    private void handleEnableRasterization(ActionEvent event) {
        isRasterizationEnabled = true;
        System.out.println("Растеризация включена");
        timeline.playFromStart();
    }

    @FXML
    private void handleDisableRasterization(ActionEvent event) {
        isRasterizationEnabled = false;
        System.out.println("Растеризация отключена");
        timeline.playFromStart();
    }

    private Model loadOriginalModel() {
        return originalModel;
    }

    @FXML
    private void handleAddCamera() {
        Camera newCamera = new Camera(
                new Vector3f(0, 0, 100),
                new Vector3f(0, 0, 0),
                1.0F, 1, 0.01F, 100
        );
        cameraManager.addCamera(newCamera);
        updateCameraComboBox();
        updateActiveCameraLabel();

        canvas.requestFocus();
    }

    @FXML
    private void handleRemoveCamera() {
        int activeIndex = cameraManager.getActiveCameraIndex();
        if (activeIndex >= 0) {
            cameraManager.removeCamera(activeIndex);
            updateCameraComboBox();
            updateActiveCameraLabel();
        }
    }

    @FXML
    private void handleSwitchCamera() {
        int nextIndex = (cameraManager.getActiveCameraIndex() + 1) % cameraManager.getCameras().size();
        cameraManager.setActiveCamera(nextIndex);
        updateActiveCameraLabel();
    }

    private void updateActiveCameraLabel() {
        Camera activeCamera = cameraManager.getActiveCamera();
        if (activeCamera != null) {
            activeCameraLabel.setText("Активная камера: " + cameraManager.getActiveCameraIndex());
        } else {
            activeCameraLabel.setText("Нет активной камеры");
        }
    }

    @FXML
    private void handleCameraSelection() {
        int selectedIndex = cameraComboBox.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < cameraManager.getCameras().size()) {
            cameraManager.setActiveCamera(selectedIndex);
            updateActiveCameraLabel();

            canvas.setOnKeyPressed(this::handleKeyPressed);
            canvas.setOnKeyReleased(this::handleKeyReleased);

            canvas.requestFocus();
        }
    }

    private void updateCameraComboBox() {
        cameraComboBox.getItems().clear();
        for (int i = 0; i < cameraManager.getCameras().size(); i++) {
            cameraComboBox.getItems().add("Камера " + i);
        }
        cameraComboBox.getSelectionModel().select(cameraManager.getActiveCameraIndex());
    }

    private void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.SHIFT) {
            // Ничего не делаем, так как выделение уже сохранено
        }
    }
}
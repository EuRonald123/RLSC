package com.rnd.controller;

import com.rnd.model.Category;
import com.rnd.model.Shortcut;
import com.rnd.service.ShortcutCreatorService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;

public class RlscController {

    @FXML
    private TextField textName;
    @FXML
    private TextField textExecutablePath;
    @FXML
    private TextField textIconPath;
    @FXML
    private TextField textFlags;
    @FXML
    private ComboBox<Category> comboCategory;

    @FXML
    public void initialize() {
        comboCategory.getItems().addAll(Category.values());
        comboCategory.setValue(Category.NONE);
    }

    //Show Alert Popup
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void findExecutable() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Executable / AppImage");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("AppImage", "*.AppImage", "*.appimage"),
                new FileChooser.ExtensionFilter("All Files", "*")
        );

        File selectedFile = fileChooser.showOpenDialog(textExecutablePath.getScene().getWindow());

        if (selectedFile != null) {
            textExecutablePath.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void findIcon() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Icon");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("img", "*.png", "*.svg", "*.ico", "*.xpm", "*.jpg")
        );

        File selectedFile = fileChooser.showOpenDialog(textIconPath.getScene().getWindow());

        if (selectedFile != null) {
            textIconPath.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void checkPreview() {
        System.out.println("Clicou em Preview!");
    }

    @FXML
    private void shortcutCreate() {
        if (textName.getText().isBlank()){
            showAlert(Alert.AlertType.WARNING, "Attention", "Please enter the shortcut name!");
            return;
        }

        if (textExecutablePath.getText().isBlank()){
            showAlert(Alert.AlertType.WARNING, "Attention", "Please select the executable path!");
            return;
        }

        Path iconPath = textIconPath.getText().isBlank() ? null : Path.of(textIconPath.getText());

        Shortcut shortcut = new Shortcut(
                textName.getText().strip(),
                Path.of(textExecutablePath.getText().strip()),
                iconPath,
                comboCategory.getValue(),
                textFlags.getText()
        );
        try{
            service.createFileShortcut(shortcut);
            showAlert(Alert.AlertType.INFORMATION, "Success","Shortcut created successfully!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }


    }
    private final ShortcutCreatorService service =  new ShortcutCreatorService();

}

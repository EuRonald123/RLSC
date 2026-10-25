package com.rnd.controller;

import com.rnd.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

    // Este método roda sozinho assim que a janela abre!
    @FXML
    public void initialize() {
        comboCategory.getItems().addAll(Category.values());
        comboCategory.setValue(Category.UTILITY);
    }

    @FXML
    private void findExecutable() {
        System.out.println("Clicou em findExecutable!");
    }

    @FXML
    private void findIcon() {
        System.out.println("Clicou em findIcon!");
    }

    @FXML
    private void checkPreview() {
        System.out.println("Clicou em Preview!");
    }

    @FXML
    private void shortcutCreate() {
        System.out.println("Clicou em Create!");
    }
}

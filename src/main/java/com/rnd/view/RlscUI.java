package com.rnd.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

public class RlscUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rlsc-ui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("RLSC - RLinux Shortcut Creator");

        InputStream iconStream = getClass().getResourceAsStream("rlsc_icon.png");
        if (iconStream != null) {
            stage.getIcons().add(new Image(iconStream));
        }

        stage.setScene(scene);

        int minX = 500;
        int maxX = 950;
        int Y = 350;

        stage.setWidth(minX);
        stage.setHeight(Y);
        stage.setMinWidth(minX);
        stage.setMinHeight(Y);
        stage.setMaxWidth(maxX);
        stage.setMaxHeight(Y);

        stage.centerOnScreen();
        stage.show();
    }

}

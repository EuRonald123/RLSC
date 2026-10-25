package com.rnd.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RlscUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rlsc-ui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("RLSC - RLinux Shortcut Creator");
        stage.setScene(scene);
        stage.show();
    }

}

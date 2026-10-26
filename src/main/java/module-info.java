module com.rnd {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.rnd.controller to javafx.fxml, javafx.graphics;

    exports com.rnd;
    exports com.rnd.model;
    exports com.rnd.service;
    exports com.rnd.view;
}

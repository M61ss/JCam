module org.example.cameraapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.bytedeco.javacv;
    requires org.bytedeco.opencv;
    requires java.desktop;
    requires webcam.capture;
    requires javafx.swing;
    requires javafx.base;
    requires atlantafx.base;
    requires static lombok;


    opens org.cameraapi to javafx.fxml;
    exports org.cameraapi;
    exports org.cameraapi.common;
    opens org.cameraapi.common to javafx.fxml;
    exports org.cameraapi.effects;
    opens org.cameraapi.effects to javafx.fxml;
    exports org.cameraapi.controller;
    opens org.cameraapi.controller to javafx.fxml;
}
package org.cameraapi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
        int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
        System.out.println("Screen resolution: " + screenWidth + " x " + screenHeight);

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("camera-home-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        ScreenController.setScreenController(scene);
        ScreenController.addScreen("home", root);

        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/CameraIconNew.png"))));
        stage.setTitle("Camera");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
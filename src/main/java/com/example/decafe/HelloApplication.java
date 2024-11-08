package com.example.decafe;

import com.example.decafe.assets.ImageAssets;
import com.example.decafe.exception.ImageNotFoundException;
import com.example.decafe.util.ImageUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// Class used to start the JavaFX Application
public class HelloApplication extends Application {

    public static final String START_SCREEN_FXML = "startScreen.fxml";
    private static Stage stage;

    @Override
    // Start Application by starting the Stage
    public void start(Stage stage) throws IOException, ImageNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(START_SCREEN_FXML));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.setStage(stage);
        stage.getIcons().add(ImageUtil.getImageFromResources(ImageAssets.MUG_TAB_PIC.getImage()));
        stage.setTitle("DeCafé");
        HelloApplication.getStage().setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        HelloApplication.stage = stage;
    }
}
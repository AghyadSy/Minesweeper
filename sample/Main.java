package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
public static Stage windows;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        windows=primaryStage;
        windows.setTitle("Minesweeper");
        windows.setScene(new Scene(root));
        windows.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

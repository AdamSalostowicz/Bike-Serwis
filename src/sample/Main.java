package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.awt.*;

public class Main extends Application {
    public double width;
    public double height;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Region root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Twój magazyn");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getWidth();
        FileInputStream fileInputStream = new FileInputStream("Graphic/cinelli.jpg");
        Image image = new Image(fileInputStream);
        BackgroundImage bgi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(width,height, true,true,true,true));
        Background background = new Background(bgi);
        root.setBackground(background);
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

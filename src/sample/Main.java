package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.awt.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;

public class Main extends Application {
    public double width;
    public double height;
    private static FileInputStream in;
    static Connection conn = null;

    @Override
    public void init() throws IOException {
        Properties prop;
        prop = new Properties();
        in = new FileInputStream("src/sample/conf.properties");
        prop.load(in);
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            System.out.println(prop.getProperty("user"));
            conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
            System.out.println("Connected to the Postgresql server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


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

    @Override
    public void stop() throws SQLException {
        conn.close();
    }

}

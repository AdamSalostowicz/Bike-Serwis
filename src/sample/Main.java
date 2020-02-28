package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.awt.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import java.sql.Connection;

public class Main extends Application {
    static double width;
    static double height;
    private static FileInputStream in;
    static Connection conn = null;
    static int number = 1;
    static Dimension screenSize;
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
        System.out.println(LocalDate.now());
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Region root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Twój magazyn");
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getWidth();
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

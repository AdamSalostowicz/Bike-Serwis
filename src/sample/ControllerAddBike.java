package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import javax.swing.table.TableColumn;
import java.net.URL;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import static sample.Main.conn;

public class ControllerAddBike implements Initializable {

    @FXML
    public ChoiceBox choicebox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Object> serviceItems = new ArrayList<>();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<String> listService = new ArrayList<>();
        Statement stmt1 = null;
        ResultSet resultSet = null;
        int i =0;
        try {
            stmt1 = conn.createStatement();
            resultSet = stmt1.executeQuery("SELECT * FROM service");
            while (resultSet.next()){
                observableList.add(resultSet.getString(2));
                listService.add(String.valueOf(resultSet.getInt(1)));
                listService.add(resultSet.getString(2));
                listService.add(resultSet.getString(3));
                choicebox.getItems().add(resultSet.getString(2) + " - " + resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

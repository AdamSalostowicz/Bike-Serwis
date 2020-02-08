package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import static sample.Main.conn;

public class ControllerAddBike implements Initializable {

    @FXML public TableView<Serwis> tableView;
    @FXML public ChoiceBox choicebox;
    @FXML public TableColumn<Serwis, String> column1;
    @FXML public TableColumn<Serwis, Double> column2;

    public Object service = null;
    ObservableList<Serwis> observableList = FXCollections.observableArrayList();;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Statement stmt1 = null;
        ResultSet resultSet = null;
        int i =0;
        try {
            stmt1 = conn.createStatement();
            resultSet = stmt1.executeQuery("SELECT * FROM service");
            while (resultSet.next()){
                choicebox.getItems().add(resultSet.getString(1) + " " + resultSet.getString(2) + " - " + resultSet.getString(3) + " PLN");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void addRow() throws SQLException {
        if (choicebox.getValue() != null) {
            service = choicebox.getValue();
            String serviceBis = String.valueOf(service);
            String row = "";

            for (int i = 0; i < 2; i++) {
                if (Character.isDigit(serviceBis.charAt(i)))
                    row += serviceBis.charAt(i);
            }
            PreparedStatement preparedStatement = conn.prepareStatement("select service_name, service_price from service where service_id = ?;");
            preparedStatement.setInt(1, Integer.parseInt(row));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                observableList.add(new Serwis(resultSet.getString(1), resultSet.getDouble(2)));
            }
            column1.setCellValueFactory(
                    new PropertyValueFactory<Serwis, String>("name"));
            column2.setCellValueFactory(
                    new PropertyValueFactory<Serwis, Double>("price"));
            tableView.setItems(observableList);
        }
    }
}

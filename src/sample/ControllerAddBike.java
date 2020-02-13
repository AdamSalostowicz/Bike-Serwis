package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.Collection;
import java.util.ResourceBundle;
import static sample.Main.conn;

public class ControllerAddBike implements Initializable {

    @FXML public TableView<Serwis> tableView;
    @FXML public ChoiceBox choicebox;
    @FXML public TableColumn<Serwis, String> column1;
    @FXML public TableColumn<Serwis, Double> column2;

    public Object service = null;
    public double sum = 0;
    ObservableList<Serwis> observableList = FXCollections.observableArrayList();;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableList.add(new Serwis("Suma", sum));
        column1.setCellValueFactory(
                new PropertyValueFactory<Serwis, String>("name"));
        column2.setCellValueFactory(
                new PropertyValueFactory<Serwis, Double>("price"));
        tableView.setItems(observableList);
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
            observableList.remove(tableView.getItems().size() - 1);
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
                sum += resultSet.getDouble(2);
            }
            observableList.add(new Serwis("Suma", sum));
            tableView.setItems(observableList);
        }
    }
    @FXML
    public void deleteRow(ActionEvent actionEvent) {
        if ((tableView.getSelectionModel().getFocusedIndex() != tableView.getItems().size() - 1)){
            sum -= tableView.getSelectionModel().getSelectedItem().getPrice();
            observableList.remove(tableView.getItems().size() - 1);
            observableList.add(new Serwis("Suma", sum));
            observableList.remove(tableView.getSelectionModel().getFocusedIndex());
        }
    }
    @FXML
    public void addBike(ActionEvent actionEvent) {
//        new mainService();
    }
}

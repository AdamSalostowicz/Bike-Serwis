package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import static sample.Main.conn;
import static sample.Controller.observableListBikes;

public class ControllerAddBike implements Initializable {

    @FXML public TableView<Serwis> tableView;
    @FXML public ChoiceBox choicebox;
    @FXML public TableColumn<Serwis, String> column1;
    @FXML public TableColumn<Serwis, Double> column2;

    @FXML private TextArea description;
    @FXML private DatePicker datePicker;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML public TextField bikeName;
    @FXML private TextField phoneNumber;
    public String bikeModel;
    public String bikeDescription;
    public String customerName;
    public String customerLastName;
    public String customerPhoneNumber;
    public String dateAcceptance;
    public String dateFixed;

    public Object service = null;
    public double sum = 0;
    ObservableList<Serwis> observableList = FXCollections.observableArrayList();;
    public Statement stmt1 = null;
    public ResultSet resultSet = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableList.add(new Serwis("Suma", sum));
        column1.setCellValueFactory(
                new PropertyValueFactory<Serwis, String>("name"));
        column2.setCellValueFactory(
                new PropertyValueFactory<Serwis, Double>("price"));
        tableView.setItems(observableList);
        stmt1 = null;
        int i =0;
        setChoicebox();
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
    public void addBike(ActionEvent actionEvent) throws SQLException {  // Insert date into Database
        if (bikeName.getText().isEmpty() == false & firstName.getText().isEmpty() == false & lastName.getText().isEmpty() == false & phoneNumber.getText().isEmpty() == false & datePicker.getValue() != null & observableList.size() > 1) {
            int key1 = 0, key2 = 0, key3 = 0, key4 = 0;  // Max Values of ID from tables - this is last insert data into table
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO bike VALUES (default , ?, ?)");
            preparedStatement.setString(1, bikeName.getText());
            preparedStatement.setString(2, description.getText());
            preparedStatement.execute();
            resultSet = stmt1.executeQuery("SELECT max(bike_id) from bike");
            while (resultSet.next()) {
                key1 = resultSet.getInt(1);
            }
            preparedStatement = conn.prepareStatement("INSERT INTO customer VALUES (default , ?, ?, ?)");
            preparedStatement.setString(1, firstName.getText());
            preparedStatement.setString(2, lastName.getText());
            preparedStatement.setString(3, phoneNumber.getText());
            preparedStatement.execute();
            resultSet = stmt1.executeQuery("SELECT max(customer_id) from customer");
            while (resultSet.next()) {
                key2 = resultSet.getInt(1);
            }
            preparedStatement = conn.prepareStatement("INSERT INTO date VALUES (default, ?, ?, null )");
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.setDate(2, Date.valueOf(datePicker.getValue()));
            preparedStatement.execute();
            resultSet = stmt1.executeQuery("SELECT max(date_id) from date");
            while (resultSet.next()) {
                key3 = resultSet.getInt(1);

            }
            preparedStatement = conn.prepareStatement("INSERT INTO orders VALUES (default, ?)");
            preparedStatement.setString(1, setOrderDetails());
            preparedStatement.execute();
            resultSet = stmt1.executeQuery("SELECT max(orders_id) from orders");
            while (resultSet.next()){
                key4 = resultSet.getInt(1);
            }
            preparedStatement = conn.prepareStatement("INSERT INTO main VALUES (?, ?, ?, ?)"); //  Insert data into table main
            preparedStatement.setInt(1, key2);
            preparedStatement.setInt(2, key1);
            preparedStatement.setInt(3, key3);
            preparedStatement.setInt(4, key4);
            preparedStatement.execute();
            System.out.println("Dodano");
            observableListBikes.add(new TableBike(bikeName.getText(), String.valueOf(LocalDate.now()), String.valueOf(datePicker.getValue())));
            clearAllControllers();
        }else{
            System.out.println("NIe dodano");
        }
    }
    public String setOrderDetails() throws SQLException {
        char[] serviceList = new char[74];
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.fill(serviceList, '0');
        for (int i = 0; i < observableList.size() - 1; i++) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT service_id FROM service where service_name = ?");
            preparedStatement.setString(1,observableList.get(i).getName());
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                serviceList[resultSet.getInt(1) - 1] = '1';
            }
        }
        for (int i = 0; i < serviceList.length; i++) {
            stringBuilder.append(serviceList[i]);
        }
        return stringBuilder.toString();
    }
    public void clearAllControllers(){
        observableList.clear();
        sum = 0;
        observableList.add(new Serwis("Suma", sum));
        description.clear();
        firstName.clear();
        lastName.clear();
        bikeName.clear();
        phoneNumber.clear();
        datePicker.getEditor().clear();
    }

    public void setChoicebox() {
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
}

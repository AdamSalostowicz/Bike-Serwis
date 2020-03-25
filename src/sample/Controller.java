package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ResourceBundle;

import static sample.Main.conn;
public class Controller implements Initializable {

    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label month1;
    @FXML
    private Label month2;
    @FXML
    private Label month3;
    @FXML
    private Label month4;
    @FXML
    private Label month5;
    @FXML
    private Label month6;
    @FXML
    private GridPane fullGrid;
    @FXML
    private TableView<TableBike> tableRoweryNaStanie;
    @FXML
    private TableColumn<TableBike, String> col1;
    @FXML
    private TableColumn<TableBike, String> col2;
    @FXML
    private TableColumn<TableBike, String> col3;
    @FXML
    private TabPane tabPane;
    @FXML
    private GridPane mainGrid;

    private int week = 0;
    private String[] polishMonths = {"styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"};
    private Stage stage1 = new Stage();
    private Stage stage2 = new Stage();
    private Stage stage3 = new Stage();
    private Stage stage4 = new Stage();
    static ObservableList<TableBike> observableListBikes = FXCollections.observableArrayList();
    public Statement stmt1 = null;
    public ResultSet resultSet = null;
    public VBox vBox;
    private Label rower;
    private ObservableList<Node> rowery = FXCollections.observableArrayList();
    @FXML
    private void addBike() throws IOException {
        closeOtherWindwos();
        Parent root = FXMLLoader.load(getClass().getResource("addBike.fxml"));
        stage1.setTitle("Dodawanie nowego roweru");
        stage1.setScene(new Scene(root, 1200, 400));
        stage1.show();
    }

    @FXML
    private void backBike() throws IOException {
        closeOtherWindwos();
        Parent root = FXMLLoader.load(getClass().getResource("getBackBike.fxml"));
        stage2.setTitle("Zwrot roweru");
        stage2.setScene(new Scene(root, 400, 400));
        stage2.show();
    }

    @FXML
    private void  archive() throws IOException {
        closeOtherWindwos();
        Parent root = FXMLLoader.load(getClass().getResource("archive.fxml"));
        stage3.setTitle("Archiwum");
        stage3.setScene(new Scene(root, 400, 400));
        stage3.show();
    }


    @FXML
    private void  reservation() throws IOException {
        closeOtherWindwos();
        Parent root = FXMLLoader.load(getClass().getResource("archive.fxml"));
        stage4.setTitle("Rezerwacja");
        stage4.setScene(new Scene(root, 400, 400));
        stage4.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCurrentDate();
        month();
        try {
            startTableRoweryNaStanie();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            showBikesInCurrentWeek();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    private void showBikesInCurrentWeek() throws IOException, SQLException {
        clearGridPane();
        byte[][] position = new byte[6][10];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                position[i][j] = 0;
            }
        }
        for (int i = 0; i < observableListBikes.size(); i++) {
            int dateFixed = Integer.valueOf(observableListBikes.get(i).getDateFixed().substring(observableListBikes.get(i).getDateFixed().lastIndexOf('-') + 1));
            int year = Integer.parseInt(observableListBikes.get(i).getDateFixed().substring(0, observableListBikes.get(i).getDateFixed().indexOf('-')));
            int month = Integer.parseInt(observableListBikes.get(i).getDateFixed().substring(observableListBikes.get(i).getDateFixed().indexOf('-') + 1, observableListBikes.get(i).getDateFixed().lastIndexOf('-')));
            if (LocalDate.of(year, month, dateFixed).isAfter(ChronoLocalDate.from(DayOfWeek.SUNDAY.adjustInto(LocalDate.now().plusWeeks(week - 1)))) &&
                    LocalDate.of(year, month, dateFixed).isBefore(ChronoLocalDate.from(DayOfWeek.SUNDAY.adjustInto(LocalDate.now().plusWeeks(week))))) {
                int columnsNumber = LocalDate.of(year, month, dateFixed).getDayOfWeek().getValue() - 1;
                int rowNumber = 1;
                if (position[rowNumber - 1][columnsNumber] == 0) {
                    position[rowNumber - 1][columnsNumber] = 1;
                } else {
                    while (position[rowNumber - 1][columnsNumber] == 1) {
                        rowNumber++;
                    }
                    position[rowNumber - 1][columnsNumber] = 1;
                }
                vBox = new VBox(2);
                vBox.setAlignment(Pos.TOP_CENTER);
                vBox.setStyle("-fx-border-radius: 5%;-fx-border-insets: 3;-fx-border-color: aliceblue; -fx-border-width: 2");
                rower = new Label(observableListBikes.get(i).getBikeName());
                rower.setStyle("-fx-text-fill: white; -fx-font-size: 18");
                PreparedStatement preparedStatement = conn.prepareStatement("select customer_phonenumber from customer inner join main on customer_id=customer_id_fkey inner join bike on bike_id=bike_id_fkey where bike_name=?");
                System.out.println(observableListBikes.get(i).getBikeName());
                preparedStatement.setString(1,observableListBikes.get(i).getBikeName());
                preparedStatement.execute();
                resultSet = preparedStatement.getResultSet();
                String numberOfPhone = null;
                while (resultSet.next()){
                    numberOfPhone = resultSet.getString(1);
                }
                preparedStatement = conn.prepareStatement("select orders_items from orders inner join main on orders_id=orders_id_fkey inner join bike on bike_id=bike_id_fkey where bike_name=?");
                preparedStatement.setString(1,observableListBikes.get(i).getBikeName());
                preparedStatement.execute();
                resultSet = preparedStatement.getResultSet();
                String orderPositions = null;
                while (resultSet.next()){
                    orderPositions = resultSet.getString(1);
                }
                Label order = new Label();
                for (int j = 0; j < orderPositions.length(); j++) {
                    if (orderPositions.charAt(j) == '1'){
                        preparedStatement = conn.prepareStatement("select service_name from service where service_id=?");
                        preparedStatement.setInt(1, j + 1);
                        preparedStatement.execute();
                        resultSet = preparedStatement.getResultSet();
                        String text = "";
                        while (resultSet.next()){
                            text = resultSet.getString(1);
                            order.setText(text);
                        }
                    }
                }
                order.setStyle("-fx-text-fill: white;-fx-font-size: 16");
                Label phone = new Label(numberOfPhone);
                phone.setStyle("-fx-text-fill: white; -fx-font-size: 16");
                Label service = new Label("Bomba poszła");
                service.setStyle("-fx-text-fill: white; -fx-font-size: 14");
                vBox.getChildren().addAll(rower, phone, order);
                fullGrid.add(vBox, columnsNumber, rowNumber);
                rowery.add(vBox);
            }
        }
    }

    @FXML
    private void setDifferentBackgrund() throws FileNotFoundException {
        String tapeta = null;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getWidth();
        switch (tabPane.getSelectionModel().getSelectedIndex()) {
            case 0 : tapeta = "Graphic/cinelli.jpg";break;
            case 1 : tapeta = "Graphic/citybike.jpg";break;
            case 2 : tapeta = "Graphic/chain.jpg";break;
        }
        FileInputStream fileInputStream1 = new FileInputStream(tapeta);
        Image image = new Image(fileInputStream1);
        BackgroundImage bgi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(width,height, true,true,true,true));
        Background background = new Background(bgi);
        mainGrid.setBackground(background);
    }


    private void startTableRoweryNaStanie() throws SQLException {
        String bikeName = null, dateAcceptance = null, dateFixed = null, dateReleased = "";
        stmt1 = conn.createStatement();
        resultSet = stmt1.executeQuery("select bike_name, date_acceptance, date_fixed,date_released from bike inner join main on bike_id=bike_id_fkey inner join date on date_id=date_id_fkey");
        while (resultSet.next()){
            bikeName = resultSet.getString(1);
            dateAcceptance = resultSet.getString(2);
            dateFixed = resultSet.getString(3);
            dateReleased = resultSet.getString(4);
//            System.out.println(bikeName + " " + dateAcceptance + " " + dateFixed + " +" + dateReleased + "+");
            if (dateReleased == null){
                observableListBikes.add(new TableBike(bikeName, dateAcceptance, dateFixed));
//                System.out.println(observableListBikes.get(observableListBikes.size() - 1).getBikeName());
            }
            dateReleased = "";
        }
        col1.setCellValueFactory(
                new PropertyValueFactory<TableBike, String>("bikeName"));
        col2.setCellValueFactory(
                new PropertyValueFactory<TableBike, String>("dateAcceptance"));
        col3.setCellValueFactory(
                new PropertyValueFactory<TableBike, String>("dateFixed"));
        tableRoweryNaStanie.setItems(observableListBikes);
    }

    private void month(){
        String polishMonth = polishMonths[LocalDate.now().getMonth().getValue() - 1];
        month1.setText(polishMonth);
        month2.setText(polishMonth);
        month3.setText(polishMonth);
        month4.setText(polishMonth);
        month5.setText(polishMonth);
        month6.setText(polishMonth);
    }
    private void showCurrentDate(){
        label1.setText(String.valueOf(LocalDate.from(DayOfWeek.MONDAY.adjustInto(LocalDate.now())).getDayOfMonth()));
        label2.setText(String.valueOf(LocalDate.from(DayOfWeek.TUESDAY.adjustInto(LocalDate.now())).getDayOfMonth()));
        label3.setText(String.valueOf(LocalDate.from(DayOfWeek.WEDNESDAY.adjustInto(LocalDate.now())).getDayOfMonth()));
        label4.setText(String.valueOf(LocalDate.from(DayOfWeek.THURSDAY.adjustInto(LocalDate.now())).getDayOfMonth()));
        label5.setText(String.valueOf(LocalDate.from(DayOfWeek.FRIDAY.adjustInto(LocalDate.now())).getDayOfMonth()));
        label6.setText(String.valueOf(LocalDate.from(DayOfWeek.SATURDAY.adjustInto(LocalDate.now())).getDayOfMonth()));
    }
    @FXML
    private void showNextWeekEvent() throws IOException, SQLException {
        week++;
        label1.setText(String.valueOf(LocalDate.from(DayOfWeek.MONDAY.adjustInto(LocalDate.now().plusWeeks(week))).getDayOfMonth()));
        month1.setText(polishMonths[LocalDate.from(DayOfWeek.MONDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label2.setText(String.valueOf(LocalDate.from(DayOfWeek.TUESDAY.adjustInto(LocalDate.now().plusWeeks(week))).getDayOfMonth()));
        month2.setText(polishMonths[LocalDate.from(DayOfWeek.TUESDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label3.setText(String.valueOf(LocalDate.from(DayOfWeek.WEDNESDAY.adjustInto(LocalDate.now().plusWeeks(week))).getDayOfMonth()));
        month3.setText(polishMonths[LocalDate.from(DayOfWeek.WEDNESDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label4.setText(String.valueOf(LocalDate.from(DayOfWeek.THURSDAY.adjustInto(LocalDate.now().plusWeeks(week))).getDayOfMonth()));
        month4.setText(polishMonths[LocalDate.from(DayOfWeek.THURSDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label5.setText(String.valueOf(LocalDate.from(DayOfWeek.FRIDAY.adjustInto(LocalDate.now().plusWeeks(week))).getDayOfMonth()));
        month5.setText(polishMonths[LocalDate.from(DayOfWeek.FRIDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label6.setText(String.valueOf(LocalDate.from(DayOfWeek.SATURDAY.adjustInto(LocalDate.now().plusWeeks(week))).getDayOfMonth()));
        month6.setText(polishMonths[LocalDate.from(DayOfWeek.SATURDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        showBikesInCurrentWeek();
    }

    @FXML
    private void showBackWeekEvent() throws IOException, SQLException {
        week--;
        label1.setText(String.valueOf(LocalDate.from(DayOfWeek.MONDAY.adjustInto(LocalDate.now().plusWeeks(week))).getDayOfMonth()));
        month1.setText(polishMonths[LocalDate.from(DayOfWeek.MONDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label2.setText(String.valueOf(LocalDate.from(DayOfWeek.TUESDAY.adjustInto(LocalDate.now().plusWeeks(week))).getDayOfMonth()));
        month2.setText(polishMonths[LocalDate.from(DayOfWeek.TUESDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label3.setText(String.valueOf(LocalDate.from(DayOfWeek.WEDNESDAY.adjustInto(LocalDate.now().plusWeeks(week))).getDayOfMonth()));
        month3.setText(polishMonths[LocalDate.from(DayOfWeek.WEDNESDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label4.setText(String.valueOf(LocalDate.from(DayOfWeek.THURSDAY.adjustInto(LocalDate.now().plusWeeks(week))).getDayOfMonth()));
        month4.setText(polishMonths[LocalDate.from(DayOfWeek.THURSDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label5.setText(String.valueOf(LocalDate.from(DayOfWeek.FRIDAY.adjustInto(LocalDate.now().plusWeeks(week))).getDayOfMonth()));
        month5.setText(polishMonths[LocalDate.from(DayOfWeek.FRIDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label6.setText(String.valueOf(LocalDate.from(DayOfWeek.SATURDAY.adjustInto(LocalDate.now().plusWeeks(week))).getDayOfMonth()));
        month6.setText(polishMonths[LocalDate.from(DayOfWeek.SATURDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        showBikesInCurrentWeek();
    }
    private void closeOtherWindwos(){
        stage1.close();
        stage2.close();
        stage3.close();
        stage4.close();
    }
    private void clearGridPane(){   // This feature removes unmatched items from the week displayed.
        for (int i = 0; i < rowery.size(); i++) {
            fullGrid.getChildren().remove(rowery.get(i));
        }
    }
}

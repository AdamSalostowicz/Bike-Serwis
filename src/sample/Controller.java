package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


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
    private Label day1;
    @FXML
    private Label day2;
    @FXML
    private Label day3;
    @FXML
    private Label day4;
    @FXML
    private Label day5;
    @FXML
    private Label day6;
    @FXML
    private AnchorPane anchor1;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private AnchorPane anchor3;
    @FXML
    private AnchorPane anchor4;
    @FXML
    private AnchorPane anchor5;
    @FXML
    private AnchorPane anchor6;
    @FXML
    private Button ButtonAddBike;

    private LocalDateTime now;
    private DayOfWeek currentDayofWeek;
    private Calendar cal;
    private int week = 0;
    private String[] polishMonths = {"styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"};

    private Stage stage1 = new Stage();
    private Stage stage2 = new Stage();
    private Stage stage3 = new Stage();
    private Stage stage4 = new Stage();



    @FXML
    private void addBike() throws IOException {
        closeOtherWindwos();
        Parent root = FXMLLoader.load(getClass().getResource("addBike.fxml"));
        stage1.setTitle("Dodawanie nowego roweru");
        stage1.setScene(new Scene(root, 600, 400));
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
        cal = Calendar.getInstance();
        now = LocalDateTime.now();
        currentDayofWeek = now.getDayOfWeek();
        showCurrentDate();
//        showCurrentDay();
        dayOfWeek();
        month();
    }
    private void dayOfWeek(){

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
//    private void showCurrentDay(){
//        switch (currentDayofWeek.getValue()){
//            case 1:
//                anchor1.setStyle("-fx-border-color: white; -fx-border-style: inset");
//                label1.setText(String.valueOf(now.getDayOfMonth()));
//                break;
//            case 2:
//                anchor2.setStyle("-fx-border-color: white; -fx-border-style: inset");
//                label2.setText(String.valueOf(now.getDayOfMonth()));
//                break;
//            case 3:
//                anchor3.setStyle("-fx-border-color: white; -fx-border-style: inset");
//                label3.setText(String.valueOf(now.getDayOfMonth()));
//                break;
//            case 4:
//                anchor4.setStyle("-fx-border-color: white; -fx-border-style: inset");
//                label4.setText(String.valueOf(now.getDayOfMonth()));
//                break;
//            case 5:
//                anchor5.setStyle("-fx-border-color: white; -fx-border-style: inset");
//                label5.setText(String.valueOf(now.getDayOfMonth()));
//                break;
//            case 6:
//                anchor6.setStyle("-fx-border-color: white; -fx-border-style: inset");
//                label6.setText(String.valueOf(now.getDayOfMonth()));
//                break;
//        }
//    }
    private void showCurrentDate(){
        label1.setText(String.valueOf(LocalDate.from(DayOfWeek.MONDAY.adjustInto(LocalDate.now())).getDayOfMonth()));
        label2.setText(String.valueOf(LocalDate.from(DayOfWeek.TUESDAY.adjustInto(LocalDate.now())).getDayOfMonth()));
        label3.setText(String.valueOf(LocalDate.from(DayOfWeek.WEDNESDAY.adjustInto(LocalDate.now())).getDayOfMonth()));
        label4.setText(String.valueOf(LocalDate.from(DayOfWeek.THURSDAY.adjustInto(LocalDate.now())).getDayOfMonth()));
        label5.setText(String.valueOf(LocalDate.from(DayOfWeek.FRIDAY.adjustInto(LocalDate.now())).getDayOfMonth()));
        label6.setText(String.valueOf(LocalDate.from(DayOfWeek.SATURDAY.adjustInto(LocalDate.now())).getDayOfMonth()));
    }
    @FXML
    private void showNextWeekEvent(){
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
    }

    @FXML
    private void showBackWeekEvent(){
        week--;
        label1.setText(String.valueOf(LocalDate.from(DayOfWeek.MONDAY.adjustInto(LocalDate.now().minusWeeks(Math.abs(week)))).getDayOfMonth()));
        month1.setText(polishMonths[LocalDate.from(DayOfWeek.MONDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label2.setText(String.valueOf(LocalDate.from(DayOfWeek.TUESDAY.adjustInto(LocalDate.now().minusWeeks(Math.abs(week)))).getDayOfMonth()));
        month2.setText(polishMonths[LocalDate.from(DayOfWeek.TUESDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label3.setText(String.valueOf(LocalDate.from(DayOfWeek.WEDNESDAY.adjustInto(LocalDate.now().minusWeeks(Math.abs(week)))).getDayOfMonth()));
        month3.setText(polishMonths[LocalDate.from(DayOfWeek.WEDNESDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label4.setText(String.valueOf(LocalDate.from(DayOfWeek.THURSDAY.adjustInto(LocalDate.now().minusWeeks(Math.abs(week)))).getDayOfMonth()));
        month4.setText(polishMonths[LocalDate.from(DayOfWeek.THURSDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label5.setText(String.valueOf(LocalDate.from(DayOfWeek.FRIDAY.adjustInto(LocalDate.now().minusWeeks(Math.abs(week)))).getDayOfMonth()));
        month5.setText(polishMonths[LocalDate.from(DayOfWeek.FRIDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
        label6.setText(String.valueOf(LocalDate.from(DayOfWeek.SATURDAY.adjustInto(LocalDate.now().minusWeeks(Math.abs(week)))).getDayOfMonth()));
        month6.setText(polishMonths[LocalDate.from(DayOfWeek.SATURDAY.adjustInto(LocalDate.now().plusWeeks(week))).getMonthValue() - 1]);
    }
    private void closeOtherWindwos(){
        stage1.close();
        stage2.close();
        stage3.close();
        stage4.close();
    }
}

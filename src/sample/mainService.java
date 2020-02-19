package sample;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

import java.awt.*;
import java.time.LocalDate;

public class mainService {
    public String bikeModel;
    public String bikeDescription;
    public String customerName;
    public String customerLastName;
    public String customerPhoneNumber;
    public LocalDate dateAcceptance;
    public LocalDate dateReleased;

    @FXML private TableView tableView;
    @FXML private TextArea description;
    @FXML private DatePicker datePicker;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML public TextField bikeName;
    @FXML private TextField phoneNumber;

    mainService() {
//        dateAcceptance = LocalDate.now();
//        setBikeModel(bikeName.getText());
//        setBikeDescription(description.getText());
//        setCustomerName(firstName.getText());
//        setCustomerLastName(lastName.getText());
//        setCustomerPhoneNumber(phoneNumber.getText());
//        setDateReleased(datePicker.getValue());
//        System.out.println(LocalDateTime.now()+ " " +
//                bikeName.getText() + " " +
//                description.getText() + " " +
//                firstName.getText() + " " +
//                lastName.getText() + " " +
//                phoneNumber.getText() + " " +
//                datePicker.getValue());
//        firstName.getText();
//        System.out.println(bikeName.getText());
    }

    public String getBikeModel() { return bikeModel;}

    public String getBikeDescription() {return bikeDescription;}

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public LocalDate getDateAcceptance() {
        return dateAcceptance;
    }

    public LocalDate getDateReleased() {
        return dateReleased;
    }

    public void setBikeModel(String bikeModel) {
        this.bikeModel = bikeModel;
    }

    public void setBikeDescription(String bikeDescription) {
        this.bikeDescription = bikeDescription;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public void setDateAcceptance(LocalDate dateAcceptance) {
        this.dateAcceptance = dateAcceptance;
    }

    public void setDateReleased(LocalDate dateReleased) {
        this.dateReleased = dateReleased;
    }
}

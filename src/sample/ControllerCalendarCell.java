package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Controller.gridPane;
import static sample.Main.number;
public class ControllerCalendarCell implements Initializable {

    @FXML
    private Label labelInCell;
    @FXML
    private GridPane gridPaneInCell;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (number%8 == 1) labelInCell.setText("10");
        else if(number%8 == 2) labelInCell.setText("11");
        else if(number%8 == 3) labelInCell.setText("12");
        else if(number%8 == 4) labelInCell.setText("13");
        else if(number%8 == 5) labelInCell.setText("14");
        else if(number%8 == 6) labelInCell.setText("15");
        else if(number%8 == 7) labelInCell.setText("16");
        else if(number%8 == 0) labelInCell.setText("17");
        number++;
        showBikesOnStock();
    }

    private void showBikesOnStock() {
        
    }
}

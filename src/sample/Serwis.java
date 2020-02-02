package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Serwis {
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty price;

    public Serwis(String tName, Double tPrice){
        this.name = new SimpleStringProperty(tName);
        this.price = new SimpleDoubleProperty(tPrice);
    }
    public String getName() {
        return name.get();
    }

    public void setName(String tName) {
        name.set(tName);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double tPrice) {
        price.set(tPrice);
    }
}

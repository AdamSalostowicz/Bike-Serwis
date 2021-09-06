package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Serwis {
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty price;

    public Serwis(String fName, Double fPrice){
        this.name = new SimpleStringProperty(fName);
        this.price = new SimpleDoubleProperty(fPrice);
    }
    public String getName(){
        return name.get();
    }
    public void setName(String fName){
        name.set(fName);
    }
    public Double getPrice(){
        return price.get();
    }
    public void setPrice(Double fPrice){
        price.set(fPrice);
    }
}

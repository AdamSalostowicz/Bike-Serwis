package sample;

import javafx.beans.property.SimpleStringProperty;

public class TableBike {
    private final SimpleStringProperty bikeName;
    private final SimpleStringProperty dateAcceptance;
    private final SimpleStringProperty dateFixed;
    public TableBike(String fBikeName, String fDateAcceptance, String fDateFixed) {
        this.bikeName = new SimpleStringProperty(fBikeName);
        this.dateAcceptance = new SimpleStringProperty(fDateAcceptance);
        this.dateFixed = new SimpleStringProperty(fDateFixed);
    }
    public String getBikeName(){
        return bikeName.get();
    }
    public void setBikeName(String fBikeName){
        bikeName.set(fBikeName);
    }
    public String getDateAcceptance(){
        return dateAcceptance.get();
    }
    public void setDateAcceptance(String fDateAcceptance){
        dateAcceptance.set(fDateAcceptance);
    }
    public String getDateFixed(){
        return dateFixed.get();
    }
    public void setDateFixed(String fDateFixed){
        dateFixed.set(fDateFixed);
    }
}

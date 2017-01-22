package sample.objects;

import javafx.beans.property.SimpleStringProperty;

public class Columns {
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty date = new SimpleStringProperty("");
    private SimpleStringProperty type = new SimpleStringProperty("");
    private SimpleStringProperty size = new SimpleStringProperty("");

    public Columns(String name, String date, String type, String size) {
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
        this.type = new SimpleStringProperty(type);
        this.size = new SimpleStringProperty(size);
    }

    public Columns() {
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getSize() {
        return size.get();
    }

    public SimpleStringProperty sizeProperty() {
        return size;
    }

    public void setSize(String size) {
        this.size.set(size);
    }
}

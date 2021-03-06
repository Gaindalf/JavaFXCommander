package sample.objects;

import javafx.beans.property.SimpleStringProperty;

public class SearchData {
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty path = new SimpleStringProperty("");

    public SearchData(String name, String path) {
        this.name = new SimpleStringProperty(name);
        this.path = new SimpleStringProperty(path);
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

    public String getPath() {
        return path.get();
    }

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public void setPath(String path) {
        this.path.set(path);
    }
}

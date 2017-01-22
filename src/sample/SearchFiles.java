package sample;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.objects.SearchData;

import java.io.File;
import java.nio.file.Paths;

import static java.nio.file.Files.isRegularFile;

public class SearchFiles {
    private ObservableList<SearchData> searchedFiles = FXCollections.observableArrayList();
    boolean searchOn = true;
    SearchData searchData;
    String name;
    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSearchOn(boolean searchOn) {
        this.searchOn = searchOn;
    }

    public ObservableList<SearchData> getSearchedFiles() {
        return searchedFiles;
    }

    public void setSearchedFiles(ObservableList<SearchData> searchedFiles) {
        this.searchedFiles = searchedFiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(SearchData searchData) {

    }

    @FXML
    private Button closeButton;
    @FXML
    private Button stopSearch;
    @FXML
    private TableView table;
    @FXML
    private Label labelFiles;
    @FXML
    private TableColumn<SearchData, String> txtName;
    @FXML
    private TableColumn<SearchData, String> txtPath;
    @FXML
    private Label label;
    @FXML
    private Label labelFoundedFiles;

    int a = 0;

    public void searchFiles(String path, String name) {
        if (a < 5000) {
            setName(name);
            boolean flag = true;
            File f = new File(path);
            String[] listOfFiles = f.list();
            try {
                for (String file : listOfFiles) {
                    a++;
                    setCount(a);
                    System.out.println(a);
                    try{
                    AnimationTimer at = new AnimationTimer() {
                        int fps = 0;
                        @Override
                        public void handle(long now) {
                            fps++;
                            if(fps>60){
                                labelFiles.setText("" + a);
                                labelFoundedFiles.setText("" + searchedFiles.size());
                                fps = 0;
                                this.stop();
                            }
                        }
                    };
                    at.start();
                    }
                    catch (NullPointerException e){
                        System.out.println(e);
                    }
                    if (file.contains(name)) {
                        System.out.println(path + name + "file found");
                        if (isRegularFile(Paths.get(path + file))) {
                            searchedFiles.add(new SearchData(file, path));
                        }
                    } else {
                    }
                    File tempfile = new File(path + file);
                    if (!file.equals(".") && !file.equals("..")) {
                        if (tempfile.isDirectory()) {
                            searchFiles(path + file + "\\", name);
                        }
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("Here : " + e);
            }
        }
    }

    public void setFoundedFiles(SearchData searchData) {
        if (searchData == null) {
            return;
        }
        this.searchData = searchData;
        table.setItems(getSearchedFiles());
        for (SearchData st : searchedFiles) {
            st.getName();
            st.getPath();
        }
    }

    @FXML
    private void initialize() {
        txtName.setCellValueFactory(new PropertyValueFactory<SearchData, String>("name"));
        txtPath.setCellValueFactory(new PropertyValueFactory<SearchData, String>("path"));
        table.setItems(getSearchedFiles());
    }

    public void actionStopSearch(ActionEvent actionEvent){
        searchOn = false;
    }

    public void actionCloseSearch(ActionEvent actionEvent){
        searchOn = false;
        a = 0;
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void deleteAll() {
        searchedFiles.clear();
    }
}


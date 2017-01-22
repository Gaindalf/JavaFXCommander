package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.intarface.impl.TableBookImpl;
import sample.objects.ChoiseBox;
import sample.objects.Columns;
import sample.objects.SearchData;

import java.io.*;

public class Controller {
    TableBookImpl tableBookImpl = new TableBookImpl();
    ChoiseBox choiseBox = new ChoiseBox();
    private EditControl editControl;
    private SearchFiles searchControl;
    private Stage editDialogStage;
    private Stage searchDialogStage;
    private Stage mainStage;
    private Parent fxmlEdit;
    private Parent fxmlSearch;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private FXMLLoader fxmlSearchLoader = new FXMLLoader();
    private String copeFileName;
    private String copyFileSourceFolder;
    private String copyFileDestFolder;
    Thread t2;
    Thread t3;
    boolean threadT2 = false;
    boolean threadT3 = false;

    public String getCopyFileSourceFolder() {
        return copyFileSourceFolder;
    }

    public void setCopyFileSourceFolder(String copyFileSourceFolder) {
        this.copyFileSourceFolder = copyFileSourceFolder;
    }

    public String getCopyFileDestFolder() {
        return copyFileDestFolder;
    }

    public void setCopyFileDestFolder(String copyFileDestFolder) {
        this.copyFileDestFolder = copyFileDestFolder;
    }

    public String getCopeFileName() {
        return copeFileName;
    }

    public void setCopeFileName(String copeFileName) {
        this.copeFileName = copeFileName;
    }

    @FXML
    private TableView table;
    @FXML
    private TableColumn<Columns, String> txtName;
    @FXML
    private TableColumn<Columns, String> txtDate;
    @FXML
    private TableColumn<Columns, String> txtType;
    @FXML
    private TableColumn<Columns, String> txtSize;
    @FXML
    private Button upButton;
    @FXML
    private Button goToDisc;
    @FXML
    private Label labelCount;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField searchTextField;

    @FXML
    private void initialize() {
        txtName.setCellValueFactory(new PropertyValueFactory<Columns, String>("name"));
        txtDate.setCellValueFactory(new PropertyValueFactory<Columns, String>("date"));
        txtType.setCellValueFactory(new PropertyValueFactory<Columns, String>("type"));
        txtSize.setCellValueFactory(new PropertyValueFactory<Columns, String>("size"));
        initListeners();
        tableBookImpl.fillData();
        table.setItems(tableBookImpl.getColumnList());
        updatePach();
        choiseBox.addDiscs();
        choiceBox.setItems(choiseBox.getDisc());
        initLoader();
        initSearchLoader();
    }

    private void initListeners() {
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        if (event.getClickCount() == 2 && txtType.getCellData(table.getSelectionModel().getFocusedIndex()) == "Dir") {
                                            System.out.println(tableBookImpl.getTxtPath());
                                            tableBookImpl.setTxtPath(tableBookImpl.getTxtPath() + txtName.getCellData(table.getSelectionModel().getFocusedIndex()) + "\\");
                                            tableBookImpl.deleteAll();
                                            tableBookImpl.fillData();
                                            table.setItems(tableBookImpl.getColumnList());
                                            updatePach();
                                        }
                                    }
                                }
        );
    }

    public void actionButtonPressed(ActionEvent actionEvent) throws InterruptedException {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        Button clickedButton = (Button) source;
        switch (clickedButton.getId()) {
            case "upButton":
                String a = tableBookImpl.getTxtPath();
                int leghtOfA = tableBookImpl.getTxtPath().length();
                String b = "";
                if (a.substring(0, a.length() - 1).contains("\\")) {
                    b = a.substring(0, a.length() - 1);
                    a = b;
                    for (int i = 0; i < leghtOfA - 1; i++) {
                        if (a.charAt(leghtOfA - 2 - i) != '\\') {
                            b = a.substring(0, a.length() - 1);
                            a = b;
                        } else {
                            break;
                        }
                    }
                    System.out.println("Here" + b);
                    tableBookImpl.setTxtPath(b);
                    System.out.println(tableBookImpl.getTxtPath());
                    tableBookImpl.deleteAll();
                    tableBookImpl.fillData();
                    table.setItems(tableBookImpl.getColumnList());
                    updatePach();
                }
                break;
            case "goToDisc":
                String disc = choiceBox.getValue();
                System.out.println(disc);
                tableBookImpl.setTxtPath(disc);
                tableBookImpl.deleteAll();
                tableBookImpl.fillData();
                table.setItems(tableBookImpl.getColumnList());
                updatePach();
                break;
            case "deleteButton":
                tableBookImpl.delete((Columns) table.getSelectionModel().getSelectedItem());
                System.out.println(txtName.getCellData(table.getSelectionModel().getFocusedIndex()));
                String nameOfDeleteFile = tableBookImpl.getTxtPath() + txtName.getCellData(table.getSelectionModel().getFocusedIndex());
                new File(nameOfDeleteFile).delete();
                tableBookImpl.deleteAll();
                tableBookImpl.fillData();
                table.setItems(tableBookImpl.getColumnList());
                updatePach();
                break;
            case "createFolder":
                String nameOfNewDir = tableBookImpl.getTxtPath();
                File dir = new File(nameOfNewDir + "NewFolder");
                if (false == dir.exists()) {
                    dir.mkdir();
                } else {
                    for (int i = 0; i < 100; i++) {
                        File newDir = new File(nameOfNewDir + "NewFolder(" + i + ")");
                        if (false == newDir.exists()) {
                            newDir.mkdir();
                            break;
                        } else {
                            continue;
                        }
                    }
                }
                tableBookImpl.deleteAll();
                tableBookImpl.fillData();
                table.setItems(tableBookImpl.getColumnList());
                updatePach();
                break;
            case "renameFile":
                editControl.setNAme((Columns) table.getSelectionModel().getSelectedItem());
                String nameOfRenameFile = tableBookImpl.getTxtPath() + txtName.getCellData(table.getSelectionModel().getFocusedIndex());
                editControl.setNameOfRenameFile(nameOfRenameFile);
                showDialog();
                break;
            case "copyFile":
                setCopyFileSourceFolder(tableBookImpl.getTxtPath() + txtName.getCellData(table.getSelectionModel().getFocusedIndex()));
                setCopeFileName(txtName.getCellData(table.getSelectionModel().getFocusedIndex()));
                break;
            case "pasteFile":
                setCopyFileDestFolder(tableBookImpl.getTxtPath() + getCopeFileName());
                if (new File(tableBookImpl.getTxtPath()).getFreeSpace() != 0) {
                    if (!new File(getCopyFileDestFolder()).exists()) {
                        try {
                            copyFileUsingStream(getCopyFileSourceFolder(), getCopyFileDestFolder());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        tableBookImpl.deleteAll();
                        tableBookImpl.fillData();
                        table.setItems(tableBookImpl.getColumnList());
                        updatePach();
                    } else {

                        System.out.println("File already exist");
                    }
                } else {
                    System.out.println("Not enough space");
                }
                break;
            case "search":
                searchControl.deleteAll();
                t2 = new Thread(new MyNewRunnableTwo(), "t2");
                t3 = new Thread(new MyNewRunnableThree(), "t3");
                searchControl.setSearchOn(true);
                t2.start();
                threadT2 = true;
                t3.start();
                showSearchForm();
                threadT2 = false;
                break;
        }
    }

    private void updatePach() {
        labelCount.setText(tableBookImpl.getTxtPath());
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("edit.fxml"));
            fxmlEdit = fxmlLoader.load();
            editControl = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSearchLoader() {
        try {
            fxmlSearchLoader.setLocation(getClass().getResource("search.fxml"));
            fxmlSearch = fxmlSearchLoader.load();
            searchControl = fxmlSearchLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDialog() {

        if (editDialogStage == null) {
            editDialogStage = new Stage();
            editDialogStage.setTitle("Редактирование записи");
            editDialogStage.setMinHeight(150);
            editDialogStage.setMinWidth(300);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(mainStage);
        }
        editDialogStage.showAndWait();
    }

    private void showSearchForm()  throws NullPointerException{

        if (searchDialogStage == null) {
            searchDialogStage = new Stage();
            searchDialogStage.setTitle("Поиск");
            searchDialogStage.setMinHeight(400);
            searchDialogStage.setMinWidth(600);
            searchDialogStage.setResizable(false);
            searchDialogStage.setScene(new Scene(fxmlSearch));
            searchDialogStage.initModality(Modality.WINDOW_MODAL);
            searchDialogStage.initOwner(mainStage);
        }
            searchDialogStage.showAndWait();
        }

    private static void copyFileUsingStream(String source, String dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public class MyNewRunnableTwo implements Runnable {
        @Override
        public void run() {
            System.out.println("Поток начал работу:::" + Thread.currentThread().getName());
            while (threadT2) {
                try {
                    Thread.sleep(400);
                    searchControl.searchFiles(tableBookImpl.getTxtPath(), searchTextField.getText());
                    t2.join(12);
                    threadT2 = false;
                    System.out.println(t2.getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Поток отработал:::" + Thread.currentThread().getName());
            }
        }
    }

    public class MyNewRunnableThree implements Runnable {
        @Override
        public void run() {
            System.out.println("Поток начал работу:::" + Thread.currentThread().getName());
            try {
                Thread.sleep(400);
                searchControl.setFoundedFiles((SearchData) table.getSelectionModel().getSelectedItem());
                t3.join(12);
                System.out.println(t3.getState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Поток отработал:::" + Thread.currentThread().getName());
        }
    }
}

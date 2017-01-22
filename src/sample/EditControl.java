package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.intarface.impl.TableBookImpl;
import sample.objects.Columns;

import java.io.File;

public class EditControl {
    TableBookImpl tableBookImpl = new TableBookImpl();
    @FXML
    private TextField textField;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    private Columns columns;
    private String nameOfRenameFile;

    public String getNameOfRenameFile() {
        return nameOfRenameFile;
    }

    public void setNameOfRenameFile(String nameOfRenameFile) {
        this.nameOfRenameFile = nameOfRenameFile;
    }

    public void setNAme(Columns columns) {
        if (columns == null){
            return;
        }
        this.columns = columns;
        textField.setText(columns.getName());
    }


    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }


    public void actionSave(ActionEvent actionEvent) {
        File file = new File(getNameOfRenameFile());
        System.out.println(!new File(tableBookImpl.getTxtPath() + textField.getText()).exists());
        if(!new File(tableBookImpl.getTxtPath() + textField.getText()).exists()){
            file.renameTo(new File(tableBookImpl.getTxtPath() + textField.getText()));
            columns.setName(textField.getText());
            actionClose(actionEvent);
        }

    }

}

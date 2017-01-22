package sample.intarface.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.ReadFiles;
import sample.intarface.TableBook;
import sample.objects.Columns;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.*;

public class TableBookImpl implements TableBook {
    String txtPath = "D:\\Новая папка\\";
    private ObservableList<Columns> columnList = FXCollections.observableArrayList();
    ReadFiles readFiles = new ReadFiles();

    public String getTxtPath() {
        return txtPath;
    }

    public void setTxtPath(String txtPath) {
        this.txtPath = txtPath;
    }

    @Override
    public void add(Columns columns) {

    }

    @Override
    public void update(Columns columns) {

    }

    @Override
    public void delete(Columns columns) {

    }

    public void deleteAll() {
        columnList.clear();
    }

    public ObservableList<Columns> getColumnList() {
        return columnList;
    }

    public void fillData() {
        readFiles.readFolders();

        String name;
        String[] date = new String[2];
        String type = "";
        long size = 0;
        try (DirectoryStream<Path> directoryStream = newDirectoryStream(Paths.get(txtPath))) {
            for (Path path : directoryStream) {
                name = path.getFileName().toString();
                if(name.equals("Documents and Settings")){
                    continue;
                }
                if (getLastModifiedTime(path) != null) {
                    date = getLastModifiedTime(path).toString().split("T");
                }else{
                    date[0] = "0";
                }
                if (isDirectory(path)) {
                    type = "Dir";
                    size = size(path);
//                    tableBook.add(new Columns())
                }
                if (isRegularFile(path)) {
                    type = "File";
                    size = size(path);
                }
                System.out.println(name + ", " + date[0] + ", " + type + ", " + size);
                columnList.add(new Columns(name, date[0].toString(), type, Long.toString(size)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        personList.add(new Person("Роман Романов", "345345345"));
//        personList.add(new Person("Антон Иванов", "345345345"));
//        personList.add(new Person("Джон Маклейн", "23423423"));
//        personList.add(new Person("Джек Воробей", "234234"));
//        personList.add(new Person("Алиса Ивановна", "456456"));
//        personList.add(new Person("Боб Марли", "34534345"));
    }
}

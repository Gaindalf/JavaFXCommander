package sample.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.intarface.impl.TableBookImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.newDirectoryStream;

public class ChoiseBox {
    ObservableList<String> disc = FXCollections.observableArrayList();
    TableBookImpl tableBook;

    public ObservableList<String> getDisc() {
        return disc;
    }

    public void setDisc(ObservableList<String> disc) {
        this.disc = disc;
    }

    public void addDiscs() {
        for (char i = 'A'; i <= 'Z'; i++) {
            String a = i + ":\\";
            int j = 0;
            if(new File(a).exists() && new File(a).canRead()) {
                try (DirectoryStream<Path> directoryStream = newDirectoryStream(Paths.get(a))) {
                    for (Path path : directoryStream) {
                        j++;
                    }
                    System.out.println(a);
                    if (j > 0) {
                        disc.add(a);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

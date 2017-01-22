package sample;

import sample.objects.Columns;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.*;

public class ReadFiles {
    String txtPath = "C:\\";
//    TableBookImpl tableBook = new TableBookImpl();

    public Columns readFolders(){
        String name;
        String[] date = new String[2];
        String type = "";
        long size = 0;
        try (DirectoryStream<Path> directoryStream = newDirectoryStream(Paths.get(txtPath))) {
            for (Path path : directoryStream) {
                name = path.getFileName().toString();
                date = getLastModifiedTime(path).toString().split("T");
                if (isDirectory(path)) {
                    type = "Dir";
                    size = size(path);
//                    tableBook.add(new Columns())
                }
                if (isRegularFile(path)) {
                    type = "File";
                    size = size(path);
                }
//                System.out.println(name +", " + date[0] + ", " + type +", " + size);
//                tableBook.add(new Columns(name, date[0].toString(), type, Long.toString(size)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

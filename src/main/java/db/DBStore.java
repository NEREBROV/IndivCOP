package db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DBStore {
    // Данный класс предназначен для записи объектов
    // в файл базы и чтения базы из файла


    // Последовательность с объектами базы.
    // Решил использовать класс ArrayList,
    // т.к. он поддерживает Stream.filter и Stream.concat
    private List<String> objectsStrings;

    private final File dbFile;

    public DBStore(String path) throws IOException {
        dbFile = new File(path);
        this.objectsStrings = Files.lines(dbFile.toPath()).distinct().collect(Collectors.toList());
    }

    public Stream<String> getObjectsStrings() {
        return objectsStrings.stream();
    }

    public void setObjectsStrings(Stream<String> stream) {
        this.objectsStrings = stream.collect(Collectors.toList());
    }

    // Данный метод используется для подтверждения
    // изменений в базе данных и записи данных в файл
    public void commitChanges() throws IOException {
        // Абстракции для работы с файлом базы
        BufferedWriter dbFileWriter = new BufferedWriter(new FileWriter(dbFile));
        for (String line : objectsStrings) {
            dbFileWriter.write(line);
            dbFileWriter.newLine();
        }
    }
}
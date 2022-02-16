package utilities.files;

import exceptions.CodeApplException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWrite {

    public static void writeFromString(String toWrite, String dir, String fileName) {
        File file = UtilFiles.createFile(dir, fileName);
        writeFromString(toWrite, file);
    }

    public static void writeFromString(String toWrite, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(toWrite);
            fileWriter.close();
        } catch (Exception e) {
            throw new CodeApplException("Erreur d'écriture du fichier " + file.getPath());
        }
    }

    // A voir la version dans UtilFile
    public static void writeFromString(String toWrite, Path path) {
        try (BufferedWriter file = Files.newBufferedWriter(path)) {
            file.write(toWrite);
            file.close();
        } catch (IOException e) {
            throw new CodeApplException("Erreur d'écriture du fichier " + path);
        }
    }

}

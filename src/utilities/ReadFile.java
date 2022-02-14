package utilities;

import exceptions.CodeApplException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadFile {

    public static String fileToString(String dir, String fileName) {
        Path path = Paths.get(dir, fileName);
        return fileToString(path);
    }


    public static String fileToString(File file) {
        Path path = file.toPath();
        return fileToString(path);
    }

    public static String fileToString(Path path) {
        String content = "";
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (content != "") {
                    content += System.lineSeparator();
                }
                content += line;
            }
        } catch (IOException e) {
            throw new CodeApplException("Erreur de lecture du fichier" + path.toString());
        }

        return content;
    }
}

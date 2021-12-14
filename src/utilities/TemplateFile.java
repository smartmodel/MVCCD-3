package utilities;

import exceptions.CodeApplException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemplateFile {

    public static String templateFileToString(String templateDir, String templateFileName) {
        String content = "";
        Path path = Paths.get(templateDir, templateFileName);


        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (content != "") {
                    content += System.lineSeparator();
                }
                content += line;
            }
        } catch (IOException e) {
            throw new CodeApplException("Erreur de lecture du fichier template " + path.toString());
        }

        return content;
    }
}

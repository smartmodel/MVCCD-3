package utilities.files;

import exceptions.CodeApplException;
import main.MVCCDManager;
import main.MVCCDWindow;
import messages.MessagesBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.window.DialogMessage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UtilFiles {

    //TODO-1 Voir la libraire FileUtils de Apache !

    //TODO-1 Voir pourquoi cela ne fonctionne pas dans le code avec cette constante
    public final  String SEPEEXTENSION = "\\.";

    public static String getStrDirectory(File file) {
        String directory = StringUtils.removeEnd(file.getPath(), file.getName());
        directory = StringUtils.substring(directory, 0, directory.length() - 1);
        return directory;
    }

    public static File getDirectory(File file) {
        return new File(getStrDirectory(file));
    }

    public static Boolean hasExtension(String fileName, String extension) {
        if (StringUtils.isNotEmpty(fileName)  && StringUtils.isNotEmpty(extension)) {
            return extension.equals(FilenameUtils.getExtension(fileName));
        } else {
            return null;
        }
    }

    public static String getExtension(String fileName) {
        String[] parts = fileName.split("\\.");
        if (parts.length >1){
            return parts[parts.length - 1];
        }
        return null;
    }

    /**
     * Retourne un nouveau fileName avec l'extension modifiée
     * @param fileName
     * @param newExtension
     * @return
     */
    public static String changeExtension(String fileName, String newExtension){
        String newFileName = "";
        String[] parts = fileName.split("\\.");
        if (parts.length >1){
            for (int i=0; i < parts.length -1; i++ ){
                newFileName = newFileName  + parts[i];
            }
            newFileName = newFileName + "." + newExtension;
        }
        return newFileName;
    }

    public static boolean isFileProfil(String fileName) {
        if (hasExtension(fileName, Preferences.FILE_PROFILE_EXTENSION) ){
            return true;
        } else {
            return false;
        }
    }

    public static String fileName(String shortName, String extName){
         return shortName + "." + extName;
    }

    public static String filePath(String directory, String fileName){
        StringBuilder filePath = new StringBuilder(directory);

        //#MAJ 2021-04-03 Remplacé le text "path.separator" par File.separator
        //filePath.append(System.getProperty("path.separator")).append(fileName);
        filePath.append(File.separator).append(fileName);
        return filePath.toString();
    }

    public static String fileTextToString(String filePath) {
        String content = "";

        Charset charset = Charset.forName(Preferences.CHARSET_FILES_TEXT);
        Path path = Paths.get(filePath);

        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (StringUtils.isNotEmpty(content)) {
                    content = content + Preferences.SYSTEM_LINE_SEPARATOR ;
                }
                content = content + line;
            }
        } catch (IOException e) {
            throw (new CodeApplException(e));	// L'erreur est renvoyée
        }
        return content;
    }

    public static String fileTextToString(String directory, String fileName) {
        String filePath = filePath(directory, fileName);
        return fileTextToString(filePath);
    }

    public static ImageIcon getImageIcon(String dir, String fileName){
        return new ImageIcon (Preferences.DIRECTORY_RESSOURCES_NAME + Preferences.SYSTEM_FILE_SEPARATOR +
                dir + Preferences.SYSTEM_FILE_SEPARATOR + fileName);
    }

    public static boolean confirmIfExist(MVCCDWindow mvccdWindow, File fileChoose) {
        if (fileChoose.exists()){
            String message = MessagesBuilder.getMessagesProperty ("file.create.exist",fileChoose.getPath());
            return DialogMessage.showConfirmYesNo_No(
                    MVCCDManager.instance().getMvccdWindow(), message) == JOptionPane.YES_OPTION ;
        }
        return true;
    }

    public static File createFile(String directory, String fileName){
        return new File (directory + File.separator + fileName);
    }
}

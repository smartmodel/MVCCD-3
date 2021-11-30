package generatorsql;

import main.MVCCDManager;
import preferences.Preferences;
import resultat.Resultat;
import utilities.files.UtilFiles;

import java.io.File;
import java.io.FileWriter;

public class GenerateSQLUtil {


    public static File directorySQLFiles() {
        String filePathProjectCurrent = MVCCDManager.instance().getFileProjectCurrent().getPath();
        String fileNameProjectCurrent = MVCCDManager.instance().getFileProjectCurrent().getName();
        String fileShortNameProjectCurrent = UtilFiles.fileShortName(fileNameProjectCurrent);
        String directoryProjectCurrent = UtilFiles.getStrDirectory(MVCCDManager.instance().getFileProjectCurrent());

        String folderSQLFiles = UtilFiles.filePath(directoryProjectCurrent, fileShortNameProjectCurrent);
        File directory =  new File(folderSQLFiles);
        directory.mkdirs(); //Create folder if necessary
        return directory;
    }


    public static String nameSQLCreateFile() {
        return UtilFiles.fileName(Preferences.FILE_CREATE_SQL_SHORT_NAME, Preferences.FILE_SQL_DDL_EXTENSION);
    }

    public static File sqlCreateFile(){
        String folderSQLFiles = directorySQLFiles().getPath();
        String nameSQLCreateFile = nameSQLCreateFile();
        return UtilFiles.createFile(folderSQLFiles, nameSQLCreateFile);

    }

    public static Resultat generateSQLFile(String code) {
        //TODO-0 A voir le traitement d'erreur
        //TODO-1 Voir la librairie de Steve (FilesManager)
        Resultat resultat = new Resultat();
        try {
            File sqlCreateFile = sqlCreateFile();
            FileWriter fileWriter = new FileWriter(sqlCreateFile);

            fileWriter.write(code);
            fileWriter.close();

            return resultat;

        } catch (Exception e) {
            resultat.addExceptionUnhandled(e);
            return resultat;
        }
    }

}

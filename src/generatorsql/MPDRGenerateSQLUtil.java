package generatorsql;

import main.MVCCDManager;
import preferences.Preferences;
import resultat.Resultat;
import utilities.files.UtilFiles;

import java.io.File;
import java.io.FileWriter;

public class MPDRGenerateSQLUtil {


    public static File directorySQLFiles() {
        if (MVCCDManager.instance().getFileProjectCurrent() != null) {
            String filePathProjectCurrent = MVCCDManager.instance().getFileProjectCurrent().getPath();
            String fileNameProjectCurrent = MVCCDManager.instance().getFileProjectCurrent().getName();
            String fileShortNameProjectCurrent = UtilFiles.fileShortName(fileNameProjectCurrent);
            String directoryProjectCurrent = UtilFiles.getStrDirectory(MVCCDManager.instance().getFileProjectCurrent());

            String folderSQLFiles = UtilFiles.filePath(directoryProjectCurrent, fileShortNameProjectCurrent);
            File directory = new File(folderSQLFiles);
            directory.mkdirs(); //Create folder if necessary
            return directory;
        } else {
            return null;
        }
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


    public static String replaceKeyValue(String code, String key, String value) {
        //TODO-1 A voir expression régulière
        return code.replaceAll("\\{" + key + "}", value);
    }



    public static String cleanSeparatorArguments(String code) {
        String cleanCode = "";
        //TODO-1 A voir expression régulière
        String[] arguments = code.split("\\" + Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS);
        if (arguments.length > 0){
            for ( int i = 0 ; i < arguments.length ; i++) {
                cleanCode += arguments[i] ;
                if (i <  arguments.length -2 ){
                    // Le dernier argument n'est pas suivi d'un séparateur
                    cleanCode += Preferences.SQL_SEPARATOR_ARGUMENTS;
                }
            }
        } else {
            cleanCode = code;
        }


        return cleanCode;
    }

}

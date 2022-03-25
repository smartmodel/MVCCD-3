package generatorsql;

import generatorsql.generator.MPDRGenerateSQLObjectInCode;
import main.MVCCDManager;
import mdr.MDRCaseFormat;
import mdr.services.MDRModelService;
import mpdr.MPDRModel;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.UtilDivers;
import utilities.files.FileRead;
import utilities.files.FileWrite;
import utilities.files.UtilFiles;

import java.io.File;

public class MPDRGenerateSQLUtil {

    public static File directorySQLFiles(MPDRModel mpdrModel) {
        if (MVCCDManager.instance().getFileProjectCurrent() != null) {
            String filePathProjectCurrent = MVCCDManager.instance().getFileProjectCurrent().getPath();
            String fileNameProjectCurrent = MVCCDManager.instance().getFileProjectCurrent().getName();
            String fileShortNameProjectCurrent = UtilFiles.fileShortName(fileNameProjectCurrent);
            String directoryProjectCurrent = UtilFiles.getStrDirectory(MVCCDManager.instance().getFileProjectCurrent());

            String folderProjectSQLFiles = UtilFiles.filePath(directoryProjectCurrent, fileShortNameProjectCurrent);
            File directoryProjectSQLFile = new File(folderProjectSQLFiles);
            directoryProjectSQLFile.mkdirs(); //Create folder if necessary

            String folderMPDRModelSQLFiles = UtilFiles.filePath(folderProjectSQLFiles, mpdrModel.getName());
            File directoryMPDRModelSQLFile = new File(folderMPDRModelSQLFiles);
            directoryMPDRModelSQLFile.mkdirs(); //Create folder if necessary


            return directoryMPDRModelSQLFile;
        } else {
            return null;
        }
    }


    public static String nameSQLCreateFile() {
        return UtilFiles.fileName(Preferences.FILE_CREATE_SQL_SHORT_NAME, Preferences.FILE_SQL_DDL_EXTENSION);
    }

    public static String nameSQLPopulateFile() {
        return UtilFiles.fileName(Preferences.FILE_POPULATE_SQL_SHORT_NAME, Preferences.FILE_SQL_DDL_EXTENSION);
    }

    public static File sqlCreateFile(MPDRModel mpdrModel){
        String folderSQLFiles = directorySQLFiles(mpdrModel).getPath();
        String nameSQLCreateFile = nameSQLCreateFile();
        return UtilFiles.createFile(folderSQLFiles, nameSQLCreateFile);
    }

    public static File sqlPopulateFile(MPDRModel mpdrModel){
        String folderSQLFiles = directorySQLFiles(mpdrModel).getPath();
        String nameSQLPopulateFile = nameSQLPopulateFile();
        return UtilFiles.createFile(folderSQLFiles, nameSQLPopulateFile);
    }

    public static void generateSQLFile(MPDRModel mpdrModel, String code) {
        File sqlCreateFile = sqlCreateFile(mpdrModel);
        FileWrite.writeFromString(code, sqlCreateFile);
        /*
        //TODO-0 A voir le traitement d'erreur
        //TODO-1 Voir la librairie de Steve (FilesManager)
        try {
            File sqlCreateFile = sqlCreateFile(mpdrModel);
            FileWriter fileWriter = new FileWriter(sqlCreateFile);
            fileWriter.write(code);
            fileWriter.close();
        } catch (Exception e) {
            throw new CodeApplException("Erreur de génération du fichier SQL-DDL");
        }

         */
    }

/*
    public static String replaceKeyValue(String code, String key, String value) {
        //TODO-1 A voir expression régulière
        return code.replaceAll("\\{" + key + "}", value);
    }


 */

    public static boolean find(String code, String key) {
        //TODO-1 A voir expression régulière
        return code.indexOf(Preferences.MDR_WORDS_BEGIN + key + Preferences.MDR_WORDS_END) >= 0;
    }



    public static String cleanSeparatorArguments(String code) {
        String cleanCode = "";
        //TODO-1 A voir expression régulière
        String[] arguments = code.split(Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS);
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

    // Retire les blancs avant et après le code de la commande
    // Retire les sauts de ligne de fin de commande
    // Retourne une chaine vide si la commande ne contient que des blancs et/ou sauts de ligne
    public static String clearCommandSQL (String command){
        command = command.trim();
        while (command.endsWith(System.lineSeparator())){
            if (command.length() == 1){
                command = ""
                ;                            } else {
                command = command.substring(0, command.length()-1);
            }
        }
        return command;
    }


    public static String tabsApplicable(String generateSQLCode, String marker) {

        String tabsApplicable = tabsApplicableInternal(generateSQLCode, marker,
                Preferences.MDR_WORDS_BEGIN, Preferences.MDR_WORDS_END);
        if (StringUtils.isEmpty(tabsApplicable)) {
            String begin = Preferences.MDR_WORDS_BEGIN + Preferences.MPDR_MARKER_DYNAMIC_CODE;
            String end = Preferences.MDR_WORDS_END + Preferences.MPDR_MARKER_DYNAMIC_CODE;
            tabsApplicable = tabsApplicableInternal(generateSQLCode, marker, begin, end);
        }
        return tabsApplicable;
    }

    private static String tabsApplicableInternal(String generateSQLCode,
                                                String marker,
                                                String beginBalise,
                                                String endBalise) {
        String[] lines = StringUtils.split(generateSQLCode, System.lineSeparator());
        if (lines.length > 0) {

            for (int i = 0 ; i < lines.length - 1 ; i++){
                int posStartTemplateInterior = StringUtils.indexOf(lines[i], beginBalise + marker + endBalise);
                if (posStartTemplateInterior >= 0 ){
                    return StringUtils.substring(lines[i], 0, posStartTemplateInterior);
                }
            }
        }
        return "";
    }


    public static String integreTabsApplicable(String sqlCodeDynamic, String tabsApplicable) {
        String resultat = "";
        String[] parts = StringUtils.split(sqlCodeDynamic, System.lineSeparator());
        if (parts.length >= 1){
            resultat += parts[0];
            for (int i = 1; i < parts.length; i++ ){
                resultat +=  System.lineSeparator() + tabsApplicable + parts[i];
            }
        }
        return resultat;
    }

    public static String caseReservedWords(String template, MPDRModel mpdrModel) {
        if (template != null) {
            String resultat = "";
            boolean treat = true;
            for (int i = 0; i < template.length(); i++){
                char  c = template.charAt(i);
                boolean markerBEGINWord = StringUtils.contains(Preferences.MDR_WORDS_BEGIN, c);
                boolean markerENDWord = StringUtils.contains(Preferences.MDR_WORDS_END, c);
                if (treat) {
                    if (markerBEGINWord) {
                        treat = false;
                        resultat += c;
                    } else {
                        resultat += caseApplicable(c, mpdrModel);
                    }
                } else {
                    resultat += c ;
                    if (markerENDWord) {
                        treat = true;
                    }
                }
            }
            return resultat;
        } else {
            return null;
        }
    }

    private static char caseApplicable(char c, MPDRModel mpdrModel) {
        if (mpdrModel.getReservedWordsFormatForDB() == MDRCaseFormat.LOWERCASE){
            return Character.toLowerCase(c);
        } else if (mpdrModel.getReservedWordsFormatForDB() == MDRCaseFormat.UPPERCASE){
            return Character.toUpperCase(c);
        } else {
            return c ;
        }
    }

    public static String template(String dirTemplate, String fileNameTemplate, MPDRModel mpdrModel){
        String template = FileRead.readToString(dirTemplate, fileNameTemplate);
        return MPDRGenerateSQLUtil.caseReservedWords(template, mpdrModel);

    }

    public static String customizeNameObjectInCode(String generateSQLCode, MPDRModel mpdrModel) {
        for (MPDRGenerateSQLObjectInCode mpdrGenerateSQLObjectInCode : MPDRGenerateSQLObjectInCode.getAll()){
            String key = mpdrGenerateSQLObjectInCode.getKey();
            String name = mpdrGenerateSQLObjectInCode.getNameByDb(mpdrModel.getDb());
            name = MDRModelService.caseFormat(name, mpdrModel.getObjectsInCodeFormatForDB());
            generateSQLCode = UtilDivers.replaceKeyValue(generateSQLCode, key, name);
        }
        return generateSQLCode;
    }

    /*
    public static String customizeNameObjectInCode(String generateSQLCode, MPDRDB db) {
        for (MPDRGenerateSQLObjectInCode mpdrGenerateSQLObjectInCode : MPDRGenerateSQLObjectInCode.getAll()){
            String key = mpdrGenerateSQLObjectInCode.getKey();
            String name = mpdrGenerateSQLObjectInCode.getNameByDb(db);
            name = MDRModelService.caseNaming(name, mpdrDb.get)

            generateSQLCode = UtilDivers.replaceKeyValue(generateSQLCode, key, name);
        }
        return generateSQLCode;
    }

     */
}

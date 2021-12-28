package generatorsql.generator;

import console.ViewLogsManager;
import console.WarningLevel;
import messages.MessagesBuilder;
import mpdr.MPDRFK;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import preferences.Preferences;

import java.util.ArrayList;

public abstract class MPDRGenerateSQL {

    protected MPDRModel mpdrModel;

    public MPDRGenerateSQL(MPDRModel mpdrModel) {
        this.mpdrModel = mpdrModel;
    }

    public String generate() {

        String generateSQLCode = "";
        // Suppression en des objets existants
        if (mpdrModel.isDropBeforeCreate()){
            String message = MessagesBuilder.getMessagesProperty("generate.sql.empty");
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            generateSQLCode += generateSQLDropTables();
        }

        String message = MessagesBuilder.getMessagesProperty("generate.sql.tables");
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        generateSQLCode += generateSQLCreateTables();

        message = MessagesBuilder.getMessagesProperty("generate.sql.constraints.fks");
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        generateSQLCode += generateSQLAddFKs();

        return generateSQLCode;
    }

    private String generateSQLDropTables(){

        String generateSQLCode = "";
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            generateSQLCode += getMpdrGenerateSQLTable().generateSQLDropTable(mpdrTable);
            generateSQLCode += delimiter() ;
        }
        return generateSQLCode;
    }



    private String generateSQLCreateTables() {

        String generateSQLCode = "";
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            generateSQLCode += getMpdrGenerateSQLTable().generateSQLCreateTable(mpdrTable);
            generateSQLCode += delimiter() ;
        }
        return generateSQLCode;
    }


    public String generateSQLAddFKs() {
        String generateSQLCode = "";
        ArrayList<MPDRTable> mpdrTables = mpdrModel.getMPDRTables();

        for (MPDRTable mpdrTable : mpdrTables) {
            ArrayList<MPDRFK> mpdrFks = mpdrTable.getMPDRFKs();
            for (MPDRFK mpdrFk : mpdrFks) {
                generateSQLCode  +=  getMpdrGenerateSQLFK().generateSQLFK(mpdrFk);
                generateSQLCode += delimiter() ;
            }
        }
        return generateSQLCode;
    }

     public abstract MPDRGenerateSQLTable getMpdrGenerateSQLTable() ;

    public abstract MPDRGenerateSQLFK getMpdrGenerateSQLFK();

    private String getTemplateDirBase() {
        return Preferences.DIRECTORY_TEMPLATES_NAME + Preferences.SYSTEM_FILE_SEPARATOR +
                Preferences.DIRECTORY_TEMPLATES_SQLDDL_NAME + Preferences.SYSTEM_FILE_SEPARATOR +
                getTemplateBDDirectory() + Preferences.SYSTEM_FILE_SEPARATOR ;
    }

    public String getTemplateDirCreate() {
        return getTemplateDirBase() +
                Preferences.DIRECTORY_TEMPLATES_CREATE;
    }

    public String getTemplateDirAlter() {
        return getTemplateDirBase() +
                Preferences.DIRECTORY_TEMPLATES_ALTER;
    }


    public String getTemplateDirDrop() {
        return getTemplateDirBase() +
                Preferences.DIRECTORY_TEMPLATES_DROP;
    }

    public String delimiter(){
        return Preferences.SYSTEM_LINE_SEPARATOR + getDelimiterInstructions() + Preferences.SYSTEM_LINE_SEPARATOR;


    }


    protected abstract String getTemplateBDDirectory();

    public abstract String getDelimiterInstructions();
}

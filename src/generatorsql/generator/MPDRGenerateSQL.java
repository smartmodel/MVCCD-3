package generatorsql.generator;

import console.ViewLogsManager;
import console.WarningLevel;
import messages.MessagesBuilder;
import mpdr.*;
import preferences.Preferences;

import java.util.ArrayList;

public abstract class MPDRGenerateSQL {

    private final int DROP = 0 ;
    private final int CREATE = 1 ;
    private final int ALTER = 2 ;
    protected MPDRModel mpdrModel;

    public MPDRGenerateSQL(MPDRModel mpdrModel) {
        this.mpdrModel = mpdrModel;
    }

    public String generate() {

        String generateSQLCode = "";
        String message ;
        // Suppression en des objets existants
        if (mpdrModel.getDropBeforeCreate() == MPDRDropBefore.EMPTY) {
            message = MessagesBuilder.getMessagesProperty("generate.sql.empty");
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            generateSQLCode += generateSQLEmpty();

            message = MessagesBuilder.getMessagesProperty("execute.generate.sql.empty");
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            generateSQLCode += generateExecuteSQLEmpty();
        } else if (mpdrModel.getDropBeforeCreate() == MPDRDropBefore.OBJECTSCREATED){
            message = MessagesBuilder.getMessagesProperty("generate.sql.drop.tables");
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            generateSQLCode += generateSQLDropTables();

            message = MessagesBuilder.getMessagesProperty("generate.sql.drop.sequences");
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            generateSQLCode += generateSQLCommandSequences(DROP);
        }

        message = MessagesBuilder.getMessagesProperty("generate.sql.create.tables");
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        generateSQLCode += generateSQLCommandTables(CREATE);

        message = MessagesBuilder.getMessagesProperty("generate.sql.create.sequences");
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        generateSQLCode += generateSQLCommandSequences(CREATE);

        message = MessagesBuilder.getMessagesProperty("generate.sql.alter.constraints.fks");
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        generateSQLCode += generateSQLCommandFKs(ALTER);

        return generateSQLCode;
    }


    private String generateSQLEmpty(){

        String generateSQLCode = "";
        generateSQLCode += getMpdrGenerateSQLEmptySchema().generateSQLEmptySchema();
        generateSQLCode += delimiter() ;
        return generateSQLCode;
    }


    protected String generateExecuteSQLEmpty() {
        String generateSQLCode = "";
        generateSQLCode += getMpdrGenerateSQLEmptySchema().generateExecuteSQLEmptySchema();
        generateSQLCode += delimiter() ;
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

    private String generateSQLCommandTables(int command) {

        String generateSQLCode = "";
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            if (command == CREATE) {
                generateSQLCode += getMpdrGenerateSQLTable().generateSQLCreateTable(mpdrTable);
                generateSQLCode += delimiter();
            }
            if (command == DROP) {
                generateSQLCode += getMpdrGenerateSQLTable().generateSQLDropTable(mpdrTable);
                generateSQLCode += delimiter() ;

            }
        }
        return generateSQLCode;
    }

    private String generateSQLCommandSequences(int command) {

        String generateSQLCode = "";
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            for (MPDRColumn mpdrColumn : mpdrTable.getMPDRColumns()) {
                MPDRSequence mpdrSequence = mpdrColumn.getMPDRSequence();
                if (mpdrSequence != null) {
                    if (command == CREATE) {
                        generateSQLCode += getMpdrGenerateSQLSequence().generateSQLCreateSequence(mpdrSequence);
                        generateSQLCode += delimiter();
                    }
                    if (command == DROP) {
                        generateSQLCode += getMpdrGenerateSQLSequence().generateSQLDropSequence(mpdrSequence);
                        generateSQLCode += delimiter();
                    }
                }
            }
        }
        return generateSQLCode;
    }


    public String generateSQLCommandFKs(int command) {
        String generateSQLCode = "";
        ArrayList<MPDRTable> mpdrTables = mpdrModel.getMPDRTables();

        for (MPDRTable mpdrTable : mpdrTables) {
            ArrayList<MPDRFK> mpdrFks = mpdrTable.getMPDRFKs();
            for (MPDRFK mpdrFk : mpdrFks) {
                if (command == ALTER) {
                    generateSQLCode += getMpdrGenerateSQLFK().generateSQLFK(mpdrFk);
                    generateSQLCode += delimiter();
                }
            }
        }
        return generateSQLCode;
    }

    public abstract MPDRGenerateSQLEmptySchema getMpdrGenerateSQLEmptySchema() ;

    public abstract MPDRGenerateSQLTable getMpdrGenerateSQLTable() ;

    public abstract MPDRGenerateSQLSequence getMpdrGenerateSQLSequence() ;

    public abstract MPDRGenerateSQLFK getMpdrGenerateSQLFK();

    private String getTemplateDirBase() {
        return Preferences.DIRECTORY_TEMPLATES_NAME + Preferences.SYSTEM_FILE_SEPARATOR +
                Preferences.DIRECTORY_TEMPLATES_SQLDDL_NAME + Preferences.SYSTEM_FILE_SEPARATOR ;
    }


    public String getTemplateDirDrop() {
        return getTemplateDirBase() +
                Preferences.DIRECTORY_TEMPLATES_DROP;
    }

    private String getTemplateDirBaseDB() {
        return getTemplateDirBase() +
                getTemplateBDDirectory() + Preferences.SYSTEM_FILE_SEPARATOR ;
    }

    public String getTemplateDirCreateDB() {
        return getTemplateDirBaseDB() +
                Preferences.DIRECTORY_TEMPLATES_CREATE;
    }

    public String getTemplateDirAlterDB() {
        return getTemplateDirBaseDB() +
                Preferences.DIRECTORY_TEMPLATES_ALTER;
    }


    public String getTemplateDirDropDB() {
        return getTemplateDirBaseDB() +
                Preferences.DIRECTORY_TEMPLATES_DROP;
    }

    public String getTemplateDirTriggersDB() {
        return getTemplateDirBaseDB() +
                Preferences.DIRECTORY_TEMPLATES_TRIGGERS;
    }

    public String delimiter(){
        return Preferences.SYSTEM_LINE_SEPARATOR + getDelimiterInstructions() + Preferences.SYSTEM_LINE_SEPARATOR;


    }


    protected abstract String getTemplateBDDirectory();

    public abstract String getDelimiterInstructions();
}

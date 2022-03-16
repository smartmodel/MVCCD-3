package generatorsql.generator;

import console.ViewLogsManager;
import console.WarningLevel;
import messages.MessagesBuilder;
import mpdr.*;
import mpdr.interfaces.IMPDRModelRequirePackage;
import mpdr.interfaces.IMPDRTableRequirePackage;
import mpdr.tapis.MPDRFunction;
import mpdr.tapis.MPDRPackage;
import mpdr.tapis.MPDRTrigger;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.UtilDivers;

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
        // Suppression  des objets existants
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

            message = MessagesBuilder.getMessagesProperty("generate.sql.drop.triggers");
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            generateSQLCode += generateSQLCommandTriggers(DROP);

            message = MessagesBuilder.getMessagesProperty("generate.sql.drop.functions");
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            generateSQLCode += generateSQLCommandFunctions(DROP);

            message = MessagesBuilder.getMessagesProperty("generate.sql.drop.packages");
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            generateSQLCode += generateSQLCommandPackages(DROP);

            message = MessagesBuilder.getMessagesProperty("generate.sql.drop.indexes");
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            generateSQLCode += generateSQLCommandIndexes(DROP);
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

        message = MessagesBuilder.getMessagesProperty("generate.sql.create.functions");
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        generateSQLCode += generateSQLCommandFunctions(CREATE);

        message = MessagesBuilder.getMessagesProperty("generate.sql.create.packages");
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        generateSQLCode += generateSQLCommandPackages(CREATE);

        message = MessagesBuilder.getMessagesProperty("generate.sql.create.triggers");
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        generateSQLCode += generateSQLCommandTriggers(CREATE);

        message = MessagesBuilder.getMessagesProperty("generate.sql.create.indexes");
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        generateSQLCode += generateSQLCommandIndexes(CREATE);

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
                    generateSQLCode += delimiter();

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

    private String generateSQLCommandTriggers(int command) {

        String generateSQLCode = "";
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            if (mpdrTable.getMPDRTriggers() != null) {
                for (MPDRTrigger mpdrTrigger : mpdrTable.getMPDRTriggers()) {
                    if (command == CREATE) {
                        generateSQLCode += getMpdrGenerateSQLTrigger().generateSQLCreateTrigger(mpdrTrigger);
                        generateSQLCode += delimiter();
                    }
                    if (command == DROP) {
                        generateSQLCode += getMpdrGenerateSQLTrigger().generateSQLDropTrigger(mpdrTrigger);
                        generateSQLCode += delimiter();
                    }
                }
            }
        }
        return generateSQLCode;
    }

    private String generateSQLCommandFunctions(int command) {

        String generateSQLCode = "";
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            if (mpdrTable.getMPDRFunctions() != null) {
                for (MPDRFunction mpdrFunction : mpdrTable.getMPDRFunctions()) {
                    if (command == CREATE) {
                        generateSQLCode += getMpdrGenerateSQLFunction().generateSQLCreateFunction(mpdrFunction);
                        generateSQLCode += delimiter();
                    }
                    if (command == DROP) {
                        generateSQLCode += getMpdrGenerateSQLFunction().generateSQLDropFunction(mpdrFunction);
                        generateSQLCode += delimiter();
                    }
                }
            }
        }
        return generateSQLCode;
    }


    private String generateSQLCommandPackages(int command) {

        String generateSQLCode = "";
        if (mpdrModel instanceof IMPDRModelRequirePackage) {
            for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
                if (((IMPDRTableRequirePackage) mpdrTable).getMPDRPackages() != null) {
                    for (MPDRPackage mpdrPackage : ((IMPDRTableRequirePackage) mpdrTable).getMPDRPackages()) {
                        if (command == CREATE) {
                            generateSQLCode += getMpdrGenerateSQLPackage().generateSQLCreatePackage(mpdrPackage);
                            generateSQLCode += delimiter();
                        }
                        if (command == DROP) {
                            generateSQLCode += getMpdrGenerateSQLPackage().generateSQLDropPackage(mpdrPackage);
                            generateSQLCode += delimiter();
                        }
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

    public String generateSQLCommandIndexes(int command) {
        String generateSQLCode = "";
        ArrayList<MPDRTable> mpdrTables = mpdrModel.getMPDRTables();

        for (MPDRTable mpdrTable : mpdrTables) {
            ArrayList<MPDRIndex> mpdrIndexes = mpdrTable.getMPDRIndexes();
            for (MPDRIndex mpdrIndex : mpdrIndexes) {
                if (command == CREATE) {
                    generateSQLCode += getMpdrGenerateSQLIndex().generateSQLCreateIndex(mpdrIndex);
                    generateSQLCode += delimiter();
                }
                if (command == DROP) {
                    generateSQLCode += getMpdrGenerateSQLIndex().generateSQLDropIndex(mpdrIndex);
                    generateSQLCode += delimiter();
                }

            }
        }
        return generateSQLCode;
    }


    public abstract MPDRGenerateSQLEmptySchema getMpdrGenerateSQLEmptySchema() ;

    public abstract MPDRGenerateSQLTable getMpdrGenerateSQLTable() ;

    public abstract MPDRGenerateSQLSequence getMpdrGenerateSQLSequence() ;

    public abstract MPDRGenerateSQLTrigger getMpdrGenerateSQLTrigger() ;

    public abstract MPDRGenerateSQLFunction getMpdrGenerateSQLFunction() ;

    public abstract MPDRGenerateSQLPackage getMpdrGenerateSQLPackage() ;

    public abstract MPDRGenerateSQLDynamicCode getMpdrGenerateSQLCodeDynamic() ;

    public abstract MPDRGenerateSQLFK getMpdrGenerateSQLFK();

    protected abstract MPDRGenerateSQLIndex getMpdrGenerateSQLIndex();

    private String getTemplateDirBase() {
        return Preferences.DIRECTORY_TEMPLATES_NAME + Preferences.SYSTEM_FILE_SEPARATOR +
                Preferences.DIRECTORY_TEMPLATES_SQLDDL_NAME  ;
    }

    private String getTemplateDirBaseDB() {
        return getTemplateDirBase() + Preferences.SYSTEM_FILE_SEPARATOR +
                getTemplateBDDirectory()  ;
    }

    public String getTemplateDirCreateDB() {
        return getTemplateDirBaseDB() + Preferences.SYSTEM_FILE_SEPARATOR +
                Preferences.DIRECTORY_TEMPLATES_CREATE;
    }

    public String getTemplateDirAlterDB() {
        return getTemplateDirBaseDB() + Preferences.SYSTEM_FILE_SEPARATOR +
                Preferences.DIRECTORY_TEMPLATES_ALTER;
    }

    public String getTemplateDirDropDB() {
        return getTemplateDirBaseDB() + Preferences.SYSTEM_FILE_SEPARATOR+
                Preferences.DIRECTORY_TEMPLATES_DROP;
    }

    public String getTemplateDirOptionsDB() {
        return getTemplateDirBaseDB() + Preferences.SYSTEM_FILE_SEPARATOR+
                Preferences.DIRECTORY_TEMPLATES_OPTIONS;
    }

    public String getTemplateDirTriggersDB() {
        return getTemplateDirBaseDB() + Preferences.SYSTEM_FILE_SEPARATOR +
                Preferences.DIRECTORY_TEMPLATES_TRIGGERS;
    }

    public String getTemplateDirCreateTriggersDB() {
        return getTemplateDirTriggersDB() + Preferences.SYSTEM_FILE_SEPARATOR +
                Preferences.DIRECTORY_TEMPLATES_CREATE;
    }

    public String getTemplateDirDropTriggersDB() {
        return getTemplateDirTriggersDB() + Preferences.SYSTEM_FILE_SEPARATOR +
                Preferences.DIRECTORY_TEMPLATES_DROP;
    }

    public String getTemplateDirStoredCodeDB() {
        return getTemplateDirBaseDB() + Preferences.SYSTEM_FILE_SEPARATOR +
                Preferences.DIRECTORY_TEMPLATES_STOREDCODE;
    }

    public String getTemplateDirCreateStoredCodeDB() {
        return getTemplateDirStoredCodeDB() + Preferences.SYSTEM_FILE_SEPARATOR +
                Preferences.DIRECTORY_TEMPLATES_CREATE;
    }

    public String getTemplateDirDropStoredCodeDB() {
        return getTemplateDirStoredCodeDB() + Preferences.SYSTEM_FILE_SEPARATOR +
                Preferences.DIRECTORY_TEMPLATES_DROP;
    }


    public String getTemplateDirDynamicCodeDB() {
        return getTemplateDirBaseDB() + Preferences.SYSTEM_FILE_SEPARATOR +
                Preferences.DIRECTORY_TEMPLATES_DYNAMIC_CODE;
    }

    /*
    public String getTemplateDirBlocksDB() {
        return getTemplateDirBaseDB() + Preferences.SYSTEM_FILE_SEPARATOR +
                Preferences.DIRECTORY_TEMPLATES_BLOCKS;
    }

     */

    public String delimiter(){
        return Preferences.SYSTEM_LINE_SEPARATOR + getDelimiterInstructions() + Preferences.SYSTEM_LINE_SEPARATOR;


    }


    protected abstract String getTemplateBDDirectory();

    public abstract String getDelimiterInstructions();


    // Surcharge si nécessité d'indiquer le schéma (PostgreSQL)
    public String replaceKeyValueWithSpecific(String code, String key, String value) {
        return UtilDivers.replaceKeyValue(code, key, value);
    }

    public String replaceKeyValueWithSpecific(String code, String key, String value, String beforeKey, String afterKey) {
        return UtilDivers.replaceKeyValue(code, key, value, beforeKey, afterKey);
    }

    public static String nullifyKey(String code, String beforeKey, String key, String value) {
        return StringUtils.replace(code, beforeKey +
                Preferences.MDR_WORDS_BEGIN+ key + Preferences.MDR_WORDS_END, value);
    }

}

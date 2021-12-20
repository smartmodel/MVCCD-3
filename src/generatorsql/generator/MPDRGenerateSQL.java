package generatorsql.generator;

import console.ViewLogsManager;
import console.WarningLevel;
import messages.MessagesBuilder;
import mpdr.MPDRModel;
import preferences.Preferences;

public class MPDRGenerateSQL {

    private MPDRModel mpdrModel;

    public MPDRGenerateSQL(MPDRModel mpdrModel) {
        this.mpdrModel = mpdrModel;
    }

    public String generate() {
            String message = MessagesBuilder.getMessagesProperty("generate.sql.tables");
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            MPDRGenerateSQLTables mpdrGenerateSQLTables = new MPDRGenerateSQLTables(mpdrModel);
            String generateSQLCode = mpdrGenerateSQLTables.generateSQLCreateTables();

            generateSQLCode += Preferences.SYSTEM_LINE_SEPARATOR;

            message = MessagesBuilder.getMessagesProperty("generate.sql.constraints.fks");
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            MPDRGenerateSQLFKs mpdrGenerateSQLFKs = new MPDRGenerateSQLFKs(mpdrModel);
            generateSQLCode += mpdrGenerateSQLFKs.generateSQLFKs();


            return generateSQLCode;
    }
}

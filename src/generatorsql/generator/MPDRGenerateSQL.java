package generatorsql.generator;

import messages.MessagesBuilder;
import mpdr.MPDRModel;
import preferences.Preferences;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;

public class MPDRGenerateSQL {

    private MPDRModel mpdrModel;
    private Resultat resultat ;

    public MPDRGenerateSQL(MPDRModel mpdrModel, Resultat resultat) {
        this.mpdrModel = mpdrModel;
        this.resultat = resultat ;
    }

    public String generate() {
        try {
            String message = MessagesBuilder.getMessagesProperty("generate.sql.tables");
            resultat.add(new ResultatElement(message, ResultatLevel.INFO));
            MPDRGenerateSQLTables mpdrGenerateSQLTables = new MPDRGenerateSQLTables(mpdrModel, resultat);
            String generateSQLCode = mpdrGenerateSQLTables.generateSQLCreateTables();

            generateSQLCode += Preferences.SYSTEM_LINE_SEPARATOR;

            message = MessagesBuilder.getMessagesProperty("generate.sql.constraints.fks");
            resultat.add(new ResultatElement(message, ResultatLevel.INFO));
            MPDRGenerateSQLFKs mpdrGenerateSQLFKs = new MPDRGenerateSQLFKs(mpdrModel, resultat);
            generateSQLCode += mpdrGenerateSQLFKs.generateSQLFKs();


            return generateSQLCode;
        } catch (Exception e) {
            resultat.addExceptionUnhandled(e);
            return null;
        }
    }
}

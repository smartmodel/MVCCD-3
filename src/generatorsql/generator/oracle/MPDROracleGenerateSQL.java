package generatorsql.generator.oracle;

import generatorsql.generator.*;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import preferences.Preferences;

public class MPDROracleGenerateSQL extends MPDRGenerateSQL {

    private MPDROracleGenerateSQLEmptySchema mpdrOracleGenerateSQLEmptySchema;
    private MPDROracleGenerateSQLTable mpdrOracleGenerateSQLTable;
    private MPDROracleGenerateSQLFK mpdrOracleGenerateSQLFK;
    private MPDROracleGenerateSQLSequence mpdrOracleGenerateSQLSequence;

    public MPDROracleGenerateSQL(MPDRModel mpdrModel) {
        super (mpdrModel);

    }

    public String generate() {
        mpdrOracleGenerateSQLEmptySchema = new MPDROracleGenerateSQLEmptySchema(this);
        mpdrOracleGenerateSQLTable = new MPDROracleGenerateSQLTable(this);
        mpdrOracleGenerateSQLFK = new MPDROracleGenerateSQLFK(this);
        mpdrOracleGenerateSQLSequence = new MPDROracleGenerateSQLSequence(this);
        String generateSQLCode = super.generate();
        return generateSQLCode;
    }

    @Override
    public MPDRGenerateSQLEmptySchema getMpdrGenerateSQLEmptySchema() {
        return mpdrOracleGenerateSQLEmptySchema;
    }


    @Override
    public MPDRGenerateSQLTable getMpdrGenerateSQLTable() {
        return mpdrOracleGenerateSQLTable;
    }

    @Override
    public MPDRGenerateSQLSequence getMpdrGenerateSQLSequence() {
        return mpdrOracleGenerateSQLSequence;
    }

    @Override
    public MPDRGenerateSQLFK getMpdrGenerateSQLFK() {
        return mpdrOracleGenerateSQLFK;
    }

    @Override
    protected String getTemplateBDDirectory() {
        return Preferences.DIRECTORY_TEMPLATES_ORACLE_NAME;
    }

    @Override
    public String getDelimiterInstructions() {
        return MPDRDB.ORACLE.getDelimiterInstructions();
    }
}

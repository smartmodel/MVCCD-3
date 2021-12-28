package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLFK;
import generatorsql.generator.MPDRGenerateSQLTable;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import preferences.Preferences;

public class MPDROracleGenerateSQL extends MPDRGenerateSQL {

    private MPDROracleGenerateSQLTable mpdrOracleGenerateSQLTable;
    private MPDROracleGenerateSQLFK mpdrOracleGenerateSQLFK;

    public MPDROracleGenerateSQL(MPDRModel mpdrModel) {
        super (mpdrModel);

    }

    public String generate() {
        mpdrOracleGenerateSQLTable = new MPDROracleGenerateSQLTable(this);
        mpdrOracleGenerateSQLFK = new MPDROracleGenerateSQLFK(this);
        String generateSQLCode = super.generate();
        return generateSQLCode;
    }



    @Override
    public MPDRGenerateSQLTable getMpdrGenerateSQLTable() {
        return mpdrOracleGenerateSQLTable;
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

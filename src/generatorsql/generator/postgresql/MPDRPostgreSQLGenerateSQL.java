package generatorsql.generator.postgresql;

import generatorsql.generator.*;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import preferences.Preferences;

public class MPDRPostgreSQLGenerateSQL extends MPDRGenerateSQL {

    private MPDRPostgreSQLGenerateSQLEmptySchema mpdrPostgreSQLGenerateSQLEmptySchema;
    private MPDRPostgreSQLGenerateSQLTable mpdrPostgreSQLGenerateSQLTable;
    private MPDRPostgreSQLGenerateSQLFK mpdrPostgreSQLGenerateSQLFK;
    private MPDRPostgreSQLGenerateSQLSequence mpdrPostgreSQLGenerateSQLSequence;
    private MPDRPostgreSQLGenerateSQLTrigger mpdrPostgreSQLGenerateSQLTrigger;
    private MPDRPostgreSQLGenerateSQLDynamicCode mpdrPostgreSQLGenerateSQLDynamicCode;

    public MPDRPostgreSQLGenerateSQL(MPDRModel mpdrModel) {
        super (mpdrModel);
        init();

    }

    private void init(){
        mpdrPostgreSQLGenerateSQLEmptySchema = new MPDRPostgreSQLGenerateSQLEmptySchema(this);
        mpdrPostgreSQLGenerateSQLTable = new MPDRPostgreSQLGenerateSQLTable(this);
        mpdrPostgreSQLGenerateSQLFK = new MPDRPostgreSQLGenerateSQLFK(this);
        mpdrPostgreSQLGenerateSQLSequence = new MPDRPostgreSQLGenerateSQLSequence(this);
        mpdrPostgreSQLGenerateSQLTrigger = new MPDRPostgreSQLGenerateSQLTrigger(this);
        mpdrPostgreSQLGenerateSQLDynamicCode = new MPDRPostgreSQLGenerateSQLDynamicCode(this);
    }

    public String generate() {
        String generateSQLCode = super.generate();
        return generateSQLCode;
    }

    @Override
    public MPDRGenerateSQLEmptySchema getMpdrGenerateSQLEmptySchema() {
        return mpdrPostgreSQLGenerateSQLEmptySchema;
    }


    @Override
    public MPDRGenerateSQLTable getMpdrGenerateSQLTable() {
        return mpdrPostgreSQLGenerateSQLTable;
    }

    @Override
    public MPDRGenerateSQLSequence getMpdrGenerateSQLSequence() {
        return mpdrPostgreSQLGenerateSQLSequence;
    }

    @Override
    public MPDRGenerateSQLTrigger getMpdrGenerateSQLTrigger() {
        return mpdrPostgreSQLGenerateSQLTrigger;
    }

    @Override
    public MPDRGenerateSQLDynamicCode getMpdrGenerateSQLCodeDynamic() {
        return mpdrPostgreSQLGenerateSQLDynamicCode;
    }

    @Override
    public MPDRGenerateSQLFK getMpdrGenerateSQLFK() {
        return mpdrPostgreSQLGenerateSQLFK;
    }

    @Override
    protected String getTemplateBDDirectory() {
        return Preferences.DIRECTORY_TEMPLATES_POSTGRESQL_NAME;
    }

    @Override
    public String getDelimiterInstructions() {
        return MPDRDB.POSTGRESQL.getDelimiterInstructions();
    }
}

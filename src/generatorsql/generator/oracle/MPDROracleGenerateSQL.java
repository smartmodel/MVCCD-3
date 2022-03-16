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
    private MPDROracleGenerateSQLTrigger mpdrOracleGenerateSQLTrigger;
    private MPDROracleGenerateSQLFunction mpdrOracleGenerateSQLFunction;
    private MPDROracleGenerateSQLPackage mpdrOracleGenerateSQLPackage;
    private MPDROracleGenerateSQLDynamicCode mpdrOracleGenerateSQLCodeDynamic;
    private MPDROracleGenerateSQLIndex mpdrOracleGenerateSQLIndex;
    private MPDROracleGenerateSQLView mpdrOracleGenerateSQLView;

    public MPDROracleGenerateSQL(MPDRModel mpdrModel) {
        super (mpdrModel);
        init();

    }

    private void init(){
        mpdrOracleGenerateSQLEmptySchema = new MPDROracleGenerateSQLEmptySchema(this);
        mpdrOracleGenerateSQLTable = new MPDROracleGenerateSQLTable(this);
        mpdrOracleGenerateSQLFK = new MPDROracleGenerateSQLFK(this);
        mpdrOracleGenerateSQLSequence = new MPDROracleGenerateSQLSequence(this);
        mpdrOracleGenerateSQLTrigger = new MPDROracleGenerateSQLTrigger(this);
        mpdrOracleGenerateSQLFunction = new MPDROracleGenerateSQLFunction(this);
        mpdrOracleGenerateSQLPackage = new MPDROracleGenerateSQLPackage(this);
        mpdrOracleGenerateSQLCodeDynamic = new MPDROracleGenerateSQLDynamicCode(this);
        mpdrOracleGenerateSQLIndex = new MPDROracleGenerateSQLIndex(this);
        mpdrOracleGenerateSQLView = new MPDROracleGenerateSQLView(this);
    }

    public String generate() {
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
    public MPDRGenerateSQLTrigger getMpdrGenerateSQLTrigger() {
        return mpdrOracleGenerateSQLTrigger;
    }

    @Override
    public MPDRGenerateSQLFunction getMpdrGenerateSQLFunction() {
        return mpdrOracleGenerateSQLFunction;
    }

    @Override
    public MPDRGenerateSQLPackage getMpdrGenerateSQLPackage() {
        return mpdrOracleGenerateSQLPackage;
    }

    @Override
    public MPDRGenerateSQLDynamicCode getMpdrGenerateSQLCodeDynamic() {
        return mpdrOracleGenerateSQLCodeDynamic;
    }

    @Override
    public MPDRGenerateSQLFK getMpdrGenerateSQLFK() {
        return mpdrOracleGenerateSQLFK;
    }

    @Override
    protected MPDRGenerateSQLIndex getMpdrGenerateSQLIndex() {
        return mpdrOracleGenerateSQLIndex;
    }

    public MPDROracleGenerateSQLView getMpdrOracleGenerateSQLView() {
        return mpdrOracleGenerateSQLView;
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

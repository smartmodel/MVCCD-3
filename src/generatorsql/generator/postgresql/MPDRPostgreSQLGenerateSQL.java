package generatorsql.generator.postgresql;

import generatorsql.generator.*;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import mpdr.postgresql.MPDRPostgreSQLModel;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;

import java.util.ArrayList;

public class MPDRPostgreSQLGenerateSQL extends MPDRGenerateSQL {

    private MPDRPostgreSQLGenerateSQLEmptySchema mpdrPostgreSQLGenerateSQLEmptySchema;
    private MPDRPostgreSQLGenerateSQLTable mpdrPostgreSQLGenerateSQLTable;
    private MPDRPostgreSQLGenerateSQLFK mpdrPostgreSQLGenerateSQLFK;
    private MPDRPostgreSQLGenerateSQLSequence mpdrPostgreSQLGenerateSQLSequence;
    private MPDRPostgreSQLGenerateSQLTrigger mpdrPostgreSQLGenerateSQLTrigger;
    private MPDRPostgreSQLGenerateSQLFunction mpdrPostgreSQLGenerateSQLFunction;
    private MPDRPostgreSQLGenerateSQLDynamicCode mpdrPostgreSQLGenerateSQLDynamicCode;
    private MPDRPostgreSQLGenerateSQLIndex mpdrPostgreSQLGenerateSQLIndex;

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
        mpdrPostgreSQLGenerateSQLFunction = new MPDRPostgreSQLGenerateSQLFunction(this);
        mpdrPostgreSQLGenerateSQLDynamicCode = new MPDRPostgreSQLGenerateSQLDynamicCode(this);
        mpdrPostgreSQLGenerateSQLIndex = new MPDRPostgreSQLGenerateSQLIndex(this);
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
    public MPDRGenerateSQLFunction getMpdrGenerateSQLFunction() {
        return mpdrPostgreSQLGenerateSQLFunction;
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
    protected MPDRGenerateSQLIndex getMpdrGenerateSQLIndex() {
        return mpdrPostgreSQLGenerateSQLIndex;
    }

    @Override
    protected String getTemplateBDDirectory() {
        return Preferences.DIRECTORY_TEMPLATES_POSTGRESQL_NAME;
    }

    @Override
    public String getDelimiterInstructions() {
        return MPDRDB.POSTGRESQL.getDelimiterInstructions();
    }

    public String replaceKeyValue(String code, String key, String value) {

        String replacement = value ;

        ArrayList<String>  keysWithSchema = new ArrayList<String>();
        keysWithSchema.add(Preferences.MDR_TABLE_NAME_WORD);
        keysWithSchema.add(Preferences.MDR_TABLE_NAME_CHILD_WORD);
        keysWithSchema.add(Preferences.MDR_TABLE_NAME_PARENT_WORD);
        keysWithSchema.add(Preferences.MPDR_SEQUENCE_NAME_WORD);
        keysWithSchema.add(Preferences.MPDR_FUNCTION_NAME_WORD);

        if (keysWithSchema.contains(key)) {
            MPDRPostgreSQLModel mpdrPostgreSQLModel = (MPDRPostgreSQLModel) mpdrModel;
            String schema = mpdrPostgreSQLModel.getSchema();
            //schema= schema.toLowerCase();
            //value = value.toLowerCase();

            if (StringUtils.isNotEmpty(schema)) {
                replacement = schema + "." + value;
            }
        }

        return super.replaceKeyValue(code, key, replacement);
    }


}

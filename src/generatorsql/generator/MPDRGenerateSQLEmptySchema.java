package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import preferences.Preferences;

public abstract class MPDRGenerateSQLEmptySchema {


    public MPDRGenerateSQLEmptySchema() {
    }

    public String generateSQLEmptySchema() {

        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirDropDB(),
                Preferences.TEMPLATES_DROP_EMPTY_SCHEMA,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_EMPTY_SCHEMA_NAME_WORD, getEmptySchema());
        return generateSQLCode;
    }


    protected  String generateExecuteSQLEmptySchema() {

        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirDropDB(),
                Preferences.TEMPLATES_DROP_EXECUTE_EMPTY_SCHEMA,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_EMPTY_SCHEMA_NAME_WORD, getEmptySchema());
        return generateSQLCode;
    }

    protected abstract String getEmptySchema();

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
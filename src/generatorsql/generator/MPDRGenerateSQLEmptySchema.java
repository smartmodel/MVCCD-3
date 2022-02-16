package generatorsql.generator;

import preferences.Preferences;
import utilities.files.FileRead;

public abstract class MPDRGenerateSQLEmptySchema {


    public MPDRGenerateSQLEmptySchema() {
    }

    public String generateSQLEmptySchema() {
        String generateSQLCode = "";
        generateSQLCode += FileRead.readToString(getMPDRGenerateSQL().getTemplateDirDropDB(), Preferences.TEMPLATES_DROP_EMPTY_SCHEMA) ;
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_EMPTY_SCHEMA_NAME_WORD, getEmptySchema());
        return generateSQLCode;
    }


    protected  String generateExecuteSQLEmptySchema() {
        String generateSQLCode = "";
        generateSQLCode += FileRead.readToString(getMPDRGenerateSQL().getTemplateDirDrop(), Preferences.TEMPLATES_DROP_EXECUTE_EMPTY_SCHEMA) ;
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_EMPTY_SCHEMA_NAME_WORD, getEmptySchema());
        return generateSQLCode;
    }

    protected abstract String getEmptySchema();

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
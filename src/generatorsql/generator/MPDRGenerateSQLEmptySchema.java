package generatorsql.generator;

import preferences.Preferences;
import utilities.ReadFile;

public abstract class MPDRGenerateSQLEmptySchema {


    public MPDRGenerateSQLEmptySchema() {
    }

    public String generateSQLEmptySchema() {
        String generateSQLCode = "";
        generateSQLCode += ReadFile.fileToString(getMPDRGenerateSQL().getTemplateDirDropDB(), Preferences.TEMPLATES_DROP_EMPTY_SCHEMA) ;
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_EMPTY_SCHEMA_NAME_WORD, getEmptySchema());
        return generateSQLCode;
    }


    protected  String generateExecuteSQLEmptySchema() {
        String generateSQLCode = "";
        generateSQLCode += ReadFile.fileToString(getMPDRGenerateSQL().getTemplateDirDrop(), Preferences.TEMPLATES_DROP_EXECUTE_EMPTY_SCHEMA) ;
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_EMPTY_SCHEMA_NAME_WORD, getEmptySchema());
        return generateSQLCode;
    }

    protected abstract String getEmptySchema();

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
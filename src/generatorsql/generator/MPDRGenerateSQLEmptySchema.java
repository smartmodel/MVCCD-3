package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import preferences.Preferences;
import utilities.TemplateFile;

public abstract class MPDRGenerateSQLEmptySchema {


    public MPDRGenerateSQLEmptySchema() {
    }

    public String generateSQLEmptySchema() {
        String generateSQLCode = "";
        generateSQLCode += TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirDropDB(), Preferences.TEMPLATES_DROP_EMPTY_SCHEMA) ;
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, Preferences.MPDR_EMPTY_SCHEMA_NAME_WORD, getEmptySchema());
        return generateSQLCode;
    }


    protected  String generateExecuteSQLEmptySchema() {
        String generateSQLCode = "";
        generateSQLCode += TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirDrop(), Preferences.TEMPLATES_DROP_EXECUTE_EMPTY_SCHEMA) ;
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, Preferences.MPDR_EMPTY_SCHEMA_NAME_WORD, getEmptySchema());
        return generateSQLCode;
    }

    protected abstract String getEmptySchema();

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
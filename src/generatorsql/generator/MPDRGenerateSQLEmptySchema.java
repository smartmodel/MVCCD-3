package generatorsql.generator;

import preferences.Preferences;
import utilities.TemplateFile;

public abstract class MPDRGenerateSQLEmptySchema {


    public MPDRGenerateSQLEmptySchema() {
    }

    public String generateSQLEmptySchema() {
        String generateSQLCode = "";
        generateSQLCode += TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirDrop(), Preferences.TEMPLATES_DROP_EMPTY_SCHEMA) ;
        return generateSQLCode;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}
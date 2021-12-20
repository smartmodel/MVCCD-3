package generatorsql.generator;

import mpdr.MPDRModel;
import preferences.Preferences;
import utilities.TemplateFile;

public class MPDRGenerateSQLEmptySchema {

    private MPDRModel mpdrModel;

    public MPDRGenerateSQLEmptySchema(MPDRModel mpdrModel) {
        this.mpdrModel = mpdrModel;
    }

    public String generateSQLEmptySchema() {
        String generateSQLCode = "";
        generateSQLCode += TemplateFile.templateFileToString(mpdrModel.getTemplateDirDrop(), Preferences.TEMPLATES_DROP_EMPTY_SCHEMA) ;
        return generateSQLCode;
    }

}

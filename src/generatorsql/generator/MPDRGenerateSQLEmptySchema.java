package generatorsql.generator;

import mpdr.MPDRModel;
import preferences.Preferences;
import resultat.Resultat;
import utilities.TemplateFile;

public class MPDRGenerateSQLEmptySchema {

    private MPDRModel mpdrModel;
    private Resultat resultat ;

    public MPDRGenerateSQLEmptySchema(MPDRModel mpdrModel, Resultat resultat) {
        this.mpdrModel = mpdrModel;
        this.resultat = resultat ;
    }

    public String generateSQLEmptySchema() {
        String generateSQLCode = "";
        generateSQLCode += TemplateFile.templateFileToString(mpdrModel.getTemplateDirDrop(), Preferences.TEMPLATES_DROP_EMPTY_SCHEMA) ;
        return generateSQLCode;
    }

}

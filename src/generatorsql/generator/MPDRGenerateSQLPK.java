package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import preferences.Preferences;
import utilities.TemplateFile;

public class MPDRGenerateSQLPK {

    private MPDRModel  mpdrModel;
    private MPDRTable mpdrTable;

    public MPDRGenerateSQLPK(MPDRTable mpdrTable) {
        this.mpdrTable = mpdrTable;
        mpdrModel = mpdrTable.getMPDRModelParent();
    }

    public String generateSQLCreatePK() {
        String generateSQLCode = "";
        //generateSQLCode += Preferences.SQL_SEPARATOR_ARGUMENTS + System.lineSeparator();

        generateSQLCode += "\t" + TemplateFile.templateFileToString(mpdrModel.getTemplateDirCreate(), Preferences.TEMPLATE_CREATE_TABLE_PK)  +
                Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS ;

        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "pk_name", mpdrTable.getMPDRPK().getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "pk_columns", mpdrTable.getMPDRPK().getParametersNameAsStr());

        return generateSQLCode;
    }
}

package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRTable;
import preferences.Preferences;
import utilities.TemplateFile;

public abstract class MPDRGenerateSQLPK {

    public MPDRGenerateSQLPK() {
    }

    public String generateSQLCreatePK(MPDRTable mpdrTable) {
        String generateSQLCode = "";
        //generateSQLCode += Preferences.SQL_SEPARATOR_ARGUMENTS + System.lineSeparator();

        generateSQLCode += "\t" + TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirCreate(), Preferences.TEMPLATE_CREATE_TABLE_PK)  +
                Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS ;

        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, Preferences.MDR_PK_NAME_WORD, mpdrTable.getMPDRPK().getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, Preferences.MDR_PK_COLUMNS_WORD, mpdrTable.getMPDRPK().getParametersNameAsStr());

        return generateSQLCode;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

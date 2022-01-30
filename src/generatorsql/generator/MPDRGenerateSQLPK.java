package generatorsql.generator;

import mpdr.MPDRTable;
import preferences.Preferences;
import utilities.TemplateFile;

public abstract class MPDRGenerateSQLPK {

    public MPDRGenerateSQLPK() {
    }

    public String generateSQLCreatePK(MPDRTable mpdrTable) {
        String generateSQLCode = "";
        //generateSQLCode += Preferences.SQL_SEPARATOR_ARGUMENTS + System.lineSeparator();

        generateSQLCode += TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirCreateDB(), Preferences.TEMPLATE_CREATE_TABLE_PK)  +
                Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS ;

        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_PK_NAME_WORD, mpdrTable.getMPDRPK().getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_PK_COLUMNS_WORD, mpdrTable.getMPDRPK().getParametersNameAsStr());

        return generateSQLCode;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

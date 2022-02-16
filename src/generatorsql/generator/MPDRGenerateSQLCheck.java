package generatorsql.generator;

import mpdr.MPDRCheck;
import preferences.Preferences;
import utilities.files.FileRead;

public abstract class MPDRGenerateSQLCheck {

    public MPDRGenerateSQLCheck() {
    }

    public String generateSQLCreateCheck(MPDRCheck mpdrCheck) {
        String generateSQLCode = "";

        generateSQLCode += FileRead.readToString(getMPDRGenerateSQL().getTemplateDirCreateDB(), Preferences.TEMPLATE_CREATE_TABLE_CHECKS)  +
                Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS ;

        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_CHECK_NAME_WORD, mpdrCheck.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_CHECK_EXPRESSION_WORD, mpdrCheck.getMPDRParameter().getValue());

        return generateSQLCode;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

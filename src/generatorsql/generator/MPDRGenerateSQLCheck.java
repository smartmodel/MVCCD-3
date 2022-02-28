package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRCheck;
import preferences.Preferences;

public abstract class MPDRGenerateSQLCheck {

    public MPDRGenerateSQLCheck() {
    }

    public String generateSQLCreateCheck(MPDRCheck mpdrCheck) {
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirCreateDB(),
                Preferences.TEMPLATE_CREATE_TABLE_CHECKS,
                getMPDRGenerateSQL().mpdrModel) + Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS;

        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_CHECK_NAME_WORD, mpdrCheck.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_CHECK_EXPRESSION_WORD, mpdrCheck.getMPDRParameter().getValue());

        return generateSQLCode;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

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

    //Ajouté par Vincent
    public String generateSQLConsolidationAddCheck(MPDRCheck mpdrCheck){
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirAlterConsolidationDB(),
                Preferences.TEMPLATE_ALTER_TABLE_ADD_CHECK,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD, mpdrCheck.getParent().getParent().getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_CHECK_NAME_WORD, mpdrCheck.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_CHECK_EXPRESSION_WORD, mpdrCheck.getMPDRParameter().getValue());

        return generateSQLCode;
    }
    //Ajouté par Vincent
    public String generateSQLConsolidationDropCheck(MPDRCheck mpdrCheck){
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirAlterConsolidationDB(),
                Preferences.TEMPLATE_ALTER_TABLE_DROP_CHECK,
                getMPDRGenerateSQL().mpdrModel);

        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD, mpdrCheck.getParent().getParent().getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_CHECK_NAME_WORD, mpdrCheck.getName());

        return generateSQLCode;
    }


    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

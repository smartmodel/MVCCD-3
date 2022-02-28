package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRTable;
import preferences.Preferences;

public abstract class MPDRGenerateSQLPK {

    public MPDRGenerateSQLPK() {
    }

    public String generateSQLCreatePK(MPDRTable mpdrTable) {
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirCreateDB(),
                Preferences.TEMPLATE_CREATE_TABLE_PK,
                getMPDRGenerateSQL().mpdrModel) + Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS ;
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_PK_NAME_WORD, mpdrTable.getMPDRPK().getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_PK_COLUMNS_WORD, mpdrTable.getMPDRPK().getParametersNameAsStr());

        return generateSQLCode;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

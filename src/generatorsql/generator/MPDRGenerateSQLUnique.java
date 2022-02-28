package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRUnique;
import preferences.Preferences;

public abstract class MPDRGenerateSQLUnique {

    public MPDRGenerateSQLUnique() {
    }

    public String generateSQLCreateUnique(MPDRUnique mpdrUnique) {
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirCreateDB(),
                Preferences.TEMPLATE_CREATE_TABLE_UNIQUES,
                getMPDRGenerateSQL().mpdrModel) + Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS ;
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_UNIQUE_NAME_WORD, mpdrUnique.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_UNIQUE_COLUMNS_WORD, mpdrUnique.getParametersNameAsStr());

        return generateSQLCode;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

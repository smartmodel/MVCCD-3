package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRColumn;
import preferences.Preferences;

public abstract class MPDRGenerateSQLIndexColumn {


    public MPDRGenerateSQLIndexColumn() {
    }

    public String generateSQLCreateIndexColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirCreateDB(),
                Preferences.TEMPLATE_CREATE_INDEX_COLUMNS,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_NAME_WORD, mpdrColumn.getName());

        return generateSQLCode;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

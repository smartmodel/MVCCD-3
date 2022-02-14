package generatorsql.generator;

import mpdr.MPDRColumn;
import preferences.Preferences;
import utilities.ReadFile;

public abstract class MPDRGenerateSQLIndexColumn {


    public MPDRGenerateSQLIndexColumn() {
    }

    public String generateSQLCreateIndexColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = "";
        generateSQLCode += ReadFile.fileToString(getMPDRGenerateSQL().getTemplateDirCreateDB(), Preferences.TEMPLATE_CREATE_INDEX_COLUMNS) +
                    Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS;

        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_NAME_WORD, mpdrColumn.getName());

        return generateSQLCode;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

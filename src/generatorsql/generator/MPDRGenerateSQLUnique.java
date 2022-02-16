package generatorsql.generator;

import mpdr.MPDRUnique;
import preferences.Preferences;
import utilities.files.FileRead;

public abstract class MPDRGenerateSQLUnique {

    public MPDRGenerateSQLUnique() {
    }

    public String generateSQLCreateUnique(MPDRUnique mpdrUnique) {
        String generateSQLCode = "";

        generateSQLCode += FileRead.readToString(getMPDRGenerateSQL().getTemplateDirCreateDB(), Preferences.TEMPLATE_CREATE_TABLE_UNIQUES)  +
                Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS ;

        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_UNIQUE_NAME_WORD, mpdrUnique.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_UNIQUE_COLUMNS_WORD, mpdrUnique.getParametersNameAsStr());

        return generateSQLCode;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

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

    public String generateSQLCreateUniqueConsolidation(MPDRUnique mpdrUnique) {
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirAlterDB(),
                Preferences.TEMPLATE_ALTER_TABLE_CREATE_UNIQUE,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD, mpdrUnique.getParent().getParent().getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_UNIQUE_NAME_WORD, mpdrUnique.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_UNIQUE_COLUMNS_WORD, mpdrUnique.getParametersNameAsStr());

        return generateSQLCode;
    }

    public String generateSQLDropUniqueConsolidation(MPDRUnique mpdrUnique) {
        //getTemplateDirAlterDB permet de savoir qu'il doit aller chercher le fichier txt dans le dossier Alter
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirAlterDB(),
                Preferences.TEMPLATE_ALTER_TABLE_DROP_UNIQUE,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD, mpdrUnique.getParent().getParent().getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_UNIQUE_NAME_WORD, mpdrUnique.getName());

        return generateSQLCode;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

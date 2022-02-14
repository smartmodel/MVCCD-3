package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRColumn;
import mpdr.MPDRIndex;
import preferences.Preferences;
import utilities.ReadFile;

public abstract class MPDRGenerateSQLIndex {

    public MPDRGenerateSQLIndex() {
   }

    public String generateSQLDropIndex(MPDRIndex mpdrIndex) {
        String generateSQLCode = "";
        generateSQLCode += ReadFile.fileToString(getMPDRGenerateSQL().getTemplateDirDropDB(), Preferences.TEMPLATE_DROP_INDEX);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode,
                Preferences.MPDR_INDEX_NAME_WORD, mpdrIndex.getName());
        return generateSQLCode;
    }

    public String generateSQLCreateIndex(MPDRIndex mpdrIndex) {
        String generateSQLCode = "";

        //Génération de l'index
        generateSQLCode += ReadFile.fileToString(getMPDRGenerateSQL().getTemplateDirCreateDB(), Preferences.TEMPLATE_CREATE_INDEX) ;
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_INDEX_NAME_WORD, mpdrIndex.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD, mpdrIndex.getMDRTableAccueil().getName());

        //Génération des colonnes
        String tabsApplicable = MPDRGenerateSQLUtil.tabsApplicable(generateSQLCode, Preferences.TEMPLATE_CREATE_INDEX_COLUMNS);
        String columnsInCreateIndex = generateSQLCreateColumns(mpdrIndex, tabsApplicable);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.TEMPLATE_CREATE_INDEX_COLUMNS, columnsInCreateIndex);

        generateSQLCode = MPDRGenerateSQLUtil.cleanSeparatorArguments(generateSQLCode);

        return generateSQLCode ;
    }

    public String generateSQLCreateColumns(MPDRIndex mpdrIndex, String tabsApplicable){
        String generateSQLCode = "";
        // Avec nos règles de conformité, une table doit avoir au moins une colonne,

        boolean firstColumn = true;
        for (MPDRColumn mpdrColumn : mpdrIndex.getMPDRColumns()) {
            if (!firstColumn) {
                generateSQLCode +=  System.lineSeparator() + tabsApplicable;
            }
            generateSQLCode += getMPDRGenerateSQLIndexColumn().generateSQLCreateIndexColumn(mpdrColumn);
            firstColumn = false;
        }
        return generateSQLCode;
    }

    protected abstract MPDRGenerateSQLIndexColumn getMPDRGenerateSQLIndexColumn();

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;


}

package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRColumn;
import mpdr.MPDRIndex;
import preferences.Preferences;

public abstract class MPDRGenerateSQLIndex {

    public MPDRGenerateSQLIndex() {
   }

    public String generateSQLDropIndex(MPDRIndex mpdrIndex) {
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirDropDB(),
                Preferences.TEMPLATE_DROP_INDEX,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MPDR_INDEX_NAME_WORD, mpdrIndex.getName());
        return generateSQLCode;
    }

    public String generateSQLCreateIndex(MPDRIndex mpdrIndex) {
        //Génération de l'index
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirCreateDB(),
                Preferences.TEMPLATE_CREATE_INDEX,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_INDEX_NAME_WORD, mpdrIndex.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD, mpdrIndex.getMDRTableAccueil().getName());

        //Génération des colonnes
        String tabsApplicable = MPDRGenerateSQLUtil.tabsApplicable(generateSQLCode, Preferences.TEMPLATE_CREATE_INDEX_COLUMNS);
        String columnsInCreateIndex = generateSQLCreateColumns(mpdrIndex, tabsApplicable);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.TEMPLATE_CREATE_INDEX_COLUMNS, columnsInCreateIndex);

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

    public String generateSQLConsolidationDropIndex(MPDRIndex mpdrIndex) {
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirAlterConsolidationDB(),
                Preferences.TEMPLATE_DROP_INDEX,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MPDR_INDEX_NAME_WORD, mpdrIndex.getName());
        return generateSQLCode;
    }

    protected abstract MPDRGenerateSQLIndexColumn getMPDRGenerateSQLIndexColumn();

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;


}

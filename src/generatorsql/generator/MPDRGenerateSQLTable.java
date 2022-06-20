package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRCheck;
import mpdr.MPDRTable;
import mpdr.MPDRUnique;
import mpdr.interfaces.IMPDRColumn;
import mpdr.interfaces.IMPDRTable;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;

public abstract class MPDRGenerateSQLTable {

    public MPDRGenerateSQLTable() {
   }

    public String generateSQLDropTable(IMPDRTable impdrTable) {
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirDropDB(),
                Preferences.TEMPLATE_DROP_TABLE,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MDR_TABLE_NAME_WORD, impdrTable.getName());
        return generateSQLCode;
    }

    public String generateSQLCreateTable(IMPDRTable impdrTable) {
        //Génération de la table
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirCreateDB(),
                Preferences.TEMPLATE_CREATE_TABLE,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MDR_TABLE_NAME_WORD, impdrTable.getName());


        //Génération des colonnes
        String tabsApplicable = MPDRGenerateSQLUtil.tabsApplicable(generateSQLCode, Preferences.TEMPLATE_CREATE_TABLE_COLUMNS);
        String columnsInCreateTable = generateSQLCreateColumns(impdrTable, tabsApplicable);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.TEMPLATE_CREATE_TABLE_COLUMNS, columnsInCreateTable);

        if (impdrTable instanceof MPDRTable) {
            MPDRTable mpdrTable = (MPDRTable)impdrTable;
            //Génération de la contrainte de PK
            MPDRGenerateSQLPK mpdrGenerateSQLPK = getMPDRGenerateSQLPK();
            String pkInCreateTable = mpdrGenerateSQLPK.generateSQLCreatePK(mpdrTable);
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.TEMPLATE_CREATE_TABLE_PK, pkInCreateTable);

            //Génération des contraintes de CHECK
            tabsApplicable = MPDRGenerateSQLUtil.tabsApplicable(generateSQLCode, Preferences.TEMPLATE_CREATE_TABLE_CHECKS);
            String checksInCreateTable = generateSQLCreateChecks(mpdrTable, tabsApplicable);
            if (StringUtils.isNotEmpty(checksInCreateTable)) {
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.TEMPLATE_CREATE_TABLE_CHECKS, checksInCreateTable);
            } else {
                generateSQLCode = getMPDRGenerateSQL().nullifyKey(generateSQLCode, System.lineSeparator() + tabsApplicable, Preferences.TEMPLATE_CREATE_TABLE_CHECKS, "");
            }

            //Génération des contraintes UNIQUE
            tabsApplicable = MPDRGenerateSQLUtil.tabsApplicable(generateSQLCode, Preferences.TEMPLATE_CREATE_TABLE_UNIQUES);
            String uniquesInCreateTable = generateSQLCreateUniques(mpdrTable, tabsApplicable);
            if (StringUtils.isNotEmpty(uniquesInCreateTable)) {
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.TEMPLATE_CREATE_TABLE_UNIQUES, uniquesInCreateTable);
            } else {
                generateSQLCode = getMPDRGenerateSQL().nullifyKey(generateSQLCode, System.lineSeparator() + tabsApplicable, Preferences.TEMPLATE_CREATE_TABLE_UNIQUES, "");
            }

        } else {
            // tables de journalisation MPDRTableJnal
            generateSQLCode = getMPDRGenerateSQL().nullifyKey(generateSQLCode, System.lineSeparator() + tabsApplicable, Preferences.TEMPLATE_CREATE_TABLE_PK, "");
            generateSQLCode = getMPDRGenerateSQL().nullifyKey(generateSQLCode, System.lineSeparator() + tabsApplicable, Preferences.TEMPLATE_CREATE_TABLE_CHECKS, "");
            generateSQLCode = getMPDRGenerateSQL().nullifyKey(generateSQLCode, System.lineSeparator() + tabsApplicable, Preferences.TEMPLATE_CREATE_TABLE_UNIQUES, "");
        }
        generateSQLCode = MPDRGenerateSQLUtil.cleanSeparatorArguments(generateSQLCode);
        return generateSQLCode ;
    }

    public String generateSQLCreateColumns(IMPDRTable impdrTable, String tabsApplicable){
        String generateSQLCode = "";
        // Avec nos règles de conformité, une table doit avoir au moins une colonne,

        boolean firstColumn = true;
        for (IMPDRColumn impdrColumn : impdrTable.getIMPDRColumnsSortDefault()) {
            if (!firstColumn) {
                generateSQLCode +=  System.lineSeparator() + tabsApplicable;
            }
            generateSQLCode += getMPDRGenerateSQLColumn().generateSQLCreateTableColumn(impdrColumn);
            firstColumn = false;
        }
        return generateSQLCode;
    }

    public String generateSQLCreateChecks(MPDRTable mpdrTable, String tabsApplicable) {
        String generateSQLCode = "";

        boolean firstCheck = true;
        for (MPDRCheck mpdrCheck : mpdrTable.getMPDRChecks()) {
            if (!firstCheck) {
                generateSQLCode +=  System.lineSeparator() + tabsApplicable;
            }
            generateSQLCode += getMPDRGenerateSQLCheck().generateSQLCreateCheck(mpdrCheck);
            firstCheck = false;
        }
        return generateSQLCode;
    }

    protected String generateSQLCreateUniques(MPDRTable mpdrTable, String tabsApplicable) {
        String generateSQLCode = "";

        boolean firstUnique = true;
        for (MPDRUnique mpdrUnique : mpdrTable.getMPDRUniques()) {
            if (!firstUnique) {
                generateSQLCode +=  System.lineSeparator() + tabsApplicable;
            }
            generateSQLCode += getMPDRGenerateSQLUnique().generateSQLCreateUnique(mpdrUnique);
            firstUnique = false;
        }
        return generateSQLCode;

    }

    protected abstract MPDRGenerateSQLUnique getMPDRGenerateSQLUnique();


    protected abstract MPDRGenerateSQLPK getMPDRGenerateSQLPK();

    protected abstract MPDRGenerateSQLTableColumn getMPDRGenerateSQLColumn();

    protected abstract MPDRGenerateSQLCheck getMPDRGenerateSQLCheck();

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;


}

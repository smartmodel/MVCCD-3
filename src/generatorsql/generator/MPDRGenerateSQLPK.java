package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRPK;
import mpdr.MPDRTable;
import mpdr.oracle.MPDROraclePK;
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

    //Ajouté par Vincent
    public String generateSQLAddPKConsolidation(MPDRPK mpdrPk) {
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirAlterConsolidationDB(),
                Preferences.TEMPLATE_ALTER_TABLE_ADD_PK,
                getMPDRGenerateSQL().mpdrModel);

        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD, mpdrPk.getParent().getParent().getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_PK_NAME_WORD, mpdrPk.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_PK_COLUMNS_WORD,mpdrPk.getParametersNameAsStr());

        return generateSQLCode;
    }
    //Ajouté par Vincent
    public String generateSQLDropPKConsolidation(MPDRPK mpdrPk) {
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirAlterConsolidationDB(),
                Preferences.TEMPLATE_ALTER_TABLE_DROP_PK_CASCADE,
                getMPDRGenerateSQL().mpdrModel);

        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD, mpdrPk.getParent().getParent().getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_PK_NAME_WORD, mpdrPk.getName());

        return generateSQLCode;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

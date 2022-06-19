package consolidationMpdrDb.syncGenerator;

import consolidationMpdrDb.comparator.oracle.OracleComparatorDb;
import generatorsql.generator.oracle.MPDROracleGenerateSQL;
import generatorsql.generator.oracle.MPDROracleGenerateSQLTable;
import generatorsql.generator.oracle.MPDROracleGenerateSQLTableColumn;
import generatorsql.generator.oracle.MPDROracleGenerateSQLUnique;
import mpdr.*;
import preferences.Preferences;

public class OracleSyncGeneratorSQL {


    MPDROracleGenerateSQL mpdrOracleGenerateSQL;
    MPDROracleGenerateSQLTable mpdrOracleGenerateSQLTable;
    MPDROracleGenerateSQLTableColumn mpdrOracleGenerateSQLTableColumn;
    MPDROracleGenerateSQLUnique mpdrOracleGenerateSQLUnique;
    OracleComparatorDb oracleComparatorDb;

    public OracleSyncGeneratorSQL(MPDRModel mpdrModel, OracleComparatorDb oracleComparatorDb) {
        this.mpdrOracleGenerateSQL = new MPDROracleGenerateSQL(mpdrModel);
        this.mpdrOracleGenerateSQLTable = new MPDROracleGenerateSQLTable(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLTableColumn = new MPDROracleGenerateSQLTableColumn(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLUnique = new MPDROracleGenerateSQLUnique(mpdrOracleGenerateSQL);
        this.oracleComparatorDb = oracleComparatorDb;
    }

    public String delimiter(){
        return Preferences.SYSTEM_LINE_SEPARATOR + getDelimiterInstructions() + Preferences.SYSTEM_LINE_SEPARATOR;
    }
    public String getDelimiterInstructions() {
        return MPDRDB.ORACLE.getDelimiterInstructions();
    }

    public String sync(){
        StringBuilder generateSQLCodeSync = new StringBuilder();
        String message ; //A voir si nécessaire
        generateSQLCodeSync.append(syncTablesToDrop());
        generateSQLCodeSync.append(syncUniqueToDrop());
        generateSQLCodeSync.append(syncColumnsToDrop());
        generateSQLCodeSync.append(syncTablesToCreate());
        generateSQLCodeSync.append(syncColumnsToAdd());
        generateSQLCodeSync.append(syncColumnsToModify());
        generateSQLCodeSync.append(syncUniqueToAdd());
        System.out.println(generateSQLCodeSync);
        return generateSQLCodeSync.toString();
    }

    public String syncTablesToCreate() {
        StringBuilder code = new StringBuilder();
            for (MPDRTable mpdrTableToCreate : oracleComparatorDb.getMpdrTablesToCreate()) {
                code.append(mpdrOracleGenerateSQLTable.generateSQLCreateTable(mpdrTableToCreate));
                code.append(delimiter());
            }
        return code.toString();
    }

    public String syncTablesToDrop(){
        StringBuilder code=new StringBuilder();
            for (MPDRTable mpdrTableToDrop : oracleComparatorDb.getMpdrTablesToDrop()) {
                code.append(mpdrOracleGenerateSQLTable.generateSQLDropTable(mpdrTableToDrop));
                code.append(delimiter());
            }
        return code.toString();
    }

    public String syncColumnsToDrop(){
        StringBuilder code=new StringBuilder();
        for (MPDRColumn mpdrColumn : oracleComparatorDb.getMpdrColumnsToDrop()) {
            code.append(mpdrOracleGenerateSQLTableColumn.generateSQLDropColumn(mpdrColumn));
            code.append(delimiter());
        }

        return code.toString();
    }

    public String syncColumnsToAdd(){
        StringBuilder code=new StringBuilder();
        for (MPDRColumn mpdrColumn : oracleComparatorDb.getMpdrColumnsToAdd()) {
            code.append(mpdrOracleGenerateSQLTableColumn.generateSQLAddColumn(mpdrColumn));
            code.append(delimiter());
        }

        return code.toString();
    }

    public String syncColumnsToModify(){
        StringBuilder code=new StringBuilder();
        for (MPDRColumn mpdrColumn : oracleComparatorDb.getMpdrColumnsToModify()) {
            code.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumn(mpdrColumn));
            code.append(delimiter());
        }

        return code.toString();
    }

    public String syncUniqueToAdd(){
        StringBuilder code = new StringBuilder();
            for (MPDRUnique mpdrUniqueToCreate : oracleComparatorDb.getMpdrUniquesToCreate()) {
                code.append(mpdrOracleGenerateSQLUnique.generateSQLCreateUniqueConsolidation(mpdrUniqueToCreate));
                code.append(delimiter());
        }
        return code.toString();
    }
    public String syncUniqueToDrop(){
        StringBuilder code = new StringBuilder();
            for (MPDRUnique mpdrUniqueToDrop : oracleComparatorDb.getMpdrUniquesToDrop()) {
                //DROPPER pas create
                code.append(mpdrOracleGenerateSQLUnique.generateSQLDropUniqueConsolidation(mpdrUniqueToDrop));
                code.append(delimiter());
            }
        return code.toString();
    }

}

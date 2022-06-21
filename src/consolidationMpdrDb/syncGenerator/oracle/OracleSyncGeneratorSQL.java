package consolidationMpdrDb.syncGenerator.oracle;

import consolidationMpdrDb.comparator.oracle.OracleComparatorDb;
import generatorsql.generator.oracle.*;
import messages.MessagesBuilder;
import mpdr.*;
import preferences.Preferences;

public class OracleSyncGeneratorSQL {


    private MPDROracleGenerateSQL mpdrOracleGenerateSQL;
    private MPDROracleGenerateSQLTable mpdrOracleGenerateSQLTable;
    private MPDROracleGenerateSQLTableColumn mpdrOracleGenerateSQLTableColumn;
    private MPDROracleGenerateSQLPK mpdrOracleGenerateSQLPK;
    private MPDROracleGenerateSQLUnique mpdrOracleGenerateSQLUnique;
    private MPDROracleGenerateSQLCheck mpdrOracleGenerateSQLCheck;
    private MPDROracleGenerateSQLFK mpdrOracleGenerateSQLFK;
    private OracleComparatorDb oracleComparatorDb;

    public OracleSyncGeneratorSQL(MPDRModel mpdrModel, OracleComparatorDb oracleComparatorDb) {
        this.mpdrOracleGenerateSQL = new MPDROracleGenerateSQL(mpdrModel);
        this.mpdrOracleGenerateSQLTable = new MPDROracleGenerateSQLTable(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLTableColumn = new MPDROracleGenerateSQLTableColumn(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLPK = new MPDROracleGenerateSQLPK(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLUnique = new MPDROracleGenerateSQLUnique(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLCheck = new MPDROracleGenerateSQLCheck(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLFK = new MPDROracleGenerateSQLFK(mpdrOracleGenerateSQL);
        this.oracleComparatorDb = oracleComparatorDb;

        //Permet d'exécuter le mécanisme de comparaison
        this.oracleComparatorDb.compare();
    }

    public String delimiter() {
        return Preferences.SYSTEM_LINE_SEPARATOR + getDelimiterInstructions() + Preferences.SYSTEM_LINE_SEPARATOR;
    }

    public String getDelimiterInstructions() {
        return MPDRDB.ORACLE.getDelimiterInstructions();
    }

    public String sync() {
        StringBuilder generateSQLCodeSync = new StringBuilder();
        generateSQLCodeSync.append(" ");
        String message; //A voir si nécessaire
        message = MessagesBuilder.getMessagesProperty("generate.sql.drop.tables");
        generateSQLCodeSync.append(syncUniqueToDrop());
        generateSQLCodeSync.append(syncCheckToDrop());
        generateSQLCodeSync.append(syncFkToDrop());
        generateSQLCodeSync.append(syncPkToDrop());
        generateSQLCodeSync.append(syncColumnsToDrop());
        generateSQLCodeSync.append(syncTablesToDrop());

        generateSQLCodeSync.append(syncTablesToCreate());
        generateSQLCodeSync.append(syncColumnsToAdd());
        generateSQLCodeSync.append(syncColumnsToModify());
        generateSQLCodeSync.append(syncColumnsToModifyAddNotNull());
        generateSQLCodeSync.append(syncColumnsToModifyDropNotNull());
        generateSQLCodeSync.append(syncColumnsToModifyAddOrModifyDefault());
        generateSQLCodeSync.append(syncColumnsToModifyDropNotDefault());
        generateSQLCodeSync.append(syncPkToAdd());
        generateSQLCodeSync.append(syncUniqueToAdd());
        generateSQLCodeSync.append(syncCheckToAdd());
        generateSQLCodeSync.append(syncFkToAdd());

        //S'il n'y a rien à générer, affichage d'un message d'information à l'utilisateur
        if(generateSQLCodeSync.toString().equals(" ")){
            generateSQLCodeSync.append("La structure du SGBD-R est conforme au modèle");
        }

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

    public String syncTablesToDrop() {
        StringBuilder code = new StringBuilder();
        for (MPDRTable mpdrTableToDrop : oracleComparatorDb.getDbTablesToDrop()) {
            code.append(mpdrOracleGenerateSQLTable.generateSQLDropTableConsolidation(mpdrTableToDrop));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncColumnsToDrop() {
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumn : oracleComparatorDb.getDbColumnsToDrop()) {
            code.append(mpdrOracleGenerateSQLTableColumn.generateSQLDropColumn(mpdrColumn));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncColumnsToAdd() {
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumn : oracleComparatorDb.getMpdrColumnsToAdd()) {
            code.append(mpdrOracleGenerateSQLTableColumn.generateSQLAddColumn(mpdrColumn));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncColumnsToModify() {
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnToModify : oracleComparatorDb.getMpdrColumnsToModify()) {
            code.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumn(mpdrColumnToModify));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncColumnsToModifyAddNotNull(){
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnAddNN : oracleComparatorDb.getMpdrColumnsToModifyAddNotNull()) {
            code.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumnAddNotNul(mpdrColumnAddNN));
            code.append(delimiter());
        }
        return code.toString();
    }
    public String syncColumnsToModifyDropNotNull(){
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnDropNN : oracleComparatorDb.getMpdrColumnsToModifyDropNotNull()) {
            code.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumnDropNotNul(mpdrColumnDropNN));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncColumnsToModifyAddOrModifyDefault(){
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnAddOrModifDef : oracleComparatorDb.getMpdrColumnsToModifyAddOrModifyDefault()) {
            code.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumnAddOrModifyDefault(mpdrColumnAddOrModifDef));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncColumnsToModifyDropNotDefault(){
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnDropNN : oracleComparatorDb.getMpdrColumnsToModifyDropDefault()) {
            code.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumnDropDefault(mpdrColumnDropNN));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncPkToAdd(){
        StringBuilder code = new StringBuilder();
        for (MPDRPK mpdrPkToAdd : oracleComparatorDb.getMpdrPksToAdd()) {
            code.append(mpdrOracleGenerateSQLPK.generateSQLAddPKConsolidation(mpdrPkToAdd));
            code.append(delimiter());
        }
        return code.toString();
    }
    public String syncPkToDrop(){
        StringBuilder code = new StringBuilder();
        for (MPDRPK dbPk : oracleComparatorDb.getDbPksToDrop()) {
            code.append(mpdrOracleGenerateSQLPK.generateSQLDropPKConsolidation(dbPk));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncUniqueToAdd() {
        StringBuilder code = new StringBuilder();
        for (MPDRUnique mpdrUniqueToAdd : oracleComparatorDb.getMpdrUniquesToAdd()) {
            code.append(mpdrOracleGenerateSQLUnique.generateSQLCreateUniqueConsolidation(mpdrUniqueToAdd));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncUniqueToDrop() {
        StringBuilder code = new StringBuilder();
        for (MPDRUnique mpdrUniqueToDrop : oracleComparatorDb.getDbUniquesToDrop()) {
            //DROPPER pas create
            code.append(mpdrOracleGenerateSQLUnique.generateSQLDropUniqueConsolidation(mpdrUniqueToDrop));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncCheckToAdd(){
        StringBuilder code = new StringBuilder();
        for (MPDRCheck mpdrCheck : oracleComparatorDb.getMpdrChecksToAdd()) {
            code.append(mpdrOracleGenerateSQLCheck.generateSQLAddCheck(mpdrCheck));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncCheckToDrop(){
        StringBuilder code = new StringBuilder();
        for (MPDRCheck dbCheck : oracleComparatorDb.getDbChecksToDrop()) {
            code.append(mpdrOracleGenerateSQLCheck.generateSQLDropCheck(dbCheck));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncFkToAdd(){
        StringBuilder code = new StringBuilder();
        for (MPDRFK mpdrFkToAdd : oracleComparatorDb.getMpdrFksToAdd()) {
            code.append(mpdrOracleGenerateSQLFK.generateSQLFK(mpdrFkToAdd));
            code.append(delimiter());
        }
        return code.toString();
    }
    public String syncFkToDrop(){
        StringBuilder code = new StringBuilder();
        for (MPDRFK dbFkToDrop : oracleComparatorDb.getDbFksToDrop()) {
            code.append(mpdrOracleGenerateSQLFK.generateSQLFkToDropConsolidation(dbFkToDrop));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncFKAddDeleteCascade(){
        StringBuilder code = new StringBuilder();
        for (MPDRFK mpdrfk : oracleComparatorDb.getMpdrFksToAddDeleteCascade()) {
            code.append(mpdrOracleGenerateSQLFK.generateSQLFKWithDeleteCascadeOption(mpdrfk));
            code.append(delimiter());
        }
        return code.toString();
    }
}

package consolidationMpdrDb.syncGenerator.oracle;

import consolidationMpdrDb.comparator.oracle.OracleComparatorDb;
import consolidationMpdrDb.syncGenerator.SyncGeneratorSQL;
import generatorsql.generator.oracle.*;
import mpdr.*;
import mpdr.interfaces.IMPDRModelRequirePackage;
import mpdr.interfaces.IMPDRTableRequirePackage;
import mpdr.tapis.MPDRPackage;
import mpdr.tapis.MPDRTrigger;
import preferences.Preferences;


public class SyncGeneratorSQLOracle extends SyncGeneratorSQL {


    private MPDROracleGenerateSQL mpdrOracleGenerateSQL;
    private MPDROracleGenerateSQLTable mpdrOracleGenerateSQLTable;
    private MPDROracleGenerateSQLTableColumn mpdrOracleGenerateSQLTableColumn;
    private MPDROracleGenerateSQLPK mpdrOracleGenerateSQLPK;
    private MPDROracleGenerateSQLUnique mpdrOracleGenerateSQLUnique;
    private MPDROracleGenerateSQLCheck mpdrOracleGenerateSQLCheck;
    private MPDROracleGenerateSQLFK mpdrOracleGenerateSQLFK;
    private MPDROracleGenerateSQLIndex mpdrOracleGenerateSQLIndex;
    private MPDROracleGenerateSQLSequence mpdrOracleGenerateSQLSequence;
    private MPDROracleGenerateSQLTrigger mpdrOracleGenerateSQLTrigger;
    private MPDROracleGenerateSQLPackage mpdrOracleGenerateSQLPackage;
    private OracleComparatorDb oracleComparatorDb;
    private String delimiter = delimiter();

    public SyncGeneratorSQLOracle(MPDRModel mpdrModel, OracleComparatorDb oracleComparatorDb) {
        this.mpdrOracleGenerateSQL = new MPDROracleGenerateSQL(mpdrModel);
        this.oracleComparatorDb = oracleComparatorDb;
        init();
    }

    private void init(){
        this.mpdrOracleGenerateSQLTable = new MPDROracleGenerateSQLTable(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLTableColumn = new MPDROracleGenerateSQLTableColumn(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLPK = new MPDROracleGenerateSQLPK(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLUnique = new MPDROracleGenerateSQLUnique(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLCheck = new MPDROracleGenerateSQLCheck(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLFK = new MPDROracleGenerateSQLFK(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLIndex = new MPDROracleGenerateSQLIndex(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLSequence = new MPDROracleGenerateSQLSequence(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLTrigger = new MPDROracleGenerateSQLTrigger(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLPackage = new MPDROracleGenerateSQLPackage(mpdrOracleGenerateSQL);

        //Permet d'exécuter le mécanisme de comparaison
        this.oracleComparatorDb.compare();
    }

    public String delimiter() {
        return Preferences.SYSTEM_LINE_SEPARATOR + getDelimiterInstructions() + Preferences.SYSTEM_LINE_SEPARATOR;
    }

    public String getDelimiterInstructions() {
        return MPDRDB.ORACLE.getDelimiterInstructions();
    }

    public String syncOrderByTable() {

        StringBuilder generateSQLCodeSync = new StringBuilder();
        generateSQLCodeSync.append(" ");

        /*Suppression des tables en cascade efface :
            - tables
            - colonnes
                - contraintes not nul
            - contraintes uniques, check et pk
            - contraintes fk de la table
            - contraintes fk qui pointent sur la table
            - indexes
            - triggers
            -----------------------------
            - packages NON
            - séquences NON
         */
        generateSQLCodeSync.append(syncTablesToDrop());
        //Création des nouvelles tables
        generateSQLCodeSync.append(syncTablesToCreate());


        for (MPDRTable mpdrTableToModifiy : oracleComparatorDb.getMpdrTablesSameName()) {
            //suppressions
            generateSQLCodeSync.append(syncUniqueToDrop(mpdrTableToModifiy));
            generateSQLCodeSync.append(syncCheckToDrop(mpdrTableToModifiy));
            generateSQLCodeSync.append(syncFkToDrop(mpdrTableToModifiy));
            generateSQLCodeSync.append(syncFkDropDeleteCascade(mpdrTableToModifiy));
            generateSQLCodeSync.append(syncPkToDrop(mpdrTableToModifiy));
            generateSQLCodeSync.append(syncColumnsToDrop(mpdrTableToModifiy));
            //Ajouts et modifications
            generateSQLCodeSync.append(syncColumnsToAdd(mpdrTableToModifiy));
            generateSQLCodeSync.append(syncColumnsToModify(mpdrTableToModifiy));
            generateSQLCodeSync.append(syncColumnsToModifyAddNotNull(mpdrTableToModifiy));
            generateSQLCodeSync.append(syncColumnsToModifyDropNotNull(mpdrTableToModifiy));
            generateSQLCodeSync.append(syncColumnsToModifyAddOrModifyDefault(mpdrTableToModifiy));
            generateSQLCodeSync.append(syncColumnsToModifyDropDefault(mpdrTableToModifiy));
            generateSQLCodeSync.append(syncPkToAdd(mpdrTableToModifiy));
            generateSQLCodeSync.append(syncUniqueToAdd(mpdrTableToModifiy));
            generateSQLCodeSync.append(syncCheckToAdd(mpdrTableToModifiy));
        }//Fin de boucle des tables identiques

        //Ajout des contraintes de clés étrangères
        generateSQLCodeSync.append(syncFKAddDeleteCascade());
        generateSQLCodeSync.append(syncFkToAdd());

        //Suppression et ajout des séquences, triggers et packages
        generateSQLCodeSync.append(syncSequenceToDrop());
        generateSQLCodeSync.append(syncSequenceNotInTableToDrop());
        generateSQLCodeSync.append(syncSequenceToCreate());
        generateSQLCodeSync.append(syncIndexToDrop());
        generateSQLCodeSync.append(syncIndexToCreate());
        generateSQLCodeSync.append(syncTriggerToDrop());
        generateSQLCodeSync.append(syncTriggerNotInTableToDrop());
        generateSQLCodeSync.append(syncPackageToDrop());
        generateSQLCodeSync.append(syncPackageNotInTableToDrop());
        //On redéfinit manuellement le délimiteur car ensuite, les instructions sont en bloc PL/SQL
        delimiter = Preferences.SYSTEM_LINE_SEPARATOR + "/" + Preferences.SYSTEM_LINE_SEPARATOR;
        generateSQLCodeSync.append(syncTriggerToCreateOrReplace());
        generateSQLCodeSync.append(syncTriggerToCreateForNewTable());
        generateSQLCodeSync.append(syncPackageToCreateOrReplace());
        generateSQLCodeSync.append(syncPackageToCreateForNewTable());

        //S'il n'y a rien à générer, affichage d'un message d'information à l'utilisateur
        if (generateSQLCodeSync.toString().equals(" ")) {
            generateSQLCodeSync.append("La structure du SGBD-R est conforme au modèle");
        }

        return generateSQLCodeSync.toString();
    }


    private String syncTablesToCreate() {
        StringBuilder code = new StringBuilder();
        for (MPDRTable mpdrTableToCreate : oracleComparatorDb.getMpdrTablesToCreate()) {
            code.append(mpdrOracleGenerateSQLTable.generateSQLCreateTable(mpdrTableToCreate));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncTablesToDrop() {
        StringBuilder code = new StringBuilder();
        for (MPDRTable mpdrTableToDrop : oracleComparatorDb.getDbTablesToDrop()) {
            code.append(mpdrOracleGenerateSQLTable.generateSQLConsolidationDropTable(mpdrTableToDrop));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncColumnsToDrop(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRColumn dbColumnToDrop : oracleComparatorDb.getDbColumnsToDrop()) {
            if (dbColumnToDrop.getMPDRTableAccueil().getName().equals(tableToModify.getName().toUpperCase())) {
                code.append(mpdrOracleGenerateSQLTableColumn.generateSQLConsolidationDropColumn(dbColumnToDrop));
                code.append(delimiter);
            }
        }
        return code.toString();
    }

    private String syncColumnsToAdd(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnToAdd : oracleComparatorDb.getMpdrColumnsToAdd()) {
            if (mpdrColumnToAdd.getMPDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())) {
                code.append(mpdrOracleGenerateSQLTableColumn.generateSQLConsolidationAddColumn(mpdrColumnToAdd));
                code.append(delimiter);
            }
        }
        return code.toString();
    }

    private String syncColumnsToModify(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnToModify : oracleComparatorDb.getMpdrColumnsToModify()) {
            if (mpdrColumnToModify.getMPDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())) {
                code.append(mpdrOracleGenerateSQLTableColumn.generateSQLConsolidationModifyColumn(mpdrColumnToModify));
                code.append(delimiter);
            }
        }
        return code.toString();
    }

    private String syncColumnsToModifyAddNotNull(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnAddNN : oracleComparatorDb.getMpdrColumnsToModifyAddNotNull()) {
            if (mpdrColumnAddNN.getMPDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())) {
                code.append(mpdrOracleGenerateSQLTableColumn.generateSQLConsolidationModifyColumnAddNotNul(mpdrColumnAddNN));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    private String syncColumnsToModifyDropNotNull(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnDropNN : oracleComparatorDb.getMpdrColumnsToModifyDropNotNull()) {
            if (mpdrColumnDropNN.getMPDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())) {
                code.append(mpdrOracleGenerateSQLTableColumn.generateSQLConsolidationModifyColumnDropNotNul(mpdrColumnDropNN));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    private String syncColumnsToModifyAddOrModifyDefault(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnAddOrModifDefault : oracleComparatorDb.getMpdrColumnsToModifyAddOrModifyDefault()) {
            if (mpdrColumnAddOrModifDefault.getMPDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())) {
                code.append(mpdrOracleGenerateSQLTableColumn.generateSQLConsolidationModifyColumnAddOrModifyDefault(mpdrColumnAddOrModifDefault));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    private String syncColumnsToModifyDropDefault(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumDropDefault : oracleComparatorDb.getMpdrColumnsToModifyDropDefault()) {
            if (mpdrColumDropDefault.getMPDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())) {
                code.append(mpdrOracleGenerateSQLTableColumn.generateSQLConsolidationModifyColumnDropDefault(mpdrColumDropDefault));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    private String syncPkToAdd(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRPK mpdrPkToAdd : oracleComparatorDb.getMpdrPksToAdd()) {
            if (mpdrPkToAdd.getMDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())) {
                code.append(mpdrOracleGenerateSQLPK.generateSQLConsolidationAddPK(mpdrPkToAdd));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    private String syncPkToDrop(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRPK dbPkToDrop : oracleComparatorDb.getDbPksToDrop()) {
            if (dbPkToDrop.getMDRTableAccueil().getName().equals(tableToModify.getName().toUpperCase())) {
                code.append(mpdrOracleGenerateSQLPK.generateSQLConsolidationDropPK(dbPkToDrop));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    private String syncUniqueToAdd(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRUnique mpdrUniqueToAdd : oracleComparatorDb.getMpdrUniquesToAdd()) {
            if (mpdrUniqueToAdd.getMDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())) {
                code.append(mpdrOracleGenerateSQLUnique.generateSQLConsolidationAddUnique(mpdrUniqueToAdd));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    private String syncUniqueToDrop(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRUnique dbUniqueToDrop : oracleComparatorDb.getDbUniquesToDrop()) {
            if (dbUniqueToDrop.getMDRTableAccueil().getName().equals(tableToModify.getName().toUpperCase())) {
                code.append(mpdrOracleGenerateSQLUnique.generateSQLConsolidationDropUnique(dbUniqueToDrop));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    private String syncCheckToAdd(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRCheck mpdrCheckToAdd : oracleComparatorDb.getMpdrChecksToAdd()) {
            if (mpdrCheckToAdd.getMDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())) {
                code.append(mpdrOracleGenerateSQLCheck.generateSQLConsolidationAddCheck(mpdrCheckToAdd));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    private String syncCheckToDrop(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRCheck dbCheckToDrop : oracleComparatorDb.getDbChecksToDrop()) {
            if (dbCheckToDrop.getMDRTableAccueil().getName().equals(tableToModify.getName().toUpperCase())) {
                code.append(mpdrOracleGenerateSQLCheck.generateSQLConsolidationDropCheck(dbCheckToDrop));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    private String syncFkToAdd() {
        StringBuilder code = new StringBuilder();
        for (MPDRFK mpdrFkToAdd : oracleComparatorDb.getMpdrFksToAdd()) {
            code.append(mpdrOracleGenerateSQLFK.generateSQLFK(mpdrFkToAdd));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncFkToDrop(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRFK dbFkToDrop : oracleComparatorDb.getDbFksToDrop()) {
            if (dbFkToDrop.getMDRTableAccueil().getName().equals(tableToModify.getName().toUpperCase())) {
                code.append(mpdrOracleGenerateSQLFK.generateSQLConsolidationFkToDrop(dbFkToDrop));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    private String syncFKAddDeleteCascade() {
        StringBuilder code = new StringBuilder();
        for (MPDRFK mpdrfk : oracleComparatorDb.getMpdrFksToAddDeleteCascade()) {
            code.append(mpdrOracleGenerateSQLFK.generateSQLConsolidationAddFKWithDeleteCascadeOption(mpdrfk));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncFkDropDeleteCascade(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRFK dbFkToDrop : oracleComparatorDb.getDbFksToDropDeleteCascade()) {
            if (dbFkToDrop.getMDRTableAccueil().getName().equals(tableToModify.getName().toUpperCase())) {
                code.append(mpdrOracleGenerateSQLFK.generateSQLConsolidationFkToDrop(dbFkToDrop));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    private String syncSequenceToCreate() {
        StringBuilder code = new StringBuilder();
        for (MPDRSequence mpdrSequenceToCreate : oracleComparatorDb.getMpdrSequencesToCreate()) {
            code.append(mpdrOracleGenerateSQLSequence.generateSQLCreateSequence(mpdrSequenceToCreate));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncSequenceToDrop() {
        StringBuilder code = new StringBuilder();
        for (MPDRSequence dbSequenceToDrop : oracleComparatorDb.getDbSequencesToDrop()) {
            code.append(mpdrOracleGenerateSQLSequence.generateSQLConsolidationDropSequence(dbSequenceToDrop));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncSequenceNotInTableToDrop() {
        StringBuilder code = new StringBuilder();
        for (String sequenceNotInTable : oracleComparatorDb.getDbFetcherOracle().getDbSequencesNotInTable()) {
            code.append(mpdrOracleGenerateSQLSequence.generateSQLConsolidationDropSequence(sequenceNotInTable));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncTriggerToDrop() {
        StringBuilder code = new StringBuilder();
        for (MPDRTrigger dbTriggerToDrop : oracleComparatorDb.getDbTriggersToDrop()) {
            code.append(mpdrOracleGenerateSQLTrigger.generateSQLConsolidationDropTrigger(dbTriggerToDrop));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncTriggerNotInTableToDrop() {
        StringBuilder code = new StringBuilder();
        for (String triggerNotInTable : oracleComparatorDb.getDbFetcherOracle().getDbTriggersNotInTable()) {
            code.append(mpdrOracleGenerateSQLTrigger.generateSQLConsolidationDropTrigger(triggerNotInTable));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncTriggerToCreateOrReplace() {
        StringBuilder code = new StringBuilder();
        for (MPDRTrigger mpdrTriggerToCreateOrReplace : oracleComparatorDb.getMpdrTriggersToCreateOrReplace()) {
            code.append(mpdrOracleGenerateSQLTrigger.generateSQLCreateTrigger(mpdrTriggerToCreateOrReplace));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncPackageToDrop() {
        StringBuilder code = new StringBuilder();
        for (MPDRPackage dbPackageToDrop : oracleComparatorDb.getDbPackagesToDrop()) {
            code.append(mpdrOracleGenerateSQLPackage.generateSQLConsolidationDropPackageAndBody(dbPackageToDrop));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncPackageNotInTableToDrop() {
        StringBuilder code = new StringBuilder();
        for (String packageNotInTable : oracleComparatorDb.getDbFetcherOracle().getDbPackagesNotInTable()) {
            code.append(mpdrOracleGenerateSQLPackage.generateSQLConsolidationDropPackageAndBody(packageNotInTable));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncPackageToCreateOrReplace() {
        StringBuilder code = new StringBuilder();
        for (MPDRPackage mpdrPackageToCreateOrReplace : oracleComparatorDb.getMpdrPackagesToCreateOrReplace()) {
            code.append(mpdrOracleGenerateSQLPackage.generateSQLCreatePackage(mpdrPackageToCreateOrReplace));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncTriggerToCreateForNewTable() {
        StringBuilder code = new StringBuilder();
        for (MPDRTable mpdrTableToCreate : oracleComparatorDb.getMpdrTablesToCreate()) {
            if (mpdrTableToCreate.getMPDRTriggers() != null) {
                for (MPDRTrigger mpdrTrigger : mpdrTableToCreate.getMPDRTriggers()) {
                    code.append(mpdrOracleGenerateSQLTrigger.generateSQLCreateTrigger(mpdrTrigger));
                    code.append(delimiter);
                }
            }
        }
        return code.toString();
    }

    private String syncPackageToCreateForNewTable() {
        StringBuilder code = new StringBuilder();
        if (oracleComparatorDb.getMpdrModel() instanceof IMPDRModelRequirePackage) {
            for (MPDRTable mpdrTable : oracleComparatorDb.getMpdrTablesToCreate()) {
                if (((IMPDRTableRequirePackage) mpdrTable).getMPDRPackages() != null) {
                    for (MPDRPackage mpdrPackage : ((IMPDRTableRequirePackage) mpdrTable).getMPDRPackages()) {
                        code.append(mpdrOracleGenerateSQLPackage.generateSQLCreatePackage(mpdrPackage));
                        code.append(delimiter());
                    }
                }
            }
        }
        return code.toString();
    }

    private String syncIndexToCreate() {
        StringBuilder code = new StringBuilder();
        for (MPDRIndex mpdrIndexToCreate : oracleComparatorDb.getMpdrIndexsToCreate()) {
            code.append(mpdrOracleGenerateSQLIndex.generateSQLCreateIndex(mpdrIndexToCreate));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncIndexToDrop() {
        StringBuilder code = new StringBuilder();
        for (MPDRIndex dbIndexToDrop : oracleComparatorDb.getDbIndexsToDrop()) {
            code.append(mpdrOracleGenerateSQLIndex.generateSQLConsolidationDropIndex(dbIndexToDrop));
            code.append(delimiter);
        }
        return code.toString();
    }
}

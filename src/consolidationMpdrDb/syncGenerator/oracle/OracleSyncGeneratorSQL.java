package consolidationMpdrDb.syncGenerator.oracle;

import consolidationMpdrDb.comparator.oracle.OracleComparatorDb;
import generatorsql.generator.oracle.*;
import mpdr.*;
import mpdr.tapis.MPDRPackage;
import mpdr.tapis.MPDRTrigger;
import preferences.Preferences;

public class OracleSyncGeneratorSQL {


    private MPDROracleGenerateSQL mpdrOracleGenerateSQL;
    private MPDROracleGenerateSQLTable mpdrOracleGenerateSQLTable;
    private MPDROracleGenerateSQLTableColumn mpdrOracleGenerateSQLTableColumn;
    private MPDROracleGenerateSQLPK mpdrOracleGenerateSQLPK;
    private MPDROracleGenerateSQLUnique mpdrOracleGenerateSQLUnique;
    private MPDROracleGenerateSQLCheck mpdrOracleGenerateSQLCheck;
    private MPDROracleGenerateSQLFK mpdrOracleGenerateSQLFK;
    private MPDROracleGenerateSQLSequence mpdrOracleGenerateSQLSequence;
    private MPDROracleGenerateSQLTrigger mpdrOracleGenerateSQLTrigger;
    private MPDROracleGenerateSQLPackage mpdrOracleGenerateSQLPackage;
    private OracleComparatorDb oracleComparatorDb;
    private String delimiter = delimiter();

    public OracleSyncGeneratorSQL(MPDRModel mpdrModel, OracleComparatorDb oracleComparatorDb) {
        this.mpdrOracleGenerateSQL = new MPDROracleGenerateSQL(mpdrModel);
        this.mpdrOracleGenerateSQLTable = new MPDROracleGenerateSQLTable(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLTableColumn = new MPDROracleGenerateSQLTableColumn(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLPK = new MPDROracleGenerateSQLPK(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLUnique = new MPDROracleGenerateSQLUnique(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLCheck = new MPDROracleGenerateSQLCheck(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLFK = new MPDROracleGenerateSQLFK(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLSequence = new MPDROracleGenerateSQLSequence(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLTrigger = new MPDROracleGenerateSQLTrigger(mpdrOracleGenerateSQL);
        this.mpdrOracleGenerateSQLPackage = new MPDROracleGenerateSQLPackage(mpdrOracleGenerateSQL);
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

    public String syncOrderByTable(){

        StringBuilder generateSQLCodeSync = new StringBuilder();
        generateSQLCodeSync.append(" ");

        /*Suppression des tables en cascade efface :
            - tables
            - colonnes
                - contraintes not nul
            - contraintes uniques, check et pk
            - contraintes fk de la table
            - contraintes fk qui pointe sur la table
            - triggers

            - package NON
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
        delimiter = Preferences.SYSTEM_LINE_SEPARATOR + "/" + Preferences.SYSTEM_LINE_SEPARATOR;
        generateSQLCodeSync.append(syncSequenceToDrop());
        generateSQLCodeSync.append(syncSequenceNotInTableToDrop());
        delimiter = delimiter();
        generateSQLCodeSync.append(syncSequenceToCreate());
        delimiter = Preferences.SYSTEM_LINE_SEPARATOR + "/" + Preferences.SYSTEM_LINE_SEPARATOR;
        generateSQLCodeSync.append(syncTriggerToDrop());
        generateSQLCodeSync.append(syncTriggerNotInTableToDrop());
        generateSQLCodeSync.append(syncPackageToDrop());
        generateSQLCodeSync.append(syncPackageNotInTableToDrop());
        generateSQLCodeSync.append(syncTriggerToCreateOrReplace());
        generateSQLCodeSync.append(syncPackageToCreateOrReplace());

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
            code.append(delimiter);
        }
        return code.toString();
    }

    public String syncTablesToDrop() {
        StringBuilder code = new StringBuilder();
        for (MPDRTable mpdrTableToDrop : oracleComparatorDb.getDbTablesToDrop()) {
            code.append(mpdrOracleGenerateSQLTable.generateSQLDropTableConsolidation(mpdrTableToDrop));
            code.append(delimiter);
        }
        return code.toString();
    }

    public String syncColumnsToDrop(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRColumn dbColumnToDrop : oracleComparatorDb.getDbColumnsToDrop()) {
            if (dbColumnToDrop.getMPDRTableAccueil().getName().equals(tableToModify.getName().toUpperCase())) {
                code.append(mpdrOracleGenerateSQLTableColumn.generateSQLDropColumn(dbColumnToDrop));
                code.append(delimiter);
            }
        }
        return code.toString();
    }

        public String syncColumnsToAdd(MPDRTable tableToModify) {
            StringBuilder code = new StringBuilder();
            for (MPDRColumn mpdrColumnToAdd : oracleComparatorDb.getMpdrColumnsToAdd()) {
                if (mpdrColumnToAdd.getMPDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())) {
                    code.append(mpdrOracleGenerateSQLTableColumn.generateSQLAddColumn(mpdrColumnToAdd));
                    code.append(delimiter);
                }
            }
            return code.toString();
        }

    public String syncColumnsToModify(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnToModify : oracleComparatorDb.getMpdrColumnsToModify()) {
            if(mpdrColumnToModify.getMPDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())){
                code.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumn(mpdrColumnToModify));
                code.append(delimiter);
            }
        }
        return code.toString();
    }

    public String syncColumnsToModifyAddNotNull(MPDRTable tableToModify){
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnAddNN : oracleComparatorDb.getMpdrColumnsToModifyAddNotNull()) {
            if(mpdrColumnAddNN.getMPDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())){
                code.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumnAddNotNul(mpdrColumnAddNN));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    public String syncColumnsToModifyDropNotNull(MPDRTable tableToModify){
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnDropNN : oracleComparatorDb.getMpdrColumnsToModifyDropNotNull()) {
            if(mpdrColumnDropNN.getMPDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())){
                code.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumnDropNotNul(mpdrColumnDropNN));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    public String syncColumnsToModifyAddOrModifyDefault(MPDRTable tableToModify){
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumnAddOrModifDefault : oracleComparatorDb.getMpdrColumnsToModifyAddOrModifyDefault()) {
            if(mpdrColumnAddOrModifDefault.getMPDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())){
                code.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumnAddOrModifyDefault(mpdrColumnAddOrModifDefault));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    public String syncColumnsToModifyDropDefault(MPDRTable tableToModify){
        StringBuilder code = new StringBuilder();
        for (MPDRColumn mpdrColumDropDefault : oracleComparatorDb.getMpdrColumnsToModifyDropDefault()) {
            if(mpdrColumDropDefault.getMPDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())){
                code.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumnDropDefault(mpdrColumDropDefault));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    public String syncPkToAdd(MPDRTable tableToModify){
        StringBuilder code = new StringBuilder();
        for (MPDRPK mpdrPkToAdd : oracleComparatorDb.getMpdrPksToAdd()) {
            if(mpdrPkToAdd.getMDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())){
                code.append(mpdrOracleGenerateSQLPK.generateSQLAddPKConsolidation(mpdrPkToAdd));
                code.append(delimiter());
            }
        }
        return code.toString();
    }
    public String syncPkToDrop(MPDRTable tableToModify){
        StringBuilder code = new StringBuilder();
        for (MPDRPK dbPkToDrop : oracleComparatorDb.getDbPksToDrop()) {
            if(dbPkToDrop.getMDRTableAccueil().getName().equals(tableToModify.getName().toUpperCase()))   {
                code.append(mpdrOracleGenerateSQLPK.generateSQLDropPKConsolidation(dbPkToDrop));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    public String syncUniqueToAdd(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRUnique mpdrUniqueToAdd : oracleComparatorDb.getMpdrUniquesToAdd()) {
            if(mpdrUniqueToAdd.getMDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())){
                code.append(mpdrOracleGenerateSQLUnique.generateSQLCreateUniqueConsolidation(mpdrUniqueToAdd));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    public String syncUniqueToDrop(MPDRTable tableToModify) {
        StringBuilder code = new StringBuilder();
        for (MPDRUnique dbUniqueToDrop : oracleComparatorDb.getDbUniquesToDrop()) {
            if(dbUniqueToDrop.getMDRTableAccueil().getName().equals(tableToModify.getName().toUpperCase())){
                code.append(mpdrOracleGenerateSQLUnique.generateSQLDropUniqueConsolidation(dbUniqueToDrop));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    public String syncCheckToAdd(MPDRTable tableToModify){
        StringBuilder code = new StringBuilder();
        for (MPDRCheck mpdrCheckToAdd : oracleComparatorDb.getMpdrChecksToAdd()) {
            if(mpdrCheckToAdd.getMDRTableAccueil().getName().equalsIgnoreCase(tableToModify.getName())){
                code.append(mpdrOracleGenerateSQLCheck.generateSQLAddCheck(mpdrCheckToAdd));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    public String syncCheckToDrop(MPDRTable tableToModify){
        StringBuilder code = new StringBuilder();
        for (MPDRCheck dbCheckToDrop : oracleComparatorDb.getDbChecksToDrop()) {
            if(dbCheckToDrop.getMDRTableAccueil().getName().equals(tableToModify.getName().toUpperCase())){
                code.append(mpdrOracleGenerateSQLCheck.generateSQLDropCheck(dbCheckToDrop));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    public String syncFkToAdd(){
        StringBuilder code = new StringBuilder();
        for (MPDRFK mpdrFkToAdd : oracleComparatorDb.getMpdrFksToAdd()) {
            code.append(mpdrOracleGenerateSQLFK.generateSQLFK(mpdrFkToAdd));
            code.append(delimiter);
        }
        return code.toString();
    }
    public String syncFkToDrop(MPDRTable tableToModify){
        StringBuilder code = new StringBuilder();
        for (MPDRFK dbFkToDrop : oracleComparatorDb.getDbFksToDrop()) {
            if(dbFkToDrop.getMDRTableAccueil().getName().equals(tableToModify.getName().toUpperCase())){
                code.append(mpdrOracleGenerateSQLFK.generateSQLFkToDropConsolidation(dbFkToDrop));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    public String syncFKAddDeleteCascade(){
        StringBuilder code = new StringBuilder();
        for (MPDRFK mpdrfk : oracleComparatorDb.getMpdrFksToAddDeleteCascade()) {
            code.append(mpdrOracleGenerateSQLFK.generateSQLFKWithDeleteCascadeOption(mpdrfk));
            code.append(delimiter);
        }
        return code.toString();
    }

    public String syncFkDropDeleteCascade(MPDRTable tableToModify){
        StringBuilder code = new StringBuilder();
        for (MPDRFK dbFkToDrop : oracleComparatorDb.getDbFksToDropDeleteCascade()) {
            if(dbFkToDrop.getMDRTableAccueil().getName().equals(tableToModify.getName().toUpperCase())){
                code.append(mpdrOracleGenerateSQLFK.generateSQLFkToDropConsolidation(dbFkToDrop));
                code.append(delimiter());
            }
        }
        return code.toString();
    }

    public String syncSequenceToCreate(){
        StringBuilder code = new StringBuilder();
        for (MPDRSequence mpdrSequenceToCreate : oracleComparatorDb.getMpdrSequencesToCreate()) {
            code.append(mpdrOracleGenerateSQLSequence.generateSQLCreateSequence(mpdrSequenceToCreate));
            code.append(delimiter);
        }
        return code.toString();
    }

    public String syncSequenceToDrop(){
        StringBuilder code = new StringBuilder();
        for (MPDRSequence dbSequenceToDrop : oracleComparatorDb.getDbSequencesToDrop()) {
            code.append(mpdrOracleGenerateSQLSequence.generateSQLDropSequence(dbSequenceToDrop));
            code.append(delimiter);
        }
        return code.toString();
    }

    public String syncSequenceNotInTableToDrop(){
        StringBuilder code = new StringBuilder();
        for (String sequenceNotInTable : oracleComparatorDb.getDbFetcherOracle().getDbSequencesNotInTable()) {
            code.append(mpdrOracleGenerateSQLSequence.generateSQLDropSequence(sequenceNotInTable));
            code.append(delimiter);
        }
        return code.toString();
    }

    public String syncTriggerToDrop(){
        StringBuilder code = new StringBuilder();
        for (MPDRTrigger dbTriggerToDrop : oracleComparatorDb.getDbTriggersToDrop()) {
            code.append(mpdrOracleGenerateSQLTrigger.generateSQLDropTrigger(dbTriggerToDrop));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncTriggerNotInTableToDrop(){
        StringBuilder code = new StringBuilder();
        for (String triggerNotInTable : oracleComparatorDb.getDbFetcherOracle().getDbTriggersNotInTable()) {
            code.append(mpdrOracleGenerateSQLTrigger.generateSQLDropTrigger(triggerNotInTable));
            code.append(delimiter);
        }
        return code.toString();
    }

    public String syncTriggerToCreateOrReplace(){
        StringBuilder code = new StringBuilder();
        for (MPDRTrigger mpdrTriggerToCreateOrReplace : oracleComparatorDb.getMpdrTriggersToCreateOrReplace()) {
            code.append(mpdrOracleGenerateSQLTrigger.generateSQLCreateTrigger(mpdrTriggerToCreateOrReplace));
            code.append(delimiter);
        }
        return code.toString();
    }

    public String syncPackageToDrop(){
        StringBuilder code = new StringBuilder();
        for (MPDRPackage dbPackageToDrop : oracleComparatorDb.getDbPackagesToDrop()) {
            code.append(mpdrOracleGenerateSQLPackage.generateSQLDropPackage(dbPackageToDrop));
            code.append(delimiter);
        }
        return code.toString();
    }

    private String syncPackageNotInTableToDrop(){
        StringBuilder code = new StringBuilder();
        for (String packageNotInTable : oracleComparatorDb.getDbFetcherOracle().getDbPackagesNotInTable()) {
            code.append(mpdrOracleGenerateSQLPackage.generateSQLDropPackage(packageNotInTable));
            code.append(delimiter);
        }
        return code.toString();
    }

    public String syncPackageToCreateOrReplace(){
        StringBuilder code = new StringBuilder();
        for (MPDRPackage mpdrPackageToCreateOrReplace : oracleComparatorDb.getMpdrPackagesToCreateOrReplace()) {
            code.append(mpdrOracleGenerateSQLPackage.generateSQLCreatePackage(mpdrPackageToCreateOrReplace));
            code.append(delimiter);
        }
        return code.toString();
    }

}

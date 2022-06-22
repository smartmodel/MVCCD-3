package consolidationMpdrDb.syncGenerator.oracle;

import consolidationMpdrDb.comparator.oracle.OracleComparatorDb;
import generatorsql.generator.oracle.*;
import messages.MessagesBuilder;
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
        generateSQLCodeSync.append(syncSequenceToDrop());

        //Création des nouvelles tables
        generateSQLCodeSync.append(syncTablesToCreate());


        for (MPDRTable mpdrTableToModifiy : oracleComparatorDb.getMpdrTablesSameName()) {
            //Suppression des contraintes UNIQUE
            for (MPDRUnique dbUniqueToDrop : oracleComparatorDb.getDbUniquesToDrop()) {
                if(dbUniqueToDrop.getMDRTableAccueil().getName().equals(mpdrTableToModifiy.getName().toUpperCase())){
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLUnique.generateSQLDropUniqueConsolidation(dbUniqueToDrop));
                    generateSQLCodeSync.append(delimiter());
                }
            }
            //Suppression des contraintes CHECK
            for (MPDRCheck dbCheckToDrop : oracleComparatorDb.getDbChecksToDrop()) {
                if(dbCheckToDrop.getMDRTableAccueil().getName().equals(mpdrTableToModifiy.getName().toUpperCase())){
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLCheck.generateSQLDropCheck(dbCheckToDrop));
                    generateSQLCodeSync.append(delimiter());
                }
            }
            //Suppression des contraintes FK
            for (MPDRFK dbFkToDrop : oracleComparatorDb.getDbFksToDrop()) {
                if(dbFkToDrop.getMDRTableAccueil().getName().equals(mpdrTableToModifiy.getName().toUpperCase())){
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLFK.generateSQLFkToDropConsolidation(dbFkToDrop));
                    generateSQLCodeSync.append(delimiter());
                }
            }
            //Suppression des contraintes PK
            for (MPDRPK dbPkToDrop : oracleComparatorDb.getDbPksToDrop()) {
                if(dbPkToDrop.getMDRTableAccueil().getName().equals(mpdrTableToModifiy.getName().toUpperCase()))   {
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLPK.generateSQLDropPKConsolidation(dbPkToDrop));
                    generateSQLCodeSync.append(delimiter());
                }
            }
            //Suppression des colonnes
            for (MPDRColumn dbColumnToDrop : oracleComparatorDb.getDbColumnsToDrop()) {
                if(dbColumnToDrop.getMPDRTableAccueil().getName().equals(mpdrTableToModifiy.getName().toUpperCase())){
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLTableColumn.generateSQLDropColumn(dbColumnToDrop));
                    generateSQLCodeSync.append(delimiter());
                }
            }
            //Ajout des colonnes
            for (MPDRColumn mpdrColumnToAdd : oracleComparatorDb.getMpdrColumnsToAdd()) {
                if(mpdrColumnToAdd.getMPDRTableAccueil().getName().equalsIgnoreCase(mpdrTableToModifiy.getName())){
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLTableColumn.generateSQLAddColumn(mpdrColumnToAdd));
                    generateSQLCodeSync.append(delimiter());
                    }
                }
            //Modification des colonnes
            for (MPDRColumn mpdrColumnToModify : oracleComparatorDb.getMpdrColumnsToModify()) {
                if(mpdrColumnToModify.getMPDRTableAccueil().getName().equalsIgnoreCase(mpdrTableToModifiy.getName())){
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumn(mpdrColumnToModify));
                    generateSQLCodeSync.append(delimiter());
                }
            }
            //Ajout de la contrainte NOT NULL sur la colonne
            for (MPDRColumn mpdrColumnAddNN : oracleComparatorDb.getMpdrColumnsToModifyAddNotNull()) {
                if(mpdrColumnAddNN.getMPDRTableAccueil().getName().equalsIgnoreCase(mpdrTableToModifiy.getName())){
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumnAddNotNul(mpdrColumnAddNN));
                    generateSQLCodeSync.append(delimiter());
                }
            }
            //Suppression de la contrainte NOT NULL de la colonne
            for (MPDRColumn mpdrColumnDropNN : oracleComparatorDb.getMpdrColumnsToModifyDropNotNull()) {
                if(mpdrColumnDropNN.getMPDRTableAccueil().getName().equalsIgnoreCase(mpdrTableToModifiy.getName())){
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumnDropNotNul(mpdrColumnDropNN));
                    generateSQLCodeSync.append(delimiter());
                }
            }
            //Ajout de la clause DEFAULT
            for (MPDRColumn mpdrColumnAddOrModifDefault : oracleComparatorDb.getMpdrColumnsToModifyAddOrModifyDefault()) {
                if(mpdrColumnAddOrModifDefault.getMPDRTableAccueil().getName().equalsIgnoreCase(mpdrTableToModifiy.getName())){
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumnAddOrModifyDefault(mpdrColumnAddOrModifDefault));
                    generateSQLCodeSync.append(delimiter());
                }
            }
            //SUPPRESSION DE LA CLAUSE DEFAULT
            for (MPDRColumn mpdrColumDropDefault : oracleComparatorDb.getMpdrColumnsToModifyDropDefault()) {
                if(mpdrColumDropDefault.getMPDRTableAccueil().getName().equalsIgnoreCase(mpdrTableToModifiy.getName())){
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLTableColumn.generateSQLModifyColumnDropDefault(mpdrColumDropDefault));
                    generateSQLCodeSync.append(delimiter());
                }
            }
            //Ajout de la PK
            for (MPDRPK mpdrPkToAdd : oracleComparatorDb.getMpdrPksToAdd()) {
                if(mpdrPkToAdd.getMDRTableAccueil().getName().equalsIgnoreCase(mpdrTableToModifiy.getName())){
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLPK.generateSQLAddPKConsolidation(mpdrPkToAdd));
                    generateSQLCodeSync.append(delimiter());
                }
            }
            //Ajout de la contrainte UNIQUE
            for (MPDRUnique mpdrUniqueToAdd : oracleComparatorDb.getMpdrUniquesToAdd()) {
                if(mpdrUniqueToAdd.getMDRTableAccueil().getName().equalsIgnoreCase(mpdrTableToModifiy.getName())){
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLUnique.generateSQLCreateUniqueConsolidation(mpdrUniqueToAdd));
                    generateSQLCodeSync.append(delimiter());
                }
            }
            //Ajout de la contrainte CHECK
            for (MPDRCheck mpdrCheckToAdd : oracleComparatorDb.getMpdrChecksToAdd()) {
                if(mpdrCheckToAdd.getMDRTableAccueil().getName().equalsIgnoreCase(mpdrTableToModifiy.getName())){
                    generateSQLCodeSync.append(mpdrOracleGenerateSQLCheck.generateSQLAddCheck(mpdrCheckToAdd));
                    generateSQLCodeSync.append(delimiter());
                }
            }
        }//Fin de boucle des tables identiques

        generateSQLCodeSync.append(syncFkToAdd());
        generateSQLCodeSync.append(syncSequenceToCreate());

        generateSQLCodeSync.append(syncTriggerToDrop());
        generateSQLCodeSync.append(syncPackageToDrop());
        delimiter().replaceAll(".", "/");
        generateSQLCodeSync.append(syncTriggerToCreateOrReplace());
        generateSQLCodeSync.append(syncPackageToCreateOrReplace());

        //S'il n'y a rien à générer, affichage d'un message d'information à l'utilisateur
        if(generateSQLCodeSync.toString().equals(" ")){
            generateSQLCodeSync.append("La structure du SGBD-R est conforme au modèle");
        }

        return generateSQLCodeSync.toString();
    }


    public String syncOrderedByOperationType() {
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
        generateSQLCodeSync.append(syncColumnsToModifyDropDefault());
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

    public String syncColumnsToModifyDropDefault(){
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

    public String syncFkDropDeleteCascade(){
        StringBuilder code = new StringBuilder();

        return code.toString();
    }

    public String syncSequenceToCreate(){
        StringBuilder code = new StringBuilder();

        return code.toString();
    }

    public String syncSequenceToDrop(){
        StringBuilder code = new StringBuilder();
        for (MPDRSequence dbSequenceToDrop : oracleComparatorDb.getDbSequencesToDrop()) {
            code.append(mpdrOracleGenerateSQLSequence.generateSQLDropSequence(dbSequenceToDrop));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncTriggerToDrop(){
        StringBuilder code = new StringBuilder();
        for (MPDRTrigger dbTriggerToDrop : oracleComparatorDb.getDbTriggersToDrop()) {
            code.append(mpdrOracleGenerateSQLTrigger.generateSQLDropTrigger(dbTriggerToDrop));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncTriggerToCreateOrReplace(){
        StringBuilder code = new StringBuilder();
        for (MPDRTrigger mpdrTriggerToCreateOrReplace : oracleComparatorDb.getMpdrTriggersToCreateOrReplace()) {
            code.append(mpdrOracleGenerateSQLTrigger.generateSQLCreateTrigger(mpdrTriggerToCreateOrReplace));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncPackageToDrop(){
        StringBuilder code = new StringBuilder();
        for (MPDRPackage dbPackageToDrop : oracleComparatorDb.getDbPackagesToDrop()) {
            code.append(mpdrOracleGenerateSQLPackage.generateSQLDropPackage(dbPackageToDrop));
            code.append(delimiter());
        }
        return code.toString();
    }

    public String syncPackageToCreateOrReplace(){
        StringBuilder code = new StringBuilder();
        for (MPDRPackage mpdrPackageToCreateOrReplace : oracleComparatorDb.getMpdrPackagesToCreateOrReplace()) {
            code.append(mpdrOracleGenerateSQLPackage.generateSQLCreatePackage(mpdrPackageToCreateOrReplace));
            code.append(delimiter());
        }
        return code.toString();
    }

}

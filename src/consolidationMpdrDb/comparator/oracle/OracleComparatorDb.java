package consolidationMpdrDb.comparator.oracle;

import connections.ConConnection;
import consolidationMpdrDb.comparator.MpdrDbComparator;
import consolidationMpdrDb.fetcher.oracle.DbFetcherOracle;
import mpdr.*;
import mpdr.oracle.MPDROracleModel;
import mpdr.tapis.*;
import mpdr.tapis.oracle.MPDROracleBoxPackages;
import preferences.Preferences;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//ATTENTION, pour oracle, on utilise toUpperCase pour les noms
public class OracleComparatorDb extends MpdrDbComparator {

    private List<MPDRTable> mpdrTablesSameName = new ArrayList<>();
    private List<MPDRTable> mpdrTablesToCreate = new ArrayList<>();
    private List<MPDRTable> dbTablesToDrop = new ArrayList<>();
    private List<MPDRColumn> mpdrColumnsToAdd = new ArrayList<>();
    private List<MPDRColumn> dbColumnsToDrop = new ArrayList<>();
    private List<MPDRColumn> mpdrColumnsToModify = new ArrayList<>();
    private List<MPDRColumn> mpdrColumnsToModifyAddNotNull = new ArrayList<>();
    private List<MPDRColumn> mpdrColumnsToModifyDropNotNull = new ArrayList<>();
    private List<MPDRColumn> mpdrColumnsToModifyAddOrModifyDefault = new ArrayList<>();
    private List<MPDRColumn> mpdrColumnsToModifyDropDefault = new ArrayList<>();
    private List<MPDRUnique> mpdrUniquesToAdd = new ArrayList<>();
    private List<MPDRUnique> dbUniquesToDrop = new ArrayList<>();
    private List<MPDRCheck> mpdrChecksToAdd = new ArrayList<>();
    private List<MPDRCheck> dbChecksToDrop = new ArrayList<>();
    private List<MPDRPK> mpdrPksToAdd = new ArrayList<>();
    private List<MPDRPK> dbPksToDrop = new ArrayList<>();
    private List<MPDRFK> mpdrFksToAdd = new ArrayList<>();
    private List<MPDRFK> dbFksToDrop = new ArrayList<>();
    private List<MPDRFK> mpdrFksToAddDeleteCascade = new ArrayList<>();
    private List<MPDRFK> dbFksToDropDeleteCascade = new ArrayList<>(); //A supprimer ?
    private List<MPDRSequence> mpdrSequencesToCreate = new ArrayList<>();
    private List<MPDRSequence> dbSequencesToDrop = new ArrayList<>();
    private List<MPDRTrigger> mpdrTriggersToCreateOrReplace = new ArrayList<>();
    private List<MPDRTrigger> dbTriggersToDrop = new ArrayList<>();
    private List<MPDRPackage> mpdrPackagesToCreateOrReplace = new ArrayList<>();
    private List<MPDRPackage> dbPackagesToDrop = new ArrayList<>();

    private MPDROracleModel mpdrModel;
    private MPDROracleModel dbModelOracle;
    private DbFetcherOracle dbFetcherOracle;

    public OracleComparatorDb(MPDROracleModel mpdrModel, ConConnection conConnection, Connection connection) throws SQLException {
        this.mpdrModel = mpdrModel;
        this.dbFetcherOracle = new DbFetcherOracle(conConnection, connection);
        this.dbFetcherOracle.fetch();
        this.dbModelOracle = dbFetcherOracle.getDbModel();
    }

    public void compare() {
        compareTables();
    }

    private void compareTables() {
        compareTablesIdenticales();
        compareTablesToCreate();
        compareTablesToDrop();
        compareSequencesInTableToDrop();
        comparePackageInTableToDrop();
    }

    private void compareTablesIdenticales() {
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            MPDRTable dbTable = findDbTableByName(mpdrTable);
            if (dbTable != null) {
                mpdrTablesSameName.add(mpdrTable);
                compareColumns(mpdrTable.getMPDRColumns(), dbTable.getMPDRColumns());
                comparePk(mpdrTable, dbTable);
                compareUnique(mpdrTable, dbTable);
                compareCheck(mpdrTable, dbTable);
                compareFk(mpdrTable, dbTable);
                compareFkOptionDeleteCascadeToAddOrDrop(mpdrTable, dbTable);
                removeConstraintFromDropColumn();
                compareTriggers(mpdrTable.getMPDRBoxTriggers(), dbTable.getMPDRBoxTriggers());
                comparePackages((MPDROracleBoxPackages) mpdrTable.getMPDRContTAPIs().getMPDRBoxPackages(), (MPDROracleBoxPackages) dbTable.getMPDRContTAPIs().getMPDRBoxPackages());
            }
        }
    }

    private void compareTablesToCreate() {
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            MPDRTable dbTable = findDbTableByName(mpdrTable);
            if (dbTable == null) {
                mpdrTablesToCreate.add(mpdrTable);
                //Ajout des séquences pour la nouvelle table
                for (MPDRColumn mpdrColumn : mpdrTable.getMPDRColumns()) {
                    if (!mpdrColumn.getChilds().isEmpty()) {
                        if(!mpdrSequencesToCreate.contains(mpdrColumn.getMPDRSequence())){
                            mpdrSequencesToCreate.add(mpdrColumn.getMPDRSequence());
                        }
                    }
                }
            }
        }
    }

    private void compareTablesToDrop() {
        for (MPDRTable dbTable : dbModelOracle.getMPDRTables()) {
            MPDRTable mpdrTable = findMpdrTableByName(dbTable);
            if (mpdrTable == null) {
                dbTablesToDrop.add(dbTable);
            }
        }
    }

    private MPDRTable findDbTableByName(MPDRTable mpdrTable) {
        for (MPDRTable dbTable : dbModelOracle.getMPDRTables()) {
            if (mpdrTable.getName().toUpperCase().equals(dbTable.getName())) {
                return dbTable;
            }
        }
        return null;
    }

    private MPDRTable findMpdrTableByName(MPDRTable dbTable) {
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            if (dbTable.getName().equals(mpdrTable.getName().toUpperCase())) {
                return mpdrTable;
            }
        }
        return null;
    }

    private void compareColumns(List<MPDRColumn> mpdrColumns, List<MPDRColumn> dbColumns) {
        //Pour toutes les colonnes du Mpdr, soit elles sont identiques, soit elles ne sont pas dans la table de la db et doivent y être ajoutées
        for (MPDRColumn mpdrColumn : mpdrColumns) {
            compareColumnsIdenticales(mpdrColumn, dbColumns);
            compareColumnToAdd(mpdrColumn, dbColumns);
        }
        //Pour toutes les colonnes dans la table de la db, on supprime celles qui ne sont pas dans le mpdr
        for (MPDRColumn dbColumn : dbColumns) {
            compareColumnToDrop(dbColumn, mpdrColumns);
        }
    }

    private void compareColumnsIdenticales(MPDRColumn mpdrColumn, List<MPDRColumn> dbColumns) {
        MPDRColumn dbColumn = findDbColumnByName(mpdrColumn, dbColumns);
        if (dbColumn != null) {
            //Si le nom est identique, on compare alors les attributs entre les colonnes du mpdr et de la db
            boolean columnIdentic = true;
            if (!compareColumnDataType(mpdrColumn, dbColumn)) {
                columnIdentic = false;
            }
            if (!compareColumnSize(mpdrColumn, dbColumn)) {
                columnIdentic = false;
            }
            if (!compareColumnScale(mpdrColumn, dbColumn)) {
                columnIdentic = false;
            }

            compareColumnMandatory(mpdrColumn, dbColumn);

            compareColumnInitValue(mpdrColumn, dbColumn);

            if (!columnIdentic) {
                mpdrColumnsToModify.add(mpdrColumn);
            }
            //Ensuite, on compare la séquence si la colonne en possède une
            compareSequence(mpdrColumn, dbColumn);
        }
    }

    private MPDRColumn findDbColumnByName(MPDRColumn mpdrColumn, List<MPDRColumn> dbColumns) {
        for (MPDRColumn dbColumn : dbColumns) {
            if (mpdrColumn.getName().toUpperCase().equals(dbColumn.getName())) {
                return dbColumn;
            }
        }
        return null;
    }

    private MPDRColumn findMpdrColumnByName(MPDRColumn dbColumn, List<MPDRColumn> mpdrColumns) {
        for (MPDRColumn mpdrColumn : mpdrColumns) {
            if (dbColumn.getName().equals(mpdrColumn.getName().toUpperCase())) {
                return mpdrColumn;
            }
        }
        return null;
    }

    private void compareColumnToAdd(MPDRColumn mpdrColumn, List<MPDRColumn> dbColumns) {
        MPDRColumn dbColumnFind = findDbColumnByName(mpdrColumn, dbColumns);
        if (dbColumnFind == null) {
            mpdrColumnsToAdd.add(mpdrColumn);
        }
    }

    private void compareColumnToDrop(MPDRColumn dbColumn, List<MPDRColumn> mpdrColumns) {
        MPDRColumn mpdrColumnFind = findMpdrColumnByName(dbColumn, mpdrColumns);
        if (mpdrColumnFind == null) {
            dbColumnsToDrop.add(dbColumn);
        }
    }

    private boolean compareColumnDataType(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        return mpdrColumn.getDatatypeLienProg().toUpperCase().equals(dbColumn.getDatatypeLienProg());
    }

    //Attention, si MpdrDataType=DATE -> Size=null mais dans la db la size=7
    //Selon décision de FC, pas de comparaison de la longueur lorsqu'il s'agit de DATE
    private boolean compareColumnSize(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        boolean sameSize = false;
        if (!mpdrColumn.getDatatypeLienProg().toUpperCase().equals("DATE")) {
            if (mpdrColumn.getSize().equals(dbColumn.getSize())) {
                return sameSize = true;
            }
        } else {
            return sameSize = true;
        }
        return sameSize;
    }

    //Dans le mpdr, s'il n'y a pas de scale=null alors que dans la db=0
    private boolean compareColumnScale(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        if (mpdrColumn.getScale() == null && dbColumn.getScale() == 0) {
            return true;
        } else {
            if (mpdrColumn.getScale() == null || dbColumn.getScale() == null) {
                return false;
            }
        }
        if (mpdrColumn.getScale().equals(dbColumn.getScale())) {
            return true;
        }
        return false;
    }

    private void compareColumnMandatory(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        compareColumnAddMandatory(mpdrColumn, dbColumn);
        compareColumnDropMandatory(mpdrColumn, dbColumn);
    }

    private void compareColumnAddMandatory(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        if (mpdrColumn.isMandatory() && !dbColumn.getMandatory()) {
            mpdrColumnsToModifyAddNotNull.add(mpdrColumn);
        }
    }

    private void compareColumnDropMandatory(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        if (!mpdrColumn.isMandatory() && dbColumn.getMandatory()) {
            mpdrColumnsToModifyDropNotNull.add(mpdrColumn);
        }
    }

    //Dans le mpdr les datatype NUMBER ont une initValue à null alors que les autre type = ""
    private void compareColumnInitValue(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        compareColumnDropInitValue(mpdrColumn, dbColumn);
        compareColumnAddOrModifyInitValue(mpdrColumn, dbColumn);
    }

    //Si la suppression de la clause par défaut a déjà été effectuée une fois, elle devient une string "null" au lieu d'être null
    private void compareColumnDropInitValue(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        if ((mpdrColumn.getInitValue() == null || mpdrColumn.getInitValue().equals("")) && dbColumn.getInitValue() != null) {
            if (!dbColumn.getInitValue().equals("null")) {
                mpdrColumnsToModifyDropDefault.add(mpdrColumn);
            }
        }
    }

    //L'ajout ou la modification de la clause ont la même instruction SQL donc on les groupe dans la même liste
    private void compareColumnAddOrModifyInitValue(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        if (mpdrColumn.getInitValue() == null || mpdrColumn.getInitValue().equals("")) {
            //doNothing - pour éviter la nullPointerExeption si la valeur est nulle
        } else {
            if (dbColumn.getInitValue() == null) {
                mpdrColumnsToModifyAddOrModifyDefault.add(mpdrColumn);
            } else {
                //Suppression des espaces car le SGBD-R en ajoute un automatiquement à la fin
                String dbInitValueWithoutSpace = dbColumn.getInitValue().toUpperCase().replaceAll("\\s+", "");
                String mpdrInitValueWithoutSpace = mpdrColumn.getInitValue().toUpperCase().replaceAll("\\s+", "");
                if (!mpdrInitValueWithoutSpace.equals(dbInitValueWithoutSpace)) {
                    mpdrColumnsToModifyAddOrModifyDefault.add(mpdrColumn);
                }
            }
        }
    }

    private void comparePk(MPDRTable mpdrTable, MPDRTable dbTable) {
        comparePkToAdd(mpdrTable, dbTable);
        comparePkToDrop(mpdrTable, dbTable);
    }

    private void comparePkToDrop(MPDRTable mpdrTable, MPDRTable dbTable) {
        if (!mpdrTable.getMPDRPK().getName().toUpperCase().equals(dbTable.getMPDRPK().getName())) {
            dbPksToDrop.add(dbTable.getMPDRPK());
        }
    }

    private void comparePkToAdd(MPDRTable mpdrTable, MPDRTable dbTable) {
        if (mpdrTable.getMPDRPK() != null) {
            if (dbTable.getMPDRPK() == null) {
                mpdrPksToAdd.add(mpdrTable.getMPDRPK());
            } else {
                if (mpdrTable.getMPDRPK().getName().toUpperCase().equals(dbTable.getMPDRPK().getName())) {
                    //contrôle que les PK pointent sur les mêmes noms de colonne
                    for (int i = 0; i < mpdrTable.getMPDRPK().getMDRParameters().size(); i++) {
                        if (!mpdrTable.getMPDRPK().getMDRParameters().get(i).getName().equals(
                                dbTable.getMPDRPK().getMDRParameters().get(i).getName())) {
                            dbPksToDrop.add(dbTable.getMPDRPK());
                            mpdrPksToAdd.add(mpdrTable.getMPDRPK());
                            break;
                        }
                    }
                } else {
                    mpdrPksToAdd.add(mpdrTable.getMPDRPK());
                }
            }
        }
    }

    private void compareCheck(MPDRTable mpdrTable, MPDRTable dbTable) {
        compareCheckToAdd(mpdrTable, dbTable);
        compareCheckToDrop(mpdrTable, dbTable);
        compareCheckToModify(mpdrTable, dbTable);
    }

    private void compareCheckToAdd(MPDRTable mpdrTable, MPDRTable dbTable) {
        List<String> dbCheckNameList = new ArrayList<>();
        for (MPDRCheck dbCheck : dbTable.getMPDRChecks()) {
            dbCheckNameList.add(dbCheck.getName());
        }
        for (MPDRCheck mpdrCheck : mpdrTable.getMPDRChecks()) {
            if (!dbCheckNameList.contains(mpdrCheck.getName().toUpperCase())) {
                mpdrChecksToAdd.add(mpdrCheck);
            }
        }
    }

    private void compareCheckToDrop(MPDRTable mpdrTable, MPDRTable dbTable) {
        List<String> mpdrCheckNameList = new ArrayList<>();
        for (MPDRCheck mpdrCheck : mpdrTable.getMPDRChecks()) {
            mpdrCheckNameList.add(mpdrCheck.getName().toUpperCase());
        }
        for (MPDRCheck dbCheck : dbTable.getMPDRChecks()) {
            if (!mpdrCheckNameList.contains(dbCheck.getName())) {
                dbChecksToDrop.add(dbCheck);
            }
        }
    }

    private void compareCheckToModify(MPDRTable mpdrTable, MPDRTable dbTable) {
        for (MPDRCheck mpdrCheck : mpdrTable.getMPDRChecks()) {
            for (MPDRCheck dbCheck : dbTable.getMPDRChecks()) {
                if (mpdrCheck.getName().toUpperCase().equals(dbCheck.getName())) {
                    String dbValueWithoutSpace = dbCheck.getMPDRParameter().getValue().toUpperCase().replaceAll("\\s+", "");
                    String mpdrValueWithoutSpace = mpdrCheck.getMPDRParameter().getValue().toUpperCase().replaceAll("\\s+", "");
                    if (!mpdrValueWithoutSpace.equals(dbValueWithoutSpace)) {
                        dbChecksToDrop.add(dbCheck);
                        mpdrChecksToAdd.add(mpdrCheck);
                    }
                }
            }

        }

    }

    private void compareFk(MPDRTable mpdrTable, MPDRTable dbTable) {
        compareFkToAdd(mpdrTable, dbTable);
        compareFkToDrop(mpdrTable, dbTable);
        compareFkRemoveFromDropListIfRefTableDrop(mpdrTable, dbTable);
    }

    private void compareFkToAdd(MPDRTable mpdrTable, MPDRTable dbTable) {
        List<String> dbFkNameList = new ArrayList<>();
        if (dbTable.getMPDRFKs() != null) {
            for (MPDRFK dbFk : dbTable.getMPDRFKs()) {
                dbFkNameList.add(dbFk.getName());
            }
        }
        if (mpdrTable.getMPDRFKs() != null) {
            for (MPDRFK mpdrfk : mpdrTable.getMPDRFKs()) {
                if (!dbFkNameList.contains(mpdrfk.getName().toUpperCase())) {
                    mpdrFksToAdd.add(mpdrfk);
                }
            }
        }
    }

    private void compareFkToDrop(MPDRTable mpdrTable, MPDRTable dbTable) {
        List<String> mpdrFkNameList = new ArrayList<>();
        for (MPDRFK mpdrFk : mpdrTable.getMPDRFKs()) {
            mpdrFkNameList.add(mpdrFk.getName().toUpperCase());
        }
        for (MPDRFK dbFk : dbTable.getMPDRFKs()) {
            if (!mpdrFkNameList.contains(dbFk.getName())) {
                dbFksToDrop.add(dbFk);
            }
        }
    }

    //ATTENTION, fonctionne pour autant que Preference.MDR_FK_NAME_FORMAT_DEFAULT =
// "{FK}{indConstFK}{fkIndSep}{childTableShortName}{tableSep}{parentTableShortName}{tableSep}{parentRoleShortName}";
    private void compareFkRemoveFromDropListIfRefTableDrop(MPDRTable mpdrTable, MPDRTable dbTable) {
        List<String> mpdrFkNameList = new ArrayList<>(); // par exemple: FK1_COL_QUAL_ATTRIB
        List<String> mpdrTableShortName = new ArrayList<>();
        List<String> splitFkName = new ArrayList<>();
        for (MPDRFK mpdrFk : mpdrTable.getMPDRFKs()) {
            mpdrFkNameList.add(mpdrFk.getName().toUpperCase());
            splitFkName.add(mpdrFk.getName().split("_")[2]);
        }
        for (MPDRTable mpdrSameTable : mpdrTablesSameName) {
            mpdrTableShortName.add(mpdrSameTable.getShortName());
        }
        for (String shortName : mpdrTableShortName) {
            if (!splitFkName.contains(shortName)) {
                for (MPDRFK dbFk : dbTable.getMPDRFKs()) {
                    dbFksToDrop.remove(dbFk);
                }
            }
        }
    }

    private void compareFkOptionDeleteCascadeToAddOrDrop(MPDRTable mpdrTable, MPDRTable dbTable) {
        for (MPDRFK mpdrFk : mpdrTable.getMPDRFKs()) {
            for (MPDRFK dbFk : dbTable.getMPDRFKs()) {
                if (mpdrFk.getName().toUpperCase().equals(dbFk.getName())) {
                    if (mpdrFk.isDeleteCascade() && !dbFk.isDeleteCascade()) {
                        //on l'ajoute à la liste à supprimer pour pouvoir la recréer ensuite avec les bons paramètres
                        dbFksToDrop.add(dbFk);
                        mpdrFksToAddDeleteCascade.add(mpdrFk);
                    }
                    if (!mpdrFk.isDeleteCascade() && dbFk.isDeleteCascade()) {
                        //On supprime la fk et on la recrée comme une fk standard
                        dbFksToDrop.add(dbFk);
                        mpdrFksToAdd.add(mpdrFk);
                    }
                }
            }
        }

    }

    private void compareUnique(MPDRTable mpdrTable, MPDRTable dbTable) {
        compareUniqueToAdd(mpdrTable, dbTable);
        compareUniqueToDrop(mpdrTable, dbTable);
    }

    private void compareUniqueToDrop(MPDRTable mpdrTable, MPDRTable dbTable) {
        List<String> mpdrUniqueNameList = new ArrayList();
        for (MPDRUnique mpdrUnique : mpdrTable.getMPDRUniques()) {
            mpdrUniqueNameList.add(mpdrUnique.getName().toUpperCase());
        }

        for (MPDRUnique dbUnique : dbTable.getMPDRUniques()) {
            if (!mpdrUniqueNameList.contains(dbUnique.getName())) {
                dbUniquesToDrop.add(dbUnique);
            }
        }
    }

    private void compareUniqueToAdd(MPDRTable mpdrTable, MPDRTable dbTable) {
        List<String> dbUniqueNameList = new ArrayList<>();
        for (MPDRUnique dbUnique : dbTable.getMPDRUniques()) {
            dbUniqueNameList.add(dbUnique.getName());
        }
        for (MPDRUnique mpdrUnique : mpdrTable.getMPDRUniques()) {
            if (!dbUniqueNameList.contains(mpdrUnique.getName())) {
                mpdrUniquesToAdd.add(mpdrUnique);
            }
        }
    }


    private void compareSequence(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        compareSequencesToCreate(mpdrColumn, dbColumn);
        compareSequencesToDrop(mpdrColumn, dbColumn);
    }

    private void compareSequencesToCreate(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        //On compare uniquement si la colonne du mpdr possède un enfant, donc une séquence
        if (!mpdrColumn.getChilds().isEmpty()) {
            if (!dbColumn.getChilds().isEmpty()) {
                if (!mpdrColumn.getMPDRSequence().getName().toUpperCase().equals(dbColumn.getMPDRSequence().getName())) {
                    mpdrSequencesToCreate.add(mpdrColumn.getMPDRSequence());
                }
            } else {
                mpdrSequencesToCreate.add(mpdrColumn.getMPDRSequence());
            }
        }
    }

    //Attention, si la PK n'était pas sur la bonne colonne, la séquence sera effacée et recréée
    private void compareSequencesToDrop(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        //On compare uniquement si la colonne de la db possède un enfant, donc une séquence
        if (!dbColumn.getChilds().isEmpty()) {
            if (mpdrColumn.getMPDRSequence() != null) {
                if (!dbColumn.getMPDRSequence().getName().equals(mpdrColumn.getMPDRSequence().getName().toUpperCase())) {
                    dbSequencesToDrop.add(dbColumn.getMPDRSequence());
                }
            } else {
                dbSequencesToDrop.add(dbColumn.getMPDRSequence());
            }
        }
    }

    private void compareSequencesInTableToDrop() {
        //Si la séquence est dans une table qui doit être supprimée
        for (MPDRTable dbTableToDrop : dbTablesToDrop) {
            for (MPDRColumn dbColumnInTableToDrop : dbTableToDrop.getMPDRColumns()) {
                if (!dbColumnInTableToDrop.getChilds().isEmpty()) {
                    if (dbColumnInTableToDrop.getMPDRSequence() != null) {
                        dbSequencesToDrop.add(dbColumnInTableToDrop.getMPDRSequence());
                    }
                }
            }
        }
    }


    private void compareTriggerToCreateOrReplace(MPDRBoxTriggers mpdrBoxTriggers) {
        if (mpdrBoxTriggers.getAllTriggers() != null) {
            for (MPDRTrigger trigger : mpdrBoxTriggers.getAllTriggers()) {
                mpdrTriggersToCreateOrReplace.add(trigger);
            }
        }
    }

    private void compareTriggersToDrop(MPDRBoxTriggers mpdrBoxTriggers, MPDRBoxTriggers dbBoxTriggers) {
        if (mpdrBoxTriggers.getAllTriggers() != null && dbBoxTriggers.getAllTriggers() != null) {
            List<MPDRTrigger> sameTriggers = new ArrayList<>();
            List<MPDRTrigger> differentTriggers = new ArrayList<>();
            for (MPDRTrigger dbTrigger : dbBoxTriggers.getAllTriggers()) {
                for (MPDRTrigger mpdrTrigger : mpdrBoxTriggers.getAllTriggers()) {
                    if (mpdrTrigger.getName().toUpperCase().equals(dbTrigger.getName())) {
                        if (!sameTriggers.contains(dbTrigger)) {
                            sameTriggers.add(dbTrigger);
                        }
                    } else {
                        if (!differentTriggers.contains(dbTrigger)) {
                            differentTriggers.add(dbTrigger);
                        }
                    }
                }
            }
            for (MPDRTrigger differentTrigger : differentTriggers) {
                if (!sameTriggers.contains(differentTrigger)) {
                    dbTriggersToDrop.add(differentTrigger);
                }
            }
        }
    }


    private void compareTriggers(MPDRBoxTriggers mpdrBoxTriggers, MPDRBoxTriggers dbBoxTriggers) {
        compareTriggerToCreateOrReplace(mpdrBoxTriggers);
        compareTriggersToDrop(mpdrBoxTriggers, dbBoxTriggers);

    }

    private void comparePackages(MPDROracleBoxPackages mpdrOracleBoxPackages, MPDROracleBoxPackages
            dbOracleBoxPackages) {
        comparePackagesToCreateOrReplace(mpdrOracleBoxPackages);
        comparePackagesToDrop(mpdrOracleBoxPackages, dbOracleBoxPackages);
    }

    private void comparePackagesToCreateOrReplace(MPDROracleBoxPackages mpdrOracleBoxPackages) {
        if (mpdrOracleBoxPackages != null) {
            for (MPDRPackage mpdrPackage : mpdrOracleBoxPackages.getAllPackages()) {
                mpdrPackagesToCreateOrReplace.add(mpdrPackage);
            }
        }
    }

    private void comparePackagesToDrop(MPDROracleBoxPackages mpdrOracleBoxPackages, MPDROracleBoxPackages dbOracleBoxPackages) {
        if (mpdrOracleBoxPackages != null && dbOracleBoxPackages != null) {
            List<MPDRPackage> samePackages = new ArrayList<>();
            List<MPDRPackage> differentPackages = new ArrayList<>();
            for (MPDRPackage dbPackage : dbOracleBoxPackages.getAllPackages()) {
                for (MPDRPackage mpdrPackage : mpdrOracleBoxPackages.getAllPackages()) {
                    if (mpdrPackage.getName().toUpperCase().equals(dbPackage.getName())) {
                        if (!samePackages.contains(dbPackage)) {
                            samePackages.add(dbPackage);
                        }
                    } else {
                        if (!differentPackages.contains(dbPackage))
                            differentPackages.add(dbPackage);
                    }
                }
            }
            for (MPDRPackage differentPackage : differentPackages) {
                if (!samePackages.contains(differentPackage)) {
                    dbPackagesToDrop.add(differentPackage);
                }
            }
        }
    }

    private void comparePackageInTableToDrop() {
        //Si la package est dans une table qui doit être supprimée
        for (MPDRTable dbTableToDrop : dbTablesToDrop) {
            for (MPDRPackage dbPackageInTableToDrop : dbTableToDrop.getMPDRContTAPIs().getMPDRBoxPackages().getAllPackages()) {
                if (dbPackageInTableToDrop != null) {
                    dbPackagesToDrop.add(dbPackageInTableToDrop);
                }
            }
        }
    }

    //Elle permet de ne pas supprimer les contraintes qui sont déjà supprimées implicitement lors du drop column cascade
    // Utilisation d'Iterator car sinon on ne peut pas supprimer un objet
    // d'une liste sur laquelle on boucle (currentmodificationexception)
    private void removeConstraintFromDropColumn() {
        List<String> listNameColumnToDrop = new ArrayList<>();
        List<String> listSplitElement = new ArrayList<>();
        List<Integer> listTargetIdColumnToDrop = new ArrayList<>();
        String regex = Preferences.MDR_TABLE_SEP_WORD; // par défaut le caractère "_"
        for (MPDRColumn dbColumnToDrop : dbColumnsToDrop) {
            listNameColumnToDrop.add(dbColumnToDrop.getName());
            listTargetIdColumnToDrop.add(dbColumnToDrop.getId());
        }
        for (Iterator<MPDRPK> iterator = dbPksToDrop.iterator(); iterator.hasNext(); ) {
            MPDRPK dbPk = iterator.next();
            if (listTargetIdColumnToDrop.contains(dbPk.getTargets())) {
                iterator.remove();
            }
        }
        for (Iterator<MPDRUnique> iterator = dbUniquesToDrop.iterator(); iterator.hasNext(); ) {
            MPDRUnique dbUnique = iterator.next();
            for (String splitter : dbUnique.getName().split(regex)) {
                listSplitElement.add(splitter);
            }
            for (String splitElement : listSplitElement) {
                if (listNameColumnToDrop.contains(splitElement)) {
                    iterator.remove();
                }
            }
        }
        for (Iterator<MPDRCheck> iterator = dbChecksToDrop.iterator(); iterator.hasNext(); ) {
            MPDRCheck dbCheck = iterator.next();
            for (String splitter : dbCheck.getName().split(regex)) {
                listSplitElement.add(splitter);
            }
            for (String splitElement : listSplitElement) {
                if (listNameColumnToDrop.contains(splitElement)) {
                    iterator.remove();
                }
            }
        }
        for (Iterator<MPDRFK> iterator = dbFksToDrop.iterator(); iterator.hasNext(); ) {
            MPDRFK dbFk = iterator.next();
            for (String splitter : dbFk.getName().split(regex)) {
                listSplitElement.add(splitter);
            }
            for (String splitElement : listSplitElement) {
                if (listNameColumnToDrop.contains(splitElement)) {
                    iterator.remove();
                }
            }
        }
    }

    public List<MPDRTable> getMpdrTablesToCreate() {
        return mpdrTablesToCreate;
    }

    public List<MPDRTable> getDbTablesToDrop() {
        return dbTablesToDrop;
    }

    public List<MPDRTable> getMpdrTablesSameName() {
        return mpdrTablesSameName;
    }

    public List<MPDRColumn> getMpdrColumnsToAdd() {
        return mpdrColumnsToAdd;
    }

    public List<MPDRColumn> getDbColumnsToDrop() {
        return dbColumnsToDrop;
    }

    public List<MPDRColumn> getMpdrColumnsToModify() {
        return mpdrColumnsToModify;
    }

    public List<MPDRColumn> getMpdrColumnsToModifyAddNotNull() {
        return mpdrColumnsToModifyAddNotNull;
    }

    public List<MPDRColumn> getMpdrColumnsToModifyDropNotNull() {
        return mpdrColumnsToModifyDropNotNull;
    }

    public List<MPDRColumn> getMpdrColumnsToModifyAddOrModifyDefault() {
        return mpdrColumnsToModifyAddOrModifyDefault;
    }

    public List<MPDRColumn> getMpdrColumnsToModifyDropDefault() {
        return mpdrColumnsToModifyDropDefault;
    }

    public List<MPDRUnique> getMpdrUniquesToAdd() {
        return mpdrUniquesToAdd;
    }

    public List<MPDRUnique> getDbUniquesToDrop() {
        return dbUniquesToDrop;
    }

    public List<MPDRCheck> getMpdrChecksToAdd() {
        return mpdrChecksToAdd;
    }

    public List<MPDRCheck> getDbChecksToDrop() {
        return dbChecksToDrop;
    }

    public List<MPDRPK> getMpdrPksToAdd() {
        return mpdrPksToAdd;
    }


    public List<MPDRPK> getDbPksToDrop() {
        return dbPksToDrop;
    }

    public List<MPDRFK> getMpdrFksToAdd() {
        return mpdrFksToAdd;
    }

    public List<MPDRFK> getDbFksToDrop() {
        return dbFksToDrop;
    }

    public List<MPDRSequence> getMpdrSequencesToCreate() {
        return mpdrSequencesToCreate;
    }

    public List<MPDRSequence> getDbSequencesToDrop() {
        return dbSequencesToDrop;
    }

    public MPDROracleModel getMpdrModel() {
        return mpdrModel;
    }

    public MPDROracleModel getDbModelOracle() {
        return dbModelOracle;
    }

    public DbFetcherOracle getDbFetcherOracle() {
        return dbFetcherOracle;
    }

    public List<MPDRTrigger> getMpdrTriggersToCreateOrReplace() {
        return mpdrTriggersToCreateOrReplace;
    }

    public List<MPDRTrigger> getDbTriggersToDrop() {
        return dbTriggersToDrop;
    }

    public List<MPDRPackage> getMpdrPackagesToCreateOrReplace() {
        return mpdrPackagesToCreateOrReplace;
    }

    public List<MPDRPackage> getDbPackagesToDrop() {
        return dbPackagesToDrop;
    }

    public List<MPDRFK> getMpdrFksToAddDeleteCascade() {
        return mpdrFksToAddDeleteCascade;
    }

    public List<MPDRFK> getDbFksToDropDeleteCascade() {
        return dbFksToDropDeleteCascade;
    }
}

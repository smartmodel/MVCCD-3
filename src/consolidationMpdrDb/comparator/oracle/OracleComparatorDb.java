package consolidationMpdrDb.comparator.oracle;

import connections.ConConnection;
import consolidationMpdrDb.comparator.MpdrDbComparator;
import consolidationMpdrDb.fetcher.oracle.DbFetcherOracle;
import mpdr.*;
import mpdr.oracle.MPDROracleModel;
import mpdr.tapis.*;
import mpdr.tapis.oracle.MPDROracleBoxPackages;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//ATTENTION, pour oracle, on utilise toUpperCase pour les noms
public class OracleComparatorDb extends MpdrDbComparator {

    private List<MPDRTable> mpdrTablesSameName = new ArrayList<>();
    private List<MPDRTable> mpdrTablesToCreate = new ArrayList<>();
    private List<MPDRTable> mpdrTablesToDrop = new ArrayList<>();
    private List<MPDRColumn> mpdrColumnsToAdd = new ArrayList<>();
    private List<MPDRColumn> mpdrColumnsToDrop = new ArrayList<>();
    private List<MPDRColumn> mpdrColumnsToModify = new ArrayList<>();
    private List<MPDRColumn> mpdrColumnsToModifyAddNotNull = new ArrayList<>();
    private List<MPDRColumn> mpdrColumnsToModifyDropNotNull = new ArrayList<>();
    private List<MPDRColumn> mpdrColumnsToModifyAddOrModifyDefault = new ArrayList<>();
    private List<MPDRColumn> mpdrColumnsToModifyDropDefault = new ArrayList<>();
    private List<MPDRUnique> mpdrUniquesToCreate = new ArrayList<>();
    private List<MPDRUnique> mpdrUniquesToDrop = new ArrayList<>();
    private List<MPDRCheck> mpdrChecksToCreate = new ArrayList<>();
    private List<MPDRCheck> mpdrChecksToDrop = new ArrayList<>();
    private List<MPDRPK> mpdrpksToCreate = new ArrayList<>();
    private List<MPDRPK> mpdrpkstoDrop = new ArrayList<>();
    private List<MPDRFK> mpdrfksToCreate = new ArrayList<>();
    private List<MPDRFK> mpdrfksToDrop = new ArrayList<>();
    private List<MPDRSequence> mpdrSequencesToCreate = new ArrayList<>();
    private List<MPDRSequence> mpdrSequencesToDrop = new ArrayList<>();
    private List<MPDRTrigger> mpdrTriggersToCreate = new ArrayList<>();
    private List<MPDRTrigger> mpdrTriggersToDrop = new ArrayList<>();
    private List<MPDRPackage> mpdrPackagesToCreateOrReplace = new ArrayList<>();
    private List<MPDRPackage> mpdrPackagesToDrop = new ArrayList<>();


    private MPDROracleModel mpdrModel;
    private MPDROracleModel mpdrDbModelOracle;
    private DbFetcherOracle dbFetcherOracle;

    public OracleComparatorDb(MPDROracleModel mpdrModel, ConConnection conConnection, Connection connection) throws SQLException {
        this.mpdrModel = mpdrModel;
        dbFetcherOracle = new DbFetcherOracle(conConnection, connection);
        dbFetcherOracle.fetch();
        mpdrDbModelOracle = dbFetcherOracle.getDbModel();
    }

    public void compare() {
        compareTables();
    }

    public void compareTables() {
        compareTablesIdenticales();
        compareTablesToCreate();
        compareTablesToDrop();
    }

    public void compareTablesIdenticales() {
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            MPDRTable dbTable = findDbTableByName(mpdrTable);
            if (dbTable != null) {
                mpdrTablesSameName.add(mpdrTable);
                compareColumns(mpdrTable.getMPDRColumns(), dbTable.getMPDRColumns());
                compareUnique(mpdrTable, dbTable);
                compareTriggers(mpdrTable.getMPDRBoxTriggers(), dbTable.getMPDRBoxTriggers());
                comparePackages((MPDROracleBoxPackages) mpdrTable.getMPDRContTAPIs().getMPDRBoxPackages(), (MPDROracleBoxPackages) dbTable.getMPDRContTAPIs().getMPDRBoxPackages());
            }
        }
    }

    public void compareTablesToCreate() {
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            MPDRTable dbTable = findDbTableByName(mpdrTable);
            if (dbTable == null) {
                mpdrTablesToCreate.add(mpdrTable);
            }
        }
    }

    public void compareTablesToDrop() {
        for (MPDRTable dbTable : mpdrDbModelOracle.getMPDRTables()) {
            MPDRTable mpdrTable = findMpdrTableByName(dbTable);
            if (mpdrTable == null) {
                mpdrTablesToDrop.add(dbTable);
            }
        }
    }

    public MPDRTable findDbTableByName(MPDRTable mpdrTable) {
        for (MPDRTable dbTable : mpdrDbModelOracle.getMPDRTables()) {
            if (mpdrTable.getName().toUpperCase().equals(dbTable.getName())) {
                return dbTable;
            }
        }
        return null;
    }

    public MPDRTable findMpdrTableByName(MPDRTable dbTable) {
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            if (dbTable.getName().equals(mpdrTable.getName().toUpperCase())) {
                return mpdrTable;
            }
        }
        return null;
    }

    public boolean compareTableName(MPDRTable mpdrTable, MPDRTable dbTable) {
        return mpdrTable.getName().toUpperCase().equals(dbTable.getName());
    }


    public void compareColumns(List<MPDRColumn> mpdrColumns, List<MPDRColumn> dbColumns) {
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

    public void compareColumnsIdenticales(MPDRColumn mpdrColumn, List<MPDRColumn> dbColumns) {
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
            mpdrColumnsToDrop.add(dbColumn);
        }
    }

    public boolean compareColumnDataType(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        return mpdrColumn.getDatatypeLienProg().toUpperCase().equals(dbColumn.getDatatypeLienProg());
    }

    public boolean compareColumnSize(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        return mpdrColumn.getSize().equals(dbColumn.getSize());
    }

    public boolean compareColumnScale(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        return mpdrColumn.getSize().equals(dbColumn.getSize());
    }

    public void compareColumnMandatory(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
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

    public void compareColumnInitValue(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        compareColumnDropInitValue(mpdrColumn, dbColumn);
        compareColumnAddOrModifyInitValue(mpdrColumn, dbColumn);
    }

    public void compareColumnDropInitValue(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        if (mpdrColumn.getInitValue().equals("") && dbColumn.getInitValue() != null) {
            mpdrColumnsToModifyDropDefault.add(mpdrColumn);
        }
    }

    //L'ajout ou la modification de la clause ont la même instruction SQL
    public void compareColumnAddOrModifyInitValue(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        if (!mpdrColumn.getInitValue().equals("")) {
            if (!mpdrColumn.getInitValue().toUpperCase().equals(dbColumn.getInitValue().toUpperCase())) {
                mpdrColumnsToModifyAddOrModifyDefault.add(mpdrColumn);
            }
        }
    }

    public void compareUnique(MPDRTable mpdrTable, MPDRTable dbTable) {
        compareUniqueToCreate(mpdrTable, dbTable);
        compareUniqueToDrop(mpdrTable, dbTable);
    }

    public void compareUniqueToDrop(MPDRTable mpdrTable, MPDRTable dbTable) {
        for (MPDRUnique dbUnique : dbTable.getMPDRUniques()) {
            if (!mpdrTable.getMPDRUniques().contains(dbUnique.getName())) {
                mpdrUniquesToDrop.add(dbUnique);
            }
        }
    }

    public void compareUniqueToCreate(MPDRTable mpdrTable, MPDRTable dbTable) {
        for (MPDRUnique mpdrUnique : mpdrTable.getMPDRUniques()) {
            if (!dbTable.getMPDRUniques().contains(mpdrUnique.getName())) {
                mpdrUniquesToCreate.add(mpdrUnique);
            }
        }
    }

    public void compareSequence(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        compareSequencesToCreate(mpdrColumn, dbColumn);
        compareSequencesToDrop(mpdrColumn, dbColumn);
    }

    public void compareSequencesToCreate(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        //On compare uniquement si la colonne du mpdr possède un enfant, donc une séquence
        if (!mpdrColumn.getChilds().isEmpty()) {
            if (!mpdrColumn.getMPDRSequence().getName().toUpperCase().equals(dbColumn.getMPDRSequence().getName())) {
                mpdrSequencesToCreate.add(mpdrColumn.getMPDRSequence());
            }
        }
    }

    public void compareSequencesToDrop(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        //On compare uniquement si la colonne de la db possède un enfant, donc une séquence
        if (!dbColumn.getChilds().isEmpty()) {
            if (!dbColumn.getMPDRSequence().getName().equals(mpdrColumn.getMPDRSequence().getName().toUpperCase())) {
                mpdrSequencesToDrop.add(mpdrColumn.getMPDRSequence());
            }
        }
    }

    public void compareTriggerToCreateOrReplace(MPDRBoxTriggers mpdrBoxTriggers){
        if (mpdrBoxTriggers.getAllTriggers() != null ) {
            for (MPDRTrigger trigger : mpdrBoxTriggers.getAllTriggers()) {
                mpdrTriggersToCreate.add(trigger);
            }
        }
    }
    public void compareTriggersToDrop(MPDRBoxTriggers mpdrBoxTriggers, MPDRBoxTriggers dbBoxTriggers) {
        if (mpdrBoxTriggers.getAllTriggers() != null && dbBoxTriggers.getAllTriggers() != null) {
            for (MPDRTrigger trigger : dbBoxTriggers.getAllTriggers()) {
                if(mpdrBoxTriggers.getAllTriggers().contains(trigger.getName())){
                    mpdrTriggersToDrop.add(trigger);
                }
            }
        }
    }


    public void compareTriggers(MPDRBoxTriggers mpdrBoxTriggers, MPDRBoxTriggers dbBoxTriggers) {
        compareTriggerToCreateOrReplace(mpdrBoxTriggers);
        compareTriggersToDrop(mpdrBoxTriggers, dbBoxTriggers);

    }

    private void comparePackages(MPDROracleBoxPackages mpdrOracleBoxPackages, MPDROracleBoxPackages dbOracleBoxPackages) {
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
            for (MPDRPackage dbPackage : dbOracleBoxPackages.getAllPackages()) {
                //TODO NE FONCTIONNE PAS CAR LE GETCHILDS EST FAUX
                if (!mpdrOracleBoxPackages.getChilds().contains(dbPackage.getName())) {
                    mpdrPackagesToDrop.add(dbPackage);
                }
            }
        }
    }


    public void generateSQLScriptForTableToCreate() {
        for (MPDRTable mpdrTable : mpdrTablesToCreate) {
            //mpdrOracleGenerateSQLTable.generateSQLCreateTable(mpdrTable);
        }
    }

    public List<MPDRTable> getMpdrTablesToCreate() {
        return mpdrTablesToCreate;
    }

    public List<MPDRTable> getMpdrTablesToDrop() {
        return mpdrTablesToDrop;
    }

    public List<MPDRTable> getMpdrTablesSameName() {
        return mpdrTablesSameName;
    }

    public List<MPDRColumn> getMpdrColumnsToAdd() {
        return mpdrColumnsToAdd;
    }

    public List<MPDRColumn> getMpdrColumnsToDrop() {
        return mpdrColumnsToDrop;
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

    public List<MPDRUnique> getMpdrUniquesToCreate() {
        return mpdrUniquesToCreate;
    }

    public List<MPDRUnique> getMpdrUniquesToDrop() {
        return mpdrUniquesToDrop;
    }

    public List<MPDRCheck> getMpdrChecksToCreate() {
        return mpdrChecksToCreate;
    }

    public List<MPDRCheck> getMpdrChecksToDrop() {
        return mpdrChecksToDrop;
    }

    public List<MPDRPK> getMpdrpksToCreate() {
        return mpdrpksToCreate;
    }

    public List<MPDRPK> getMpdrpkstoDrop() {
        return mpdrpkstoDrop;
    }

    public List<MPDRFK> getMpdrfksToCreate() {
        return mpdrfksToCreate;
    }

    public List<MPDRFK> getMpdrfksToDrop() {
        return mpdrfksToDrop;
    }

    public List<MPDRSequence> getMpdrSequencesToCreate() {
        return mpdrSequencesToCreate;
    }

    public List<MPDRSequence> getMpdrSequencesToDrop() {
        return mpdrSequencesToDrop;
    }

    public MPDROracleModel getMpdrModel() {
        return mpdrModel;
    }

    public MPDROracleModel getMpdrDbModelOracle() {
        return mpdrDbModelOracle;
    }

    public DbFetcherOracle getDbFetcherOracle() {
        return dbFetcherOracle;
    }
}

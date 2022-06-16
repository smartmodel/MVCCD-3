package consolidationMpdrDb.comparator.oracle;

import connections.ConConnection;
import consolidationMpdrDb.comparator.MpdrDbComparator;
import consolidationMpdrDb.fetcher.oracle.DbFetcherOracle;
import mpdr.MPDRColumn;
import mpdr.MPDRTable;
import mpdr.oracle.MPDROracleModel;
import mpdr.tapis.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//ATTENTION, pour oracle, on utilise toUpperCase pour les noms
public class OracleComparatorDb extends MpdrDbComparator {

    List<MPDRTable> mpdrTablesSameName = new ArrayList<>();
    List<MPDRTable> mpdrTablesMissing = new ArrayList<>();
    private MPDROracleModel mpdrModel;
    private MPDROracleModel mpdrDbModelOracle;
    private DbFetcherOracle dbFetcherOracle;

    /*
        private MPDROracleGenerateSQLTable mpdrOracleGenerateSQLTable;
        private MPDROracleGenerateSQLFK mpdrOracleGenerateSQLFK;
        private MPDROracleGenerateSQLSequence mpdrOracleGenerateSQLSequence;
        private MPDROracleGenerateSQLTrigger mpdrOracleGenerateSQLTrigger;
        private MPDROracleGenerateSQLFunction mpdrOracleGenerateSQLFunction;
        private MPDROracleGenerateSQLPackage mpdrOracleGenerateSQLPackage;
        private MPDROracleGenerateSQLDynamicCode mpdrOracleGenerateSQLCodeDynamic;
        private MPDROracleGenerateSQLIndex mpdrOracleGenerateSQLIndex;
        private MPDROracleGenerateSQLView mpdrOracleGenerateSQLView;
    */
    public OracleComparatorDb(MPDROracleModel mpdrModel, ConConnection conConnection, Connection connection) throws SQLException {
        this.mpdrModel = mpdrModel;
        dbFetcherOracle = new DbFetcherOracle(conConnection, connection);
        dbFetcherOracle.fetch();
        mpdrDbModelOracle = dbFetcherOracle.getDbModel();
    }

    public void compare() {
        compareTables();
    }

    //ajoute toutes les tables qui ont un nom identique entre le mpdr et la Db, dans la liste mpdrTablesSameName
    public void compareTables() {
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            for (MPDRTable dbTable : mpdrDbModelOracle.getMPDRTables()) {
                if (compareTableName(mpdrTable, dbTable)) {
                    mpdrTablesSameName.add(mpdrTable);
                    compareColumns(mpdrTable.getMPDRColumns(), dbTable.getMPDRColumns());
                    compareTriggers(mpdrTable.getMPDRBoxTriggers(), dbTable.getMPDRBoxTriggers());
                    //comparePackages((MPDROracleBoxPackages) mpdrTable.getMPDRContTAPIs().getMPDRBoxPackages(), (MPDROracleBoxPackages) dbTable.getMPDRContTAPIs().getMPDRBoxPackages());
                } else {
                    //FAUX add trop de tables
                    mpdrTablesMissing.add(mpdrTable);
                }
            }
        }
    }


    public void compareTables2() {
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            boolean dbTableByName = findDbTableByName();
            }
    }

    public boolean findDbTableByName(){
return false;
    }

    public boolean compareTableName(MPDRTable mpdrTable, MPDRTable dbTable) {
        return mpdrTable.getName().toUpperCase().equals(dbTable.getName());
    }


    public void compareColumns(List<MPDRColumn> mpdrColumns, List<MPDRColumn> dbColumns) {
        for (MPDRColumn mpdrColumn : mpdrColumns) {
            for (MPDRColumn dbColumn : dbColumns) {
                compareColumnName(mpdrColumn, dbColumn);
            }

        }

    }

    public boolean compareColumnName(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        if (mpdrColumn.getName().toUpperCase().equals(dbColumn.getName().toUpperCase())) {
            //Si le nom est identique, on compare alors les attributs de la colonne
            boolean columnIdentique = true;
            if (!compareColumnDataType(mpdrColumn, dbColumn)) {
                columnIdentique = false;
            }
            if (!compareColumnSize(mpdrColumn, dbColumn)) {
                columnIdentique = false;
            }
            if (!compareColumnScale(mpdrColumn, dbColumn)) {
                columnIdentique = false;
            }
            if (compareColumnMandatory(mpdrColumn, dbColumn)) {
                columnIdentique = false;
            }
            if (compareColumnInitValue(mpdrColumn, dbColumn)) {
                columnIdentique = false;
            }
            if (!columnIdentique) {
                //méthode qui fera : ALTER TABLE MODIFY {+mpdrColumn+}...
            }

            if (compareSequencesName(mpdrColumn, dbColumn)) {

            }
            return true;
        }
        return false;
    }

    public boolean compareColumnDataType(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        return mpdrColumn.getDatatypeLienProg().equals(dbColumn.getDatatypeLienProg());
    }

    public boolean compareColumnSize(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        return mpdrColumn.getSize().equals(dbColumn.getSize());
    }

    public boolean compareColumnScale(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        return mpdrColumn.getSize().equals(dbColumn.getSize());
    }

    public boolean compareColumnMandatory(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        return mpdrColumn.isMandatory() == dbColumn.getMandatory();
    }

    public boolean compareColumnInitValue(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        return mpdrColumn.getInitValue().equals(dbColumn.getInitValue());
    }

    public boolean compareSequencesName(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        if (mpdrColumn.getMPDRSequence() != null && dbColumn.getMPDRSequence() != null) {
            return mpdrColumn.getMPDRSequence().getName().equals(dbColumn.getMPDRSequence().getName());
        }
        return false;
    }

    public void compareTriggers(MPDRBoxTriggers mpdrBoxTriggers, MPDRBoxTriggers dbBoxTriggers) {
        if (mpdrBoxTriggers.getAllTriggers() != null && dbBoxTriggers.getAllTriggers() != null) {
            for (MPDRTrigger mpdrTrigger : mpdrBoxTriggers.getAllTriggers()) {
                for (MPDRTrigger dbTrigger : dbBoxTriggers.getAllTriggers()) {
                    compareTriggerName(mpdrTrigger, dbTrigger);
                }
            }
        }
    }

    public boolean compareTriggerName(MPDRTrigger mpdrTrigger, MPDRTrigger dbTrigger) {
        return mpdrTrigger.getName().equals(dbTrigger.getName());
    }

    //TODO VINCENT
    // Ne fonctionne pas car mpdrOracleBoxPackages est nullpointer car non créer par ADM, est-ce parce que les packages ne sont pas terminé
    //ou car pas forcément de package dans un MPRDModel?
    /*
    private void comparePackages(MPDROracleBoxPackages mpdrOracleBoxPackages, MPDROracleBoxPackages dbOracleBoxPackages) {
        for (MPDRPackage mpdrPackage : mpdrOracleBoxPackages.getAllPackages()) {
            for (MPDRPackage dbPackage : dbOracleBoxPackages.getAllPackages()) {
                comparePackagesName(mpdrPackage, dbPackage);
            }
        }
    }
*/
    private boolean comparePackagesName(MPDRPackage mpdrPackage, MPDRPackage dbPackage) {
        return mpdrPackage.getName().equals(dbPackage.getName());
    }

    public void generateSQLScriptForMissingTable() {
    }


}

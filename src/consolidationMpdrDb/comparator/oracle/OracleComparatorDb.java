package consolidationMpdrDb.comparator.oracle;

import connections.ConConnection;
import consolidationMpdrDb.comparator.MpdrDbComparator;
import consolidationMpdrDb.fetcher.oracle.DbFetcherOracle;
import generatorsql.generator.oracle.*;
import mpdr.MPDRColumn;
import mpdr.MPDRTable;
import mpdr.oracle.MPDROracleModel;
import mpdr.oracle.MPDROracleTable;
import mpdr.tapis.*;
import mpdr.tapis.oracle.MPDROracleBoxPackages;
import mpdr.tapis.oracle.MPDROraclePackage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleComparatorDb extends MpdrDbComparator{

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
        mpdrDbModelOracle = dbFetcherOracle.getMpdrDbModel();
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
                    //FAUX
                    mpdrTablesMissing.add(mpdrTable);
                }
            }
        }
    }

    public boolean compareTableName(MPDRTable mpdrTable, MPDRTable dbTable) {
        return mpdrTable.getName().equals(dbTable.getName());
    }


    public void compareColumns(List<MPDRColumn> mpdrColumns, List<MPDRColumn> dbColumns) {
        for (MPDRColumn mpdrColumn : mpdrColumns) {
            for (MPDRColumn dbColumn : dbColumns) {
                compareColumnName(mpdrColumn, dbColumn);
            }

        }

    }

    public boolean compareColumnName(MPDRColumn mpdrColumn, MPDRColumn dbColumn) {
        if (mpdrColumn.getName().equals(dbColumn.getName())) {
            //Si le nom est identique, on compare alors les attributs de la colonne
            if(!compareColumnDataType(mpdrColumn, dbColumn)){
                //ALTER TABLE MODIFY {+mpdrColumn+}
            }
            compareColumnSize(mpdrColumn, dbColumn);
            compareColumnScale(mpdrColumn, dbColumn);
            compareColumnMandatory(mpdrColumn, dbColumn);
            compareColumnInitValue(mpdrColumn, dbColumn);
            compareSequencesName(mpdrColumn, dbColumn);
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
        if(mpdrColumn.getMPDRSequence()!=null && dbColumn.getMPDRSequence()!=null) {
            return mpdrColumn.getMPDRSequence().getName().equals(dbColumn.getMPDRSequence().getName());
        }
        return false;
    }

    public void compareTriggers(MPDRBoxTriggers mpdrBoxTriggers, MPDRBoxTriggers dbBoxTriggers) {
        if (mpdrBoxTriggers.getAllTriggers()!=null && dbBoxTriggers.getAllTriggers()!=null) {
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

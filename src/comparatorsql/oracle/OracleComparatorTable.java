package comparatorsql.oracle;

import comparatorsql.elementASupprimer.DbOracleStructure;
import comparatorsql.elementASupprimer.DbTable;
import mdr.MDRTable;
import mpdr.MPDRModel;

import java.util.ArrayList;
import java.util.List;

public class OracleComparatorTable {

    private MPDRModel mpdrModel;
    private DbOracleStructure dbOracleStructure;

    public OracleComparatorTable(MPDRModel mpdrModel, DbOracleStructure dbOracleStructure) {
        this.mpdrModel = mpdrModel;
        this.dbOracleStructure = dbOracleStructure;
    }

    public List<MDRTable> getMdrTableList (){
        return mpdrModel.getMPDRContTables().getMDRTables();
    }

    public List<DbTable> getDbTableList(){
        return dbOracleStructure.getTables();
    }

    //retourne toutes les tables qui ont un nom identique dans le mpdr et la Db
    public List<MDRTable> compareTable(){
    List<MDRTable> mdrTables = new ArrayList<>();
        for (MDRTable mdrTable : getMdrTableList()){
        for (DbTable dbTable : getDbTableList()) {
            if (compareTableName(mdrTable.getNames().getName30(), dbTable.getName())){
                mdrTables.add(mdrTable);
            }
        }
    }
        return mdrTables;
}

    public boolean compareMdrTable(MDRTable mdrTable1, MDRTable mdrTable2){
        if (mdrTable1.equals(mdrTable2)){
            return true;
        } else {
            return false;
        }
    }

    //récupération de la liste des tables qui sont uniquement dans le mpdr
    public List<MDRTable> fetchMdrMissingTableList(){
        List<MDRTable> mdrTables = compareTable();

        List<MDRTable> mdrTableList = getMdrTableList();
        mdrTableList.removeAll(mdrTables);

        return mdrTableList;
    }

    public boolean compareTableName(String mpdrTableName, String dbTableName){
        if (mpdrTableName.equals(dbTableName)){
            System.out.println("table : " + mpdrTableName);
            return true;
        } else {
            return false;
        }
    }


}

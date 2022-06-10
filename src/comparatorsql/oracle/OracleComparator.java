package comparatorsql.oracle;

import comparatorsql.Comparator;
import comparatorsql.elementASupprimer.DbOracleStructure;
import mdr.MDRTable;
import mpdr.MPDRModel;

import java.util.List;

public class OracleComparator implements Comparator {

    private  MPDRModel mpdrModel;
    private DbOracleStructure dbOracleStructure;
    private OracleComparatorTable oracleComparatorTable;

    public OracleComparator(MPDRModel mpdrModel, DbOracleStructure dbOracleStructure) {
        this.mpdrModel = mpdrModel;
        this.dbOracleStructure = dbOracleStructure;
        oracleComparatorTable = new OracleComparatorTable(mpdrModel,dbOracleStructure);
    }

    public void comparator(MPDRModel mpdrModel, DbOracleStructure dbOracleStructure) {
        List<MDRTable> mdrTables = oracleComparatorTable.compareTable();
        List<MDRTable> missingMdrTables = oracleComparatorTable.fetchMdrMissingTableList();
        System.out.println(mdrTables.toString());
        System.out.println(missingMdrTables.toString());



    }

    public void generateSQLScriptForMissingTable(){


    }




}

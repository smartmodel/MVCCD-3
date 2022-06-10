package comparatorsql.elementASupprimer;

import main.MVCCDElement;
import mpdr.MPDRContColumns;
import mpdr.MPDRContConstraints;
import mpdr.MPDRTable;
import mpdr.oracle.MPDROracleModel;

import java.util.ArrayList;

public class MDPROracleConverter {

    DbOracleStructure dbOracleStructure = new DbOracleStructure();
    private String schemaDB;

    public MDPROracleConverter(String schemaDB) {
        this.schemaDB = schemaDB;
    }

    public DbOracleStructure converter(MPDROracleModel mpdrModel) {

        convertTable(mpdrModel);
        return dbOracleStructure;
    }

    public void convertTable(MPDROracleModel mpdrModel){
        ArrayList<MPDRTable> mpdrTables = mpdrModel.getMPDRTables();

        for (MPDRTable mpdrTable : mpdrTables) {
            DbTable dbTable = new DbTable(schemaDB, mpdrTable.getName());
            convertColumn(mpdrTable, dbTable);
            dbOracleStructure.getTables().add(dbTable);
        }


    }

    private void convertColumn(MPDRTable mpdrTable, DbTable dbTable) {
        //TODO VINCENT
        //Ajouter une nouvelle fonction getColumns qui n'a pas besoin de metadata et de database
        //table.getColumns(metaData, databaseName, schemaDB, mpdrTable.getName());
        for (MVCCDElement child : mpdrTable.getChilds()) {
            if (child instanceof MPDRContColumns) {
                //column
            } else if (child instanceof MPDRContConstraints) {
                //convertConstraint(...)
            }
        }
    }
    private void convertConstraint(MVCCDElement mvccdElement, DbTable dbTable) {

    }

}

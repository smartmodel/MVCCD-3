package comparatorsql.fetcher.oracle;

import comparatorsql.RecuperationConnectionDB;
import comparatorsql.fetcher.DbFetcher;
import connections.ConConnection;
import main.MVCCDElementFactory;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.oracle.MPDROraclePK;
import mpdr.services.MPDRTableService;
import mpdr.tapis.oracle.MPDROraclePackage;
import mpdr.tapis.oracle.MPDROracleTrigger;

import java.sql.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbFetcherOracle extends DbFetcher {

    private final Connection connection;
    private final ConConnection conConnection;
    private DatabaseMetaData databaseMetaData;
    private String schemaDB;
    private String databaseName;
    private MPDRModel mpdrDbModel;

    public DbFetcherOracle() throws SQLException {
        RecuperationConnectionDB recuperationConnectionDB = new RecuperationConnectionDB();
        this.connection = recuperationConnectionDB.getConnection();
        this.conConnection = recuperationConnectionDB.getConConnection();
        this.databaseMetaData = this.connection.getMetaData();
        this.databaseName = this.conConnection.getDbName();
        this.schemaDB = this.conConnection.getUserName();
        this.mpdrDbModel = MVCCDElementFactory.instance().createMPDRModelOracle(null);
    }
    public DbFetcherOracle(ConConnection conConnection, Connection connection) throws SQLException {
        this.connection = connection;
        this.conConnection = conConnection;
        this.databaseMetaData = this.connection.getMetaData();
        this.databaseName = this.conConnection.getDbName();
        this.schemaDB = this.conConnection.getUserName();
        this.mpdrDbModel = MVCCDElementFactory.instance().createMPDRModelOracle(null);
    }

    public void fetch() throws SQLException {
        fetchTables();
        //ATTENTION, je ne récupère que les trigger, package et séquences qui sont liés à une table.
    }


    public void fetchTables() throws SQLException {
        String[] types = {"TABLE"};

        try (ResultSet result = databaseMetaData.getTables(databaseName, schemaDB, "%", types)) {
            while (result.next()) {
                MPDRTable mpdrTable = MVCCDElementFactory.instance()
                        .createMPDROracleTable(this.mpdrDbModel.getMPDRContTables(), null);
                mpdrTable.setName(result.getString("TABLE_NAME"));
                fetchColumns(mpdrTable);
                fetchPk(mpdrTable);
                fetchTriggers(mpdrTable);
                fetchPackages(mpdrTable);
                fetchSequences(mpdrTable);

            }
        }
    }
    public void fetchColumns(MPDRTable mpdrTable){
        try (ResultSet result = this.databaseMetaData.getColumns(this.databaseName, this.schemaDB, mpdrTable.getName(), null)) {
            while (result.next()) {
                MPDRColumn mpdrColumn = MVCCDElementFactory.instance().createMPDROracleColumn(mpdrTable.getMDRContColumns(), null);
                mpdrColumn.setName(result.getString("COLUMN_NAME"));
                mpdrColumn.setDatatypeLienProg(result.getString("TYPE_NAME"));
                if (result.getBoolean("NULLABLE")){
                    mpdrColumn.setMandatory(false); //Si nullable -> non obligatoire
                } else{
                    mpdrColumn.setMandatory(true); //Si non-nullable -> obligatoire
                }
                mpdrColumn.setSize(result.getInt("COLUMN_SIZE"));
                mpdrColumn.setScale(result.getInt("DECIMAL_DIGITS"));
                mpdrColumn.setInitValue(result.getString("COLUMN_DEF"));
                mpdrColumn.setOrder(result.getInt("ORDINAL_POSITION"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void fetchPk(MPDRTable mpdrTable) throws SQLException {
        try (ResultSet result = this.databaseMetaData.getPrimaryKeys(this.databaseName, this.schemaDB, mpdrTable.getName())) {
            while (result.next()) {
                    MPDROraclePK mpdrOraclePK = MVCCDElementFactory.instance().createMPDROraclePK(mpdrTable.getMDRContConstraints(), null);
                    mpdrOraclePK.setName(result.getString("PK_NAME"));
                    //TODO VINCENT
                    //MVCCDElementFactory.instance().createMPDROracleParameter();
            }
        }
    }
    public void fetchUnique(MPDRTable mpdrTable, MPDRColumn mpdrColumn){
        try (ResultSet result = databaseMetaData.getIndexInfo(databaseName, schemaDB, mpdrTable.getName(), true, true)) {
            while (result.next()) {
                    if (mpdrColumn.getName().equals(result.getString("COLUMN_NAME"))) {
                        MVCCDElementFactory.instance().createMPDROracleUnique(mpdrTable.getMDRContConstraints(), null);
                        //TODO VINCENT
                        //MVCCDElementFactory.instance().createMPDROracleParameter();
                    }
                }
            } catch (SQLException ex) {
            ex.printStackTrace();
            }
    }

    public void fetchCheck(MPDRTable mpdrTable, List<MPDRColumn> mpdrColumns){
        try (ResultSet result = databaseMetaData.getIndexInfo(databaseName, schemaDB, mpdrTable.getName(), true, true)) {
            while (result.next()) {
                //TODO VINCENT - A voir comment gérer plusieurs colonnes
                for (MPDRColumn mpdrColumn : mpdrColumns) {
                    if (mpdrColumn.getName().equals(result.getString("COLUMN_NAME"))) {
                        if (result.getString("FILTER_CONDITION") != null) {
                            MVCCDElementFactory.instance().createMPDROracleCheckSpecific(mpdrTable.getMDRContConstraints(), null);
                            //TODO VINCENT
                            //MVCCDElementFactory.instance().createMPDROracleParameter();
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void fetchSequences(MPDRTable mpdrTable) throws SQLException {
        StringBuilder requeteSQL = new StringBuilder();
        requeteSQL.append("SELECT sequence_name FROM user_sequences");
        System.out.println(requeteSQL);
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL.toString());
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()){
            if(findTableAccueilSequence(mpdrTable,rsCurseur.getString("SEQUENCE_NAME"))!=null){
                //TODO A vérifier comment sélectionner la bonne colonne
                MVCCDElementFactory.instance().createMPDROracleSequence(mpdrTable.getMPDRColumnPKProper(), null);
            }
        }
    }

    public void fetchPackages(MPDRTable mpdrTable) throws SQLException {
        StringBuilder requeteSQL = new StringBuilder();
        requeteSQL.append("SELECT DISTINCT(OBJECT_NAME) FROM USER_PROCEDURES WHERE OBJECT_TYPE='PACKAGE'");
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL.toString());
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()){
            if (findTableAccueilTriggerOrPackage(mpdrTable, rsCurseur.getString("OBJECT_NAME"))!=null) {
                if(mpdrTable.getMPDRContTAPIs().getMPDRBoxPackages()== null){
                    MVCCDElementFactory.instance().createMPDROracleBoxPackages(mpdrTable.getMPDRContTAPIs(), null);
                }
                MPDROraclePackage mpdrOraclePackage = MVCCDElementFactory.instance().createMPDROraclePackage(mpdrTable.getMPDRContTAPIs().getMPDRBoxPackages(),null);
                mpdrOraclePackage.setName(rsCurseur.getString("OBJECT_NAME"));
            }
        }
    }

    public void fetchTriggers(MPDRTable mpdrTable) throws SQLException {
        StringBuilder requeteSQL = new StringBuilder();
        requeteSQL.append("SELECT trigger_name FROM user_triggers");
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL.toString());
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()) {
            if (findTableAccueilTriggerOrPackage(mpdrTable, rsCurseur.getString("TRIGGER_NAME"))!=null) {
                if (mpdrTable.getMPDRContTAPIs().getMPDRBoxTriggers() == null) {
                    MVCCDElementFactory.instance().createMPDROracleBoxTriggers(mpdrTable.getMPDRContTAPIs(), null);
                }
                MPDROracleTrigger mpdrOracleTrigger = MVCCDElementFactory.instance().createMPDROracleTrigger(mpdrTable.getMPDRContTAPIs().getMPDRBoxTriggers(), null);
                mpdrOracleTrigger.setName(rsCurseur.getString("TRIGGER_NAME"));
            }
        }
    }

    public MPDRTable findTableAccueilSequence(MPDRTable mpdrTable, String sequenceName) {
        String seqTableName = sequenceName.split("_")[0];
        if(mpdrTable.getName().startsWith(seqTableName)) {
            return mpdrTable;
        }
        return null;
    }

    public MPDRTable findTableAccueilTriggerOrPackage(MPDRTable mpdrTable, String triggerOrPackageName) {
        String triggerOrPackageTableName = triggerOrPackageName.split("_")[0];
        if(mpdrTable.getName().startsWith(triggerOrPackageTableName)) {
                return mpdrTable;
            }
        return null;
    }

    public void fetchIndex(){
        // A développer ultérieurement
    }

/*
    // Se met dans la boucle de la colonne et vérifie en fonction du nom de la colonne.
    public void fetchPk2(MPDRTable mpdrTable, MPDRColumn mpdrColumn) throws SQLException {
        try (ResultSet result = this.databaseMetaData.getImportedKeys(this.databaseName, this.schemaDB, mpdrTable.getName())) {
            while (result.next()) {
                if (result.getString("PKCOLUMN_NAME").equals(mpdrColumn.getName())){
                    MPDROraclePK mpdrOraclePK = MVCCDElementFactory.instance().createMPDROraclePK(mpdrTable.getMDRContConstraints(), null);
                    mpdrOraclePK.setName(result.getString("PK_NAME"));
                    //A implémenter
                    //MVCCDElementFactory.instance().createMPDROracleParameter();
                }
            }
        }
    }
    */
}

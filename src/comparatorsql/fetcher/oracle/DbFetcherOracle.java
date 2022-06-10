package comparatorsql.fetcher.oracle;

import comparatorsql.RecuperationConnectionDB;
import comparatorsql.elementASupprimer.Column;
import comparatorsql.fetcher.DbFetcher;
import connections.ConConnection;
import main.MVCCDElementFactory;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.oracle.MPDROraclePK;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        fetchTables(this.databaseMetaData,this.databaseName,this.schemaDB,this.mpdrDbModel);
    }


    public void fetchTables(DatabaseMetaData metaData, String databaseName, String schemaName, MPDRModel mpdrModel) throws SQLException {
        String[] types = {"TABLE"};

        try (ResultSet result = metaData.getTables(databaseName, schemaName, "%", types)) {
            while (result.next()) {
                MPDRTable mpdrTable = MVCCDElementFactory.instance()
                        .createMPDROracleTable(mpdrModel.getMPDRContTables(), null);
                mpdrTable.setName(result.getString("TABLE_NAME"));
                fetchColumns(mpdrTable);
                fetchPk(mpdrTable);
                    /*
                Table table = new Table(databaseName, tableName);   // on met DatabaseName car MySQL n'utilise pas le schéma
                table.getColumns(metaData, databaseName, schemaName, tableName);
                table.getPrimaryKeys(metaData, databaseName, schemaName, tableName);
                table.getUniqueConstraint(metaData, databaseName, schemaName, tableName);*/

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
    public void fetchCheck(MPDRTable mpdrTable, MPDRColumn mpdrColumn){
        try (ResultSet result = databaseMetaData.getIndexInfo(databaseName, schemaDB, mpdrTable.getName(), true, true)) {
            while (result.next()) {
                if (mpdrColumn.getName().equals(result.getString("COLUMN_NAME"))) {
                    if(result.getString("FILTER_CONDITION")!=null) {
                        MVCCDElementFactory.instance().createMPDROracleCheckSpecific(mpdrTable.getMDRContConstraints(), null);
                        //TODO VINCENT
                        //MVCCDElementFactory.instance().createMPDROracleParameter();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void fetchSequences(){

    }
    public void fetchPackages(){

    }
    public void fetchTriggers(){

    }
    public MPDRTable findTableAccueil(){
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

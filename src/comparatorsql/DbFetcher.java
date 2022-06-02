package comparatorsql;

import comparatorsql.Utils.Tokenization;
import comparatorsql.elementASupprimer.Column;
import comparatorsql.elementASupprimer.DbTable;
import comparatorsql.elementASupprimer.Sequence;
import comparatorsql.elementASupprimer.Trigger;
import connections.ConConnection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import static preferences.Preferences.FETCHER_ORACLE_DBTABLE_NAME;

public class DbFetcher {

    private final static Logger LOGGER = Logger.getLogger(DbOracleStructure.class.getName());
    private DatabaseMetaData databaseMetaData;
    private String schemaDB;
    private final Connection connection;
    private final ConConnection conConnection;

    public DbFetcher() throws SQLException {
        RecuperationConnectionDB recuperationConnectionDB = new RecuperationConnectionDB();
        this.connection = recuperationConnectionDB.getConnection();
        this.conConnection = recuperationConnectionDB.getConConnection();
        this.databaseMetaData = this.connection.getMetaData();
        this.schemaDB = this.conConnection.getUserName();
    }

    public DbOracleStructure getDatabaseStructure() {
        try {
            DbOracleStructure dbOracleStructure = new DbOracleStructure();
            loadTables(this.connection.getMetaData(), this.conConnection.getDbName(), this.conConnection.getUserName(), dbOracleStructure);
            loadSequence(this.connection.getMetaData(), this.conConnection.getDbName(), this.conConnection.getUserName(), dbOracleStructure);
            loadTrigger(this.connection.getMetaData(), this.conConnection.getDbName(), this.conConnection.getUserName(), dbOracleStructure);
            return dbOracleStructure;
        } catch (Exception ignore) {
            System.out.println();
        }
        System.out.println();
        return null;
    }

    public void loadTables(DatabaseMetaData metaData, String databaseName, String schemaName, DbOracleStructure dbOracleStructure) throws SQLException {
        String[] types = {"TABLE"};

        try (ResultSet result = metaData.getTables(databaseName, schemaName, "%", types)) {
            while (result.next()) {
                String tableName = result.getString(FETCHER_ORACLE_DBTABLE_NAME);
                DbTable dbTable = new DbTable(databaseName, tableName);   // on met DatabaseName car MySQL n'utilise pas le schéma
                dbTable.getColumns(metaData, databaseName, schemaName, tableName);
                dbTable.getPrimaryKeys(metaData, databaseName, schemaName, tableName);
                dbTable.getUniqueConstraint(metaData, databaseName, schemaName, tableName);
                dbOracleStructure.getTables().add(dbTable);
            }
        }
        setTableTrimmedName(dbOracleStructure.getTables());
        setColumnTrimmedName(dbOracleStructure.getTables());
        LOGGER.info("Table count: " + dbOracleStructure.getTables().size());
    }

    //TODO VINCENT
    // vérifier le columnLabel
    public void loadSequence(DatabaseMetaData metaData, String databaseName, String schemaName, DbOracleStructure dbOracleStructure) throws SQLException {
        String[] types = {"SEQUENCE"};
        try (ResultSet result = metaData.getTables(databaseName, schemaName, "%", types)) {
            while (result.next()) {
                String sequenceName = result.getString("SEQUENCE_NAME");
                Sequence sequence = new Sequence(sequenceName);
                dbOracleStructure.getSequences().add(sequence);
            }
        }
    }

    public void loadTrigger (DatabaseMetaData metaData, String databaseName, String schemaName, DbOracleStructure dbOracleStructure) throws SQLException {
        String[] types = {"TRIGGER"};
        try (ResultSet result = metaData.getTables(databaseName, schemaName, "%", types)) {
            while (result.next()) {
                String triggerName = result.getString("TABLE_NAME");
                Trigger trigger = new Trigger(triggerName);
                dbOracleStructure.getTriggers().add(trigger);
            }
        }
    }

    private static void setTableTrimmedName(List<DbTable> dbTables) {
        String prefix = getTableCommonPrefix(dbTables);
        for (DbTable dbTable : dbTables) {
            dbTable.setTrimmedName(dbTable.getName().replaceFirst(prefix, ""));
            dbTable.setLowerCaseTrimmedName(dbTable.getName().replaceFirst(prefix, "").toLowerCase());
            dbTable.setTokenizedLowerCaseTrimmedName(Tokenization.lowercaseSplit(dbTable.getName().replaceFirst(prefix, "")));
        }
    }

    private static String getTableCommonPrefix(List<DbTable> dbTables) {
        String commonPrefix = "";
        int maxLength = Integer.MAX_VALUE;
        for (DbTable dbTable : dbTables) {
            maxLength = Math.min(maxLength, dbTable.getName().length());
        }

        if (dbTables.size()<2) return "";

        for (int prefixLength = 1; prefixLength < maxLength ; prefixLength++) {
            commonPrefix = dbTables.get(0).getName().substring(0, prefixLength);
            for (DbTable dbTable : dbTables) {
                if (!commonPrefix.equals(dbTable.getName().substring(0, prefixLength))) {
                    return commonPrefix.substring(0, prefixLength-1);
                }
            }
        }

        return commonPrefix;
    }

    // Remove column prefixes if and only if all columns in all dbTables in the schema are prefixed
    private static void setColumnTrimmedName(List<DbTable> dbTables) {
        int prefixLengthMin = Integer.MAX_VALUE;

        for (DbTable dbTable : dbTables) {
            String prefix = getColumnCommonPrefix(dbTable.getColumnList());
            prefixLengthMin = Math.min(prefixLengthMin, prefix.length());
        }

        for (DbTable dbTable : dbTables) {
            String prefix = "";
            if (prefixLengthMin>0) prefix = getColumnCommonPrefix(dbTable.getColumnList());

            for (Column column : dbTable.getColumnList()) {
                // Set trimmed name
                column.setTrimmedName(column.getName().replaceFirst(prefix, ""));

                // Set tokenizedLowerCaseTrimmedName
                column.getTokenizedLowerCaseTrimmedName().clear();
                for (String token : Tokenization.split(column.getTrimmedName())) {
                    column.getTokenizedLowerCaseTrimmedName().add(token.toLowerCase());
                }

                // Set lowerCaseTrimmedName
                column.setLowerCaseTrimmedName(column.getTrimmedName().toLowerCase());
            }
        }
    }

    private static String getColumnCommonPrefix(List<Column> columns) {
        String commonPrefix = "";
        int maxLength = Integer.MAX_VALUE;
        for (Column column : columns) {
            maxLength = Math.min(maxLength, column.getName().length());
        }

        if (columns.size()<2) return "";

        for (int prefixLength = 1; prefixLength < maxLength ; prefixLength++) {
            commonPrefix = columns.get(0).getName().substring(0, prefixLength);
            for (Column column : columns) {
                if (!commonPrefix.equals(column.getName().substring(0, prefixLength))) {
                    return commonPrefix.substring(0, prefixLength-1);
                }
            }
        }

        return commonPrefix;
    }

    public void getTableName(DbOracleStructure dbOracleStructure) {
        for (DbTable dbTable : dbOracleStructure.getTables()) {
            System.out.println(dbTable.getName());
        }
    }

   /* private MPDROracleColumn getColumns(String tableName) throws SQLException {
        ResultSet columns = databaseMetaData.getColumns(null,null, tableName, null);
        while(columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            String columnSize = columns.getString("COLUMN_SIZE");
            String datatype = columns.getString("DATA_TYPE");
            String isNullable = columns.getString("IS_NULLABLE");

            tableName.add(columnName);
            tableName.add(columnSize);
            tableName.add(datatype);
            tableName.add(isNullable);
        }
    }

    private MPDROraclePK getPK(String tableName) throws SQLException{
        ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(null, null, tableName);
        while(primaryKeys.next()){
            String primaryKeyColumnName = primaryKeys.getString("COLUMN_NAME");
            String primaryKeyName = primaryKeys.getString("PK_NAME");
        }
    }

    private MPDROracleFK getFK (String tableName) throws SQLException{
        ResultSet foreignKeys = databaseMetaData.getImportedKeys(null, null, tableName);
        while(foreignKeys.next()){
            String pkTableName = foreignKeys.getString("PKTABLE_NAME");
            String fkTableName = foreignKeys.getString("FKTABLE_NAME");
            String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
            String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
        }
    }*/

    public DatabaseMetaData getDatabaseMetaData() {
        return databaseMetaData;
    }

    public void setDatabaseMetaData(DatabaseMetaData databaseMetaData) {
        this.databaseMetaData = databaseMetaData;
    }

    public String getSchemaDB() {
        return schemaDB;
    }

    public void setSchemaDB(String schemaDB) {
        this.schemaDB = schemaDB;
    }

    public Connection getConnection() {
        return connection;
    }

    public ConConnection getConConnection() {
        return conConnection;
    }
}

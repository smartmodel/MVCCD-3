package consolidationMpdrDb.ZelementASupprimer;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbTable {

    private final String schemaName;                        // Schema name
    private final String name;                              // Table name
    private String lowerCaseTrimmedName;                    // For string distances
    private List<String> tokenizedLowerCaseTrimmedName;     // For tpc similarity
    private String trimmedName;                             // Table name without the shared prefix in the schema
    private List<Column> columnList = new ArrayList<>();    // All columns in the table


    public DbTable(String schemaName, String name) {
        this.schemaName = schemaName;
        this.name = name;
    }

    public Column getColumn(String columnName) {
        for (Column column : columnList) {
            if (column.getName().equals(columnName)) return column;
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public List<Column> getPk() {
        List<Column> result = new ArrayList<>();
        for (Column column : columnList) {
            if (column.isPrimaryKey()) result.add(column);
        }
        return result;
    }

    public String getTrimmedName() {
        return trimmedName;
    }

    public void setTrimmedName(String trimmedName) {
        this.trimmedName = trimmedName;
    }

    public void setLowerCaseTrimmedName(String lowerCaseTrimmedName) {
        this.lowerCaseTrimmedName = lowerCaseTrimmedName;
    }

    public String getLowerCaseTrimmedName() {
        return lowerCaseTrimmedName;
    }

    public void setTokenizedLowerCaseTrimmedName(List<String> tokenizedLowerCaseTrimmedName) {
        this.tokenizedLowerCaseTrimmedName = tokenizedLowerCaseTrimmedName;
    }

    public List<String> getTokenizedLowerCaseTrimmedName() {
        return tokenizedLowerCaseTrimmedName;
    }

    public void getColumns(DatabaseMetaData metaData, String databaseName, String schemaName, String tableName) throws SQLException {
        List<Column> columns = new ArrayList<>();

        try (ResultSet result = metaData.getColumns(databaseName, schemaName, tableName, null)) {
            while (result.next()) {
                //Column column = new Column(tableName, result.getString("COLUMN_NAME"), this); //avec ajout de la table pour chaque colonne mais inutile je pense
                Column column = new Column(tableName, result.getString("COLUMN_NAME"));
                column.setDataType(result.getInt("DATA_TYPE"));
                column.setDataTypeName(result.getString("TYPE_NAME"));
                //column.setDataTypeName(JDBCType.valueOf(column.getDataType()).toString()); //pour obtenir NUMERIC, CHAR,...
                column.setNullable(result.getBoolean("NULLABLE"));
                column.setColumnSize(result.getInt("COLUMN_SIZE"));
                column.setDecimalDigits(result.getInt("DECIMAL_DIGITS"));
                column.setHasDefault(result.getString("COLUMN_DEF") != null);
                //Position de la colonne dans la table
                column.setOrdinalPosition(result.getInt("ORDINAL_POSITION"));
                column.setAutoincrement("YES".equals(result.getString("IS_AUTOINCREMENT")));
                columns.add(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        columnList = setColumnCounts(columns);
    }

    private List<Column> setColumnCounts(List<Column> columnList) {
        for (Column column : columnList) {
            column.setTableColumnCount(columnList.size());
            column.setOrdinalPositionEnd(columnList.size() - column.getOrdinalPosition() + 1);
        }
        return columnList;
    }

    public void getPrimaryKeys(DatabaseMetaData metaData, String databaseName, String schemaName, String tableName) throws SQLException {
        try (ResultSet result = metaData.getPrimaryKeys(databaseName, schemaName, tableName)) {
            while (result.next()) {
                for (Column column : columnList) {
                    if (column.getName().equals(result.getString(4))) {
                        column.setPrimaryKey(true);
                    }
                }
            }
        }
    }

    public void getUniqueConstraint(DatabaseMetaData metaData, String databaseName, String schemaName, String tableName) throws SQLException {
        try (ResultSet result = metaData.getIndexInfo(databaseName, schemaName, tableName, true, true)) {
            while (result.next()) {
                for (Column col : columnList) {
                    if (col.getName().equals(result.getString(9))) {
                        col.setUniqueConstraint(true);
                    }
                }
            }
        }
    }
}
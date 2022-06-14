package consolidationMpdrDb.ZelementASupprimer;

import consolidationMpdrDb.ZelementASupprimer.Utils.Tokenization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Column {
    private final String name;              // Column name
    private final String tableName;
    private final String longName;          // Format: table.column
    private final List<String> tokenizedName;
    private String lowerCaseTrimmedName;    // For string distance
    private List<String> tokenizedLowerCaseTrimmedName = new ArrayList<>();
    //private Table table;                    // The father table
    private int dataType;                   // Data type as defined by JDBC (simplified if appropriate)
    private String dataTypeName;            // Data type as defined by JDBC
    private Boolean isUnique;		// Expensive SQL query -> is not automatically calculated
    private Boolean isNotNull;	// Expensive SQL query -> is not automatically calculated
    private boolean isUniqueConstraint = false; // From getIndexInfo() - if there is an index, it is flipped from false
    private boolean isNullable;             // From getColumns() JDBC call -> ought to be filled in
    private boolean isJunctionTable;
    private double isJunctionTable2;
    private Boolean hasMultiplePK;
    private int columnSize;
    private int decimalDigits;				// Decided based on the data type definition -> defined only for numeric/decimal data types
    private boolean hasDefault;
    private int ordinalPosition;
    private int ordinalPositionEnd;
    private int tableColumnCount;
    private boolean isAutoincrement;
    private String trimmedName;             // Column name without the shared prefix in the table
    private Boolean isEmptyTable;	// Availability depends on the availability of estimatedRowCount
    private Boolean isPrimaryKey = false;   // The label


    public Column(String tableName, String name) {
        this.name = name;
        this.tableName = tableName;
        longName = tableName + "." + name;
        tokenizedName = Tokenization.split(name);
    }
/*
    public Column(String tableName, String name, Table table) {
        this(tableName, name);
        this.table = table;
    }
*/
    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public void setNullable(boolean isNullable) {
        this.isNullable = isNullable;
    }

    public void setUniqueConstraint(boolean uniqueConstraint) {
        isUniqueConstraint = uniqueConstraint;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public void setHasDefault(boolean hasDefault) {
        this.hasDefault = hasDefault;
    }

    public boolean getHasDefault (){
        return hasDefault;
    }

    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public void setOrdinalPositionEnd(int ordinalPositionEnd) {
        this.ordinalPositionEnd = ordinalPositionEnd;
    }

    public void setTableColumnCount(int tableColumnCount) {
        this.tableColumnCount = tableColumnCount;
    }

    public void setAutoincrement(boolean autoincrement) {
        isAutoincrement = autoincrement;
    }

    public String getName() {
        return name;
    }

    public String getLongName() {
        return longName;
    }

    public String getTrimmedName() {
        return trimmedName;
    }

    public void setTrimmedName(String trimmedName) {
        this.trimmedName = trimmedName;
    }

    public List<String> getTokenizedName() {
        return Collections.unmodifiableList(tokenizedName);
    }

    public List<String> getTokenizedLowerCaseTrimmedName() {
        return tokenizedLowerCaseTrimmedName;
    }

    public void setTokenizedLowerCaseTrimmedName(List<String> tokenizedLowerCaseTrimmedName) {
        this.tokenizedLowerCaseTrimmedName = tokenizedLowerCaseTrimmedName;
    }

    public String getLowerCaseTrimmedName() {
        return lowerCaseTrimmedName;
    }

    public void setLowerCaseTrimmedName(String lowerCaseTrimmedName) {
        this.lowerCaseTrimmedName = lowerCaseTrimmedName;
    }

    public String getTable() {
        return this.tableName;
    }

    public Boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }


    public void setJunctionTable(boolean junctionTable) {
        isJunctionTable = junctionTable;
    }

    public void setIsJunctionTable2(double isJunctionTable2) {
        this.isJunctionTable2 = isJunctionTable2;
    }

    public void setHasMultiplePK(Boolean hasMultiplePK) {
        this.hasMultiplePK = hasMultiplePK;
    }

    public Boolean hasMultiplePK() {
        return hasMultiplePK;
    }

    public String toString() {
        return longName;
    }

    @Override public int hashCode() {
        return longName.hashCode();
    }

}

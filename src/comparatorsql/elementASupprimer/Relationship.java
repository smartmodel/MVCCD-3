package comparatorsql.elementASupprimer;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Relationship {
    private final static Logger LOGGER = Logger.getLogger(Relationship.class.getName());
    private static final List<String> KEYWORDS_FK = Arrays.asList("fk", "type", "eid");
    private static final List<String> STAT_LESS_DATA_TYPE = Arrays.asList("BINARY", "LONGVARBINARY", "LONGVARCHAR");

    private boolean dataTypeAgree;
    private boolean dataTypeCategoryAgree;
    private boolean dataLengthAgree;
    private boolean decimalAgree;
    private boolean isTheSameColumn;
    private boolean isTheSameTable;
    private boolean firstCharAgree;
    private int levenshteinColumns;
    private int levenshteinToTable;
    private int levenshteinFromTable;
    private boolean containsFKName;
    private Column fk;                   // We model composite fk constraints by defining the individual relationships
    private Column pk;
    private DbTable fkDbTable;
    private DbTable pkDbTable;
    private String fkTableName;
    private String pkTableName;
    private String fkLowerCaseTrimmedTable;
    private String pkLowerCaseTrimmedTable;
    private List<String> pkTableTokenizedLowerCaseTrimmedName; // Precomputed values for Tpc similarity
    private List<String> fkTableTokenizedLowerCaseTrimmedName; // Precomputed values for Tpc similarity
    private String schema;
    private Boolean isForeignKey = false;           // The label

    public Relationship() {
    }

    public Relationship(Relationship other) {
        dataTypeAgree = other.dataTypeAgree;
        dataTypeCategoryAgree = other.dataTypeCategoryAgree;
        dataLengthAgree = other.dataLengthAgree;
        decimalAgree = other.decimalAgree;
        isTheSameColumn = other.isTheSameColumn;
        isTheSameTable = other.isTheSameTable;
        firstCharAgree = other.firstCharAgree;
        levenshteinColumns = other.levenshteinColumns;
        levenshteinToTable = other.levenshteinToTable;
        levenshteinFromTable = other.levenshteinFromTable;
        containsFKName = other.containsFKName;
        fk = other.fk;
        pk = other.pk;
        fkDbTable = other.fkDbTable;
        pkDbTable = other.pkDbTable;
        fkTableName = other.fkTableName;
        pkTableName = other.pkTableName;
        fkLowerCaseTrimmedTable = other.fkLowerCaseTrimmedTable;
        pkLowerCaseTrimmedTable = other.pkLowerCaseTrimmedTable;
        fkTableTokenizedLowerCaseTrimmedName = other.fkTableTokenizedLowerCaseTrimmedName;
        pkTableTokenizedLowerCaseTrimmedName = other.pkTableTokenizedLowerCaseTrimmedName;
        schema = other.schema;
        isForeignKey = other.isForeignKey;
    }
    public Column getFk() {
        return fk;
    }

    public void setFk(Column fk) {
        this.fk = fk;
    }

    public Column getPk() {
        return pk;
    }

    public void setPk(Column pk) {
        this.pk = pk;
    }

    public String getFkTableName() {
        return fkTableName;
    }

    public void setFkTable(DbTable fkDbTable) {
        this.fkDbTable = fkDbTable;
        fkTableName = fkDbTable.getName();
        fkLowerCaseTrimmedTable = fkDbTable.getLowerCaseTrimmedName();
        fkTableTokenizedLowerCaseTrimmedName = fkDbTable.getTokenizedLowerCaseTrimmedName();
    }

    public String getPkTableName() {
        return pkTableName;
    }

    public void setPkTable(DbTable pkDbTable) {
        this.pkDbTable = pkDbTable;
        pkTableName = pkDbTable.getName();
        pkLowerCaseTrimmedTable = pkDbTable.getLowerCaseTrimmedName();
        pkTableTokenizedLowerCaseTrimmedName = pkDbTable.getTokenizedLowerCaseTrimmedName();
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Boolean isTheSameColumn() {
        return isTheSameColumn;
    }

    public Boolean isForeignKey() {
        return isForeignKey;
    }

    public void setForeignKey(Boolean foreignKey) {
        isForeignKey = foreignKey;
    }

    public String toString() {
        return fk + " --> " + pk;
    }

}

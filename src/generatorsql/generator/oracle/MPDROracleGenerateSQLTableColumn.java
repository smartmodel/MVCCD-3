package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLTableColumn;
import mpdr.MPDRColumn;
import mpdr.MPDRDBPK;
import preferences.PreferencesManager;

public class MPDROracleGenerateSQLTableColumn extends MPDRGenerateSQLTableColumn {


    MPDROracleGenerateSQL mpdrOracleGenerateSQL;

    public MPDROracleGenerateSQLTableColumn(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateTableColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = super.generateSQLCreateTableColumn(mpdrColumn);
        return generateSQLCode;
    }

    public String generateSQLDropColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = super.generateSQLDropColumn(mpdrColumn);
        return generateSQLCode;
    }

    public String generateSQLAddColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = super.generateSQLAddColumn(mpdrColumn);
        return generateSQLCode;
    }

    public String generateSQLModifyColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = super.generateSQLModifyColumn(mpdrColumn);
        return generateSQLCode;
    }

    public String generateSQLModifyColumnAddNotNul(MPDRColumn mpdrColumn){
        String generateSQLCode = super.generateSQLModifyColumnAddNotNul(mpdrColumn);
        return generateSQLCode;
    }

    public String generateSQLModifyColumnDropNotNul(MPDRColumn mpdrColumn){
        String generateSQLCode = super.generateSQLModifyColumnDropNotNul(mpdrColumn);
        return generateSQLCode;
    }

    public String generateSQLModifyColumnAddOrModifyDefault(MPDRColumn mpdrColumn){
        String generateSQLCode = super.generateSQLModifyColumnAddOrModifyDefault(mpdrColumn);
        return generateSQLCode;
    }

    public String generateSQLModifyColumnDropDefault(MPDRColumn mpdrColumn){
        String generateSQLCode = super.generateSQLModifyColumnDropDefault(mpdrColumn);
        return generateSQLCode;
    }

    @Override
    protected boolean pkGenerateIdentity() {
        return PreferencesManager.instance().preferences().getMPDRORACLE_PK_GENERATE() == MPDRDBPK.IDENTITY;
    }


    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

    @Override
    protected String generateDatatypeSizeScale(MPDRColumn mpdrColumn) {
        String sizeScale = "";
        if (mpdrColumn.getSize() != null) {
            if (mpdrColumn.getScale() != null) {
                sizeScale = "(" + mpdrColumn.getSize().toString() + ", " + mpdrColumn.getScale().toString() + ")";
            } else {
                sizeScale = "(" + mpdrColumn.getSize().toString() + ")";
            }
        }
        return sizeScale;
    }

}

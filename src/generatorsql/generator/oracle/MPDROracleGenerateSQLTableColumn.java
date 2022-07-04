package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLTableColumn;
import mpdr.MPDRColumn;
import mpdr.MPDRDBPK;
import mpdr.interfaces.IMPDRColumn;
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

    public String generateSQLConsolidationDropColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = super.generateSQLConsolidationDropColumn(mpdrColumn);
        return generateSQLCode;
    }

    public String generateSQLConsolidationAddColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = super.generateSQLConsolidationAddColumn(mpdrColumn);
        return generateSQLCode;
    }

    public String generateSQLConsolidationModifyColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = super.generateSQLConsolidationModifyColumn(mpdrColumn);
        return generateSQLCode;
    }

    public String generateSQLConsolidationModifyColumnAddNotNul(MPDRColumn mpdrColumn){
        String generateSQLCode = super.generateSQLConsolidationModifyColumnAddNotNul(mpdrColumn);
        return generateSQLCode;
    }

    public String generateSQLConsolidationModifyColumnDropNotNul(MPDRColumn mpdrColumn){
        String generateSQLCode = super.generateSQLConsolidationModifyColumnDropNotNul(mpdrColumn);
        return generateSQLCode;
    }

    public String generateSQLConsolidationModifyColumnAddOrModifyDefault(MPDRColumn mpdrColumn){
        String generateSQLCode = super.generateSQLConsolidationModifyColumnAddOrModifyDefault(mpdrColumn);
        return generateSQLCode;
    }

    public String generateSQLConsolidationModifyColumnDropDefault(MPDRColumn mpdrColumn){
        String generateSQLCode = super.generateSQLConsolidationModifyColumnDropDefault(mpdrColumn);
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
    protected String generateDatatypeSizeScale(IMPDRColumn impdrColumn) {
        String sizeScale = "";
        if (impdrColumn.getSize() != null) {
            if (impdrColumn.getScale() != null) {
                sizeScale = "(" + impdrColumn.getSize().toString() + ", " + impdrColumn.getScale().toString() + ")";
            } else {
                sizeScale = "(" + impdrColumn.getSize().toString() + ")";
            }
        }
        return sizeScale;
    }

}

package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLTableColumn;
import mpdr.MPDRColumn;
import mpdr.MPDRDBPK;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;

public class MPDRPostgreSQLGenerateSQLTableColumn extends MPDRGenerateSQLTableColumn {


    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL;
    public MPDRPostgreSQLGenerateSQLTableColumn(MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL) {
        this.mpdrPostgreSQLGenerateSQL = mpdrPostgreSQLGenerateSQL;
    }

    public String generateSQLCreateTableColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = super.generateSQLCreateTableColumn(mpdrColumn);

        return generateSQLCode;
    }

    @Override
    protected boolean pkGenerateIdentity() {
        return  PreferencesManager.instance().preferences().getMPDRPOSTGRESQL_PK_GENERATE() == MPDRDBPK.IDENTITY;
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }

    @Override
    protected String generateDatatypeSizeScale(MPDRColumn mpdrColumn) {
        String sizeScale = "";
        if (mpdrColumn.getSize() != null) {
            if (mpdrColumn.getScale() != null) {
                sizeScale = "(" + mpdrColumn.getSize().toString() + ", " + mpdrColumn.getScale().toString() + ")";
            } else {
                // Suppression de la taille pour les colonnes de la fraterie entière
                ArrayList<String> lienProgsSibling = new ArrayList<String>();
                lienProgsSibling.add(Preferences.MPDRPOSTGRESQLDATATYPE_SMALLINT_LIENPROG);
                lienProgsSibling.add(Preferences.MPDRPOSTGRESQLDATATYPE_INTEGER_LIENPROG);
                lienProgsSibling.add(Preferences.MPDRPOSTGRESQLDATATYPE_BIGINT_LIENPROG);
                if (! lienProgsSibling.contains(mpdrColumn.getDatatypeLienProg())) {
                    sizeScale = "(" + mpdrColumn.getSize().toString() + ")";
                }
            }
        }
        return sizeScale;

    }

}

package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLColumn;
import mpdr.MPDRColumn;
import mpdr.MPDRDBPK;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;

public class MPDRPostgreSQLGenerateSQLColumn extends MPDRGenerateSQLColumn {


    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL;
    public MPDRPostgreSQLGenerateSQLColumn(MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL) {
        this.mpdrPostgreSQLGenerateSQL = mpdrPostgreSQLGenerateSQL;
    }

    public String generateSQLCreateColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = super.generateSQLCreateColumn(mpdrColumn);

        return generateSQLCode;
    }

    @Override
    protected boolean pkGenerateIdentity() {
        return  PreferencesManager.instance().preferences().getMPDRORACLE_PK_GENERATE() == MPDRDBPK.IDENTITY;
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }

    // Surcharge de la méthode générique
    protected String generateSizeScale(String generateSQLCode, MPDRColumn mpdrColumn){
        if (mpdrColumn.getSize() != null) {
            if (mpdrColumn.getScale() != null) {
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SIZE_WORD, "(" + mpdrColumn.getSize().toString());
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SCALE_WORD, ", " + mpdrColumn.getScale().toString() + ")");
            } else {
                // Suppression de la taille pour les colonnes de la fraterie entière
                ArrayList<String> lienProgsSibling = new ArrayList<String>();
                lienProgsSibling.add(Preferences.MPDRPOSTGRESQLDATATYPE_SMALLINT_LIENPROG);
                lienProgsSibling.add(Preferences.MPDRPOSTGRESQLDATATYPE_INTEGER_LIENPROG);
                lienProgsSibling.add(Preferences.MPDRPOSTGRESQLDATATYPE_BIGINT_LIENPROG);
                if (lienProgsSibling.contains(mpdrColumn.getDatatypeLienProg())){
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SIZE_WORD, "");
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SCALE_WORD, "");
                } else {
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SIZE_WORD, "(" + mpdrColumn.getSize().toString() + ")");
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SCALE_WORD, "");
                }
            }
        } else {
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SIZE_WORD, "");
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SCALE_WORD, "");
        }
        return generateSQLCode;
    }


}

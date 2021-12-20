package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRFK;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import preferences.Preferences;
import utilities.TemplateFile;

import java.util.ArrayList;

public class MPDRGenerateSQLFK {

    private MPDRModel  mpdrModel;
    private MPDRTable mpdrTable;

    public MPDRGenerateSQLFK(MPDRTable mpdrTable) {
        this.mpdrTable = mpdrTable;
        mpdrModel = mpdrTable.getMPDRModelParent();
    }

    public String generateSQLAlterFKs() {
        String generateSQLCode = "";
        ArrayList<MPDRTable> mpdrTables = mpdrModel.getMPDRTables();

        for (MPDRTable mpdrTable : mpdrTables) {
            ArrayList<MPDRFK> mpdrFks = mpdrTable.getMPDRFKs();
            for (MPDRFK mpdrFk : mpdrFks) {
                generateSQLCode += generateSQLFK(mpdrFk);
            }
        }

        return generateSQLCode;
    }

    private String generateSQLFK(MPDRFK mpdrfk) {
        String generateSQLCode = "";

        generateSQLCode += TemplateFile.templateFileToString(mpdrModel.getTemplateDirAlter(), Preferences.TEMPLATES_ALTER_TABLE_FK) + System.lineSeparator();
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "table_name", mpdrfk.getParent().getParent().getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_name", mpdrfk.getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_column", mpdrfk.getMDRColumnsNameAsParamStr());

        //Contraite PK de référence
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_reference_table", mpdrfk.getMdrPK().getMDRTableAccueil().getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_reference_column", mpdrfk.getMDRColumnsRefPKNameAsParamStr());

        return generateSQLCode;
    }


}

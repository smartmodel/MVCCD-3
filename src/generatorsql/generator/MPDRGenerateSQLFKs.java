package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRFK;
import mpdr.MPDRModel;
import mpdr.MPDRPK;
import mpdr.MPDRTable;
import preferences.Preferences;
import utilities.TemplateFile;

import java.util.ArrayList;

public class MPDRGenerateSQLFKs {

    private MPDRModel mpdrModel;

    public MPDRGenerateSQLFKs(MPDRModel mpdrModel) {
        this.mpdrModel = mpdrModel;
    }


    public String generateSQLFKs() {
        String generateSQLCode = "";
        ArrayList<MPDRTable> mpdrTables = mpdrModel.getMPDRTables();

        for (MPDRTable mpdrTable : mpdrTables) {
            ArrayList<MPDRFK> mpdrFks = mpdrTable.getMPDRFKs();
            boolean firstFK = true;
            for (MPDRFK mpdrFk : mpdrFks) {
                if (!firstFK) {
                    generateSQLCode += Preferences.SYSTEM_LINE_SEPARATOR;
                }
                generateSQLCode  += generateSQLFK(mpdrFk);
                firstFK = false;
            }
        }

        return generateSQLCode;
    }

    private String generateSQLFK(MPDRFK mpdrfk) {
        String generateSQLCode = "";
        generateSQLCode += TemplateFile.templateFileToString(mpdrModel.getTemplateDirAlter(), Preferences.TEMPLATES_ALTER_TABLE_FK) ;
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "fk_table_name", mpdrfk.getParent().getParent().getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "fk_name", mpdrfk.getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "fk_columns", mpdrfk.getParametersNameAsStr());

        //Contraite PK de référence
        MPDRPK reference = (MPDRPK) mpdrfk.getMdrPK();
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "pk_reference_table", reference.getMDRTableAccueil().getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "pk_reference_columns", reference.getParametersNameAsStr());

        return generateSQLCode;
    }


}

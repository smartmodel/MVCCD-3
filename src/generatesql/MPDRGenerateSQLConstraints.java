package generatesql;

import mpdr.MPDRFK;
import mpdr.MPDRModel;
import mpdr.MPDRPK;
import mpdr.MPDRTable;
import utilities.TemplateFile;

import java.util.ArrayList;

public class MPDRGenerateSQLConstraints {

    private MPDRGenerateSQL mpdrGenerateSQL;
    private MPDRTable mpdrTable;
    private MPDRModel mpdrModel;
    private String generateSQLCode = "";

    public MPDRGenerateSQLConstraints(MPDRGenerateSQL mpdrGenerateSQL, MPDRTable mpdrTable) {
        this.mpdrGenerateSQL = mpdrGenerateSQL;
        this.mpdrTable = mpdrTable;
    }

    public MPDRGenerateSQLConstraints(MPDRGenerateSQL mpdrGenerateSQL, MPDRModel mpdrModel) {
        this.mpdrGenerateSQL = mpdrGenerateSQL;
        this.mpdrModel = mpdrModel;
    }

    public String generateSQLConstraints() {
        generateSQLCode += "\t" + TemplateFile.templateFileToString("template/oracle/constraints", "PK.txt") + "," + System.lineSeparator();
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_name", mpdrTable.getMPDRPK().getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_column", mpdrTable.getMPDRPK().getParametersNameAsStr());

        return generateSQLCode;
    }

    public String generateSQLFKs() {
        ArrayList<MPDRTable> mpdrTables = mpdrModel.getMPDRTables();
        for(MPDRTable mpdrTable : mpdrTables) {
            ArrayList<MPDRFK> mpdrFks = mpdrTable.getMPDRFKs();
            for(MPDRFK mpdrFk : mpdrFks) {
                generateSQLFK(mpdrFk);
            }
        }

        return generateSQLCode;
    }

    private void generateSQLFK(MPDRFK mpdrfk) {
        generateSQLCode += TemplateFile.templateFileToString("template/oracle/constraints", "FK.txt") + System.lineSeparator();
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "table_name", mpdrfk.getParent().getParent().getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_name", mpdrfk.getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_column", mpdrfk.getParametersNameAsStr());

        //Contraite PK de référence
        MPDRPK reference =  (MPDRPK) mpdrfk.getMdrPK();
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_reference_table", reference.getMDRTableAccueil().getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_reference_column", reference.getParametersNameAsStr());
    }
}

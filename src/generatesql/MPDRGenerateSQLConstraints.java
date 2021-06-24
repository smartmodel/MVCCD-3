package generatesql;

import mpdr.MPDRFK;
import mpdr.MPDRPK;
import mpdr.MPDRTable;
import utilities.TemplateFile;

public class MPDRGenerateSQLConstraints {

    private MPDRGenerateSQL mpdrGenerateSQL;
    private MPDRTable mpdrTable;
    private String generateSQLCode = "";

    public MPDRGenerateSQLConstraints(MPDRGenerateSQL mpdrGenerateSQL, MPDRTable mpdrTable) {
        this.mpdrGenerateSQL = mpdrGenerateSQL;
        this.mpdrTable = mpdrTable;
    }

    public String generateSQLConstraints() {
        generateSQLCode += "\t" + TemplateFile.templateFileToString("template/oracle/constraints", "PK.txt") + "," + System.lineSeparator();
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_name", mpdrTable.getMPDRPK().getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_column", mpdrTable.getMPDRPK().getParametersNameAsStr());

        for (MPDRFK mpdrfk : mpdrTable.getMPDRFKs()) {
            generateSQLFK(mpdrfk);
        }

        return generateSQLCode;
    }

    private void generateSQLFK(MPDRFK mpdrfk) {
        generateSQLCode += "\t" + TemplateFile.templateFileToString("template/oracle/constraints", "FK.txt") + "," + System.lineSeparator();
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_name", mpdrfk.getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_column", mpdrfk.getParametersNameAsStr());

        //Contraite PK de référence
        MPDRPK reference =  (MPDRPK) mpdrfk.getMdrPK();
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_reference_table", reference.getMDRTableAccueil().getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "constraint_reference_column", reference.getParametersNameAsStr());
    }
}

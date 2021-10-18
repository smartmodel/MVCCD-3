package generatesql;

import mpdr.MPDRModel;
import mpdr.MPDRTable;
import utilities.TemplateFile;

public class MPDRGenerateSQLTables {

    private MPDRGenerateSQL mpdrGenerateSQL;
    private MPDRModel mpdrModel;
    private String generateSQLCode = "";

    public MPDRGenerateSQLTables(MPDRGenerateSQL mpdrGenerateSQL, MPDRModel mpdrModel) {
        this.mpdrGenerateSQL = mpdrGenerateSQL;
        this.mpdrModel = mpdrModel;
    }

    public String generateSQLTables() {
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            generateSQLTable(mpdrTable);
        }

        return generateSQLCode;
    }

    private String generateSQLTable(MPDRTable mpdrTable) {
        //Génération des tables
        generateSQLCode += TemplateFile.templateFileToString("template/oracle", "table.txt") + System.lineSeparator();
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "table_name", mpdrTable.getName());

        //Génération des colonnes
        MPDRGenerateSQLColumns mpdrGenerateSQLColumns = new MPDRGenerateSQLColumns(mpdrGenerateSQL, mpdrTable);
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "table_columns", mpdrGenerateSQLColumns.generateSQLColumns());

        //Génération des contraintes
        MPDRGenerateSQLConstraints mpdrGenerateSQLConstraints = new MPDRGenerateSQLConstraints(mpdrGenerateSQL, mpdrTable);
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "table_constraints", mpdrGenerateSQLConstraints.generateSQLConstraints());

        return generateSQLCode;
    }
}

package generatesql;

import mpdr.MPDRModel;
import mpdr.MPDRTable;
import utilities.TemplateFile;

public class TBGenerateSQLTables {

    private TBGenerateSQL TBGenerateSQL;
    private MPDRModel mpdrModel;
    private String generateSQLCode = "";

    public TBGenerateSQLTables(TBGenerateSQL TBGenerateSQL, MPDRModel mpdrModel) {
        this.TBGenerateSQL = TBGenerateSQL;
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
        generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "table_name", mpdrTable.getName());

        //Génération des colonnes
        TBGenerateSQLColumns TBGenerateSQLColumns = new TBGenerateSQLColumns(TBGenerateSQL, mpdrTable);
        generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "table_columns", TBGenerateSQLColumns.generateSQLColumns());

        //Génération des contraintes
        TBGenerateSQLConstraints TBGenerateSQLConstraints = new TBGenerateSQLConstraints(TBGenerateSQL, mpdrTable);
        generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "table_constraints", TBGenerateSQLConstraints.generateSQLConstraints());

        return generateSQLCode;
    }
}

package generatesql;

import mpdr.MPDRColumn;
import mpdr.MPDRTable;
import utilities.TemplateFile;

public class TBGenerateSQLColumns {

    private TBGenerateSQL TBGenerateSQL;
    private MPDRTable mpdrTable;
    private String generateSQLCode = "";

    public TBGenerateSQLColumns(TBGenerateSQL TBGenerateSQL, MPDRTable mpdrTable) {
        this.TBGenerateSQL = TBGenerateSQL;
        this.mpdrTable = mpdrTable;
    }

    public String generateSQLColumns() {
        for (MPDRColumn mpdrColumn : mpdrTable.getMPDRColumns()) {
            generateSQLColumn(mpdrColumn);
        }

        return generateSQLCode;
    }

    private void generateSQLColumn(MPDRColumn mpdrColumn) {
        generateSQLCode += "\t" + TemplateFile.templateFileToString("template/oracle", "column.txt") + "," + System.lineSeparator();
        generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_name", mpdrColumn.getName());
        generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_type", mpdrColumn.getDatatypeLienProg());

        if (mpdrColumn.getSize() != null) {
            if (mpdrColumn.getScale() != null) {
                generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_size", "(" + mpdrColumn.getSize().toString());
                generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_scale", ", " + mpdrColumn.getScale().toString() + ")");
            } else {
                generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_size", "(" + mpdrColumn.getSize().toString() + ")");
                generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_scale", "");
            }
        } else {
            generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_size", "");
            generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_scale", "");
        }

        if (mpdrColumn.isMandatory()) {
            generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_mandatory", " NOT NULL");
        } else {
            generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_mandatory", "");
        }

        if (mpdrColumn.getInitValue() != "" && mpdrColumn.getInitValue() != null) {
            if (mpdrColumn.getDatatypeLienProg() == "NUMBER") {
                generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_default", " DEFAULT " + mpdrColumn.getInitValue());
            } else {
                generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_default", " DEFAULT '" + mpdrColumn.getInitValue() + "'");
            }
        } else {
            generateSQLCode = TBGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_default", "");
        }
    }
}

package generatesql;

import mpdr.MPDRColumn;
import mpdr.MPDRTable;
import utilities.TemplateFile;

public class MPDRGenerateSQLColumns {

    private MPDRGenerateSQL mpdrGenerateSQL;
    private MPDRTable mpdrTable;
    private String generateSQLCode = "";

    public MPDRGenerateSQLColumns(MPDRGenerateSQL mpdrGenerateSQL, MPDRTable mpdrTable) {
        this.mpdrGenerateSQL = mpdrGenerateSQL;
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
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_name", mpdrColumn.getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_type", mpdrColumn.getDatatypeLienProg());

        if(mpdrColumn.getSize() != null) {
            if(mpdrColumn.getScale() != null) {
                generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_size", "(" + mpdrColumn.getSize().toString());
                generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_scale", ", " + mpdrColumn.getScale().toString() + ")");
            } else {
                generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_size", "(" + mpdrColumn.getSize().toString() + ")");
                generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_scale", "");
            }
        } else {
            generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_size", "");
            generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_scale", "");
        }

        if(mpdrColumn.isMandatory()) {
            generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_mandatory", " NOT NULL");
        } else {
            generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_mandatory", "");
        }

        if(mpdrColumn.getInitValue() != "" && mpdrColumn.getInitValue() != null) {
            if(mpdrColumn.getDatatypeLienProg() == "NUMBER") {
                generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_default", " DEFAULT " + mpdrColumn.getInitValue());
            } else {
                generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_default", " DEFAULT '" + mpdrColumn.getInitValue() + "'");
            }
        } else {
            generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_default", "");
        }
    }
}

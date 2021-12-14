package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import resultat.Resultat;
import utilities.TemplateFile;

public class MPDRGenerateSQLColumns {

    private MPDRModel mpdrModel;
    private MPDRTable mpdrTable;
    private Resultat resultat ;




    public MPDRGenerateSQLColumns(MPDRTable mpdrTable, Resultat resultat) {
        this.mpdrTable = mpdrTable;
        this.resultat = resultat;
        mpdrModel = mpdrTable.getMPDRModelParent();
    }

    public String generateSQLCreateColumns() {
        String generateSQLCode = "";

        // Avec nos règles de conformité, une table doit avoir au moins une colonne,

        boolean firstColumn = true;
        for (MPDRColumn mpdrColumn : mpdrTable.getMPDRColumns()) {
            if (! firstColumn){
                generateSQLCode +=  System.lineSeparator() ;
            }
           generateSQLCode += generateSQLCreateColumn(mpdrColumn);
           firstColumn = false;
        }
        return generateSQLCode;

    }

    private String generateSQLCreateColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = "";

        generateSQLCode += "\t" + TemplateFile.templateFileToString(mpdrModel.getTemplateDirCreate(), Preferences.TEMPLATE_CREATE_TABLE_COLUMNS)  +
                Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS ;

        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_name", mpdrColumn.getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_type", mpdrColumn.getDatatypeLienProg());

        if (mpdrColumn.getSize() != null) {
            if (mpdrColumn.getScale() != null) {
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

        if (mpdrColumn.isMandatory()) {
            generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_mandatory", " NOT NULL");
        } else {
            generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_mandatory", "");
        }

        if (StringUtils.isNotEmpty(mpdrColumn.getInitValue())) {
            //TODO-0 Faire le traitement correct de valeur par défaut
            /*
            if (mpdrColumn.getDatatypeLienProg() == "NUMBER") {
                generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_default", " DEFAULT " + mpdrColumn.getInitValue());
            } else {
                generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_default", " DEFAULT '" + mpdrColumn.getInitValue() + "'");
            }

             */
        } else {
            generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "column_default", "");
        }
        return generateSQLCode;
    }
}

package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import preferences.Preferences;
import resultat.Resultat;
import utilities.TemplateFile;

public class MPDRGenerateSQLTables {

    private MPDRModel mpdrModel;
    private Resultat resultat ;

    public MPDRGenerateSQLTables(MPDRModel mpdrModel, Resultat resultat) {
        this.mpdrModel = mpdrModel;
        this.resultat = resultat ;
    }

    public String generateSQLCreateTables() {

        String generateSQLCode = "";
        boolean firstTable = true;
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()) {
            if (! firstTable){
                generateSQLCode += Preferences.SYSTEM_LINE_SEPARATOR;
            }
            generateSQLCode += generateSQLCreateTable(mpdrTable);
            firstTable = false;
        }
        return generateSQLCode;
    }

    private String generateSQLCreateTable(MPDRTable mpdrTable) {
        Resultat resultat = new Resultat();

        //Génération des tables
        String generateSQLCode = "";
        generateSQLCode += TemplateFile.templateFileToString(mpdrModel.getTemplateDirCreate(), Preferences.TEMPLATE_CREATE_TABLE) +
                System.lineSeparator();
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, "table_name", mpdrTable.getName());


        //Génération des colonnes
        MPDRGenerateSQLColumns mpdrGenerateSQLColumns = new MPDRGenerateSQLColumns(mpdrTable, resultat);
        String columnsInCreateTable = mpdrGenerateSQLColumns.generateSQLCreateColumns();
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, Preferences.TEMPLATE_CREATE_TABLE_COLUMNS, columnsInCreateTable);

        //Génération de la contrainte de PK
        //generateSQLCode += Preferences.SQL_SEPARATOR_ARGUMENTS + System.lineSeparator();
        MPDRGenerateSQLPK mpdrGenerateSQLConstraints = new MPDRGenerateSQLPK(mpdrTable, resultat);
        String pkInCreateTable =mpdrGenerateSQLConstraints.generateSQLCreatePK();
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, Preferences.TEMPLATE_CREATE_TABLE_PK, pkInCreateTable);

        generateSQLCode = MPDRGenerateSQLUtil.cleanSeparatorArguments(generateSQLCode);

        return generateSQLCode ;
    }
}

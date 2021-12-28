package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRFK;
import mpdr.MPDRPK;
import preferences.Preferences;
import utilities.TemplateFile;

public abstract class MPDRGenerateSQLFK {

    MPDRGenerateSQL mpdrGenerateSQL ;

    public MPDRGenerateSQLFK(MPDRGenerateSQL mpdrGenerateSQL) {
        this.mpdrGenerateSQL = mpdrGenerateSQL;
    }

    public String generateSQLFK(MPDRFK mpdrfk) {
        String generateSQLCode = "";
        generateSQLCode += TemplateFile.templateFileToString(mpdrGenerateSQL.getTemplateDirAlter(), Preferences.TEMPLATES_ALTER_TABLE_FK) ;
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, Preferences.MDR_TABLE_NAME_CHILD_WORD, mpdrfk.getParent().getParent().getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, Preferences.MDR_FK_NAME_WORD, mpdrfk.getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, Preferences.MDR_FK_COLUMNS_WORD, mpdrfk.getParametersNameAsStr());

        //Contraite PK de référence
        MPDRPK reference = (MPDRPK) mpdrfk.getMdrPK();
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, Preferences.MDR_TABLE_NAME_PARENT_WORD, reference.getMDRTableAccueil().getName());
        generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, Preferences.MDR_FK_COLUMNS_REF_WORD, reference.getParametersNameAsStr());

        return generateSQLCode;
    }


}

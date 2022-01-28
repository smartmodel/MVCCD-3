package generatorsql.generator;

import mpdr.MPDRFK;
import mpdr.MPDRPK;
import preferences.Preferences;
import utilities.TemplateFile;

public abstract class MPDRGenerateSQLFK {


    public MPDRGenerateSQLFK() {
    }

    public String generateSQLFK(MPDRFK mpdrfk) {
        String generateSQLCode = "";
        generateSQLCode += TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirAlterDB(), Preferences.TEMPLATES_ALTER_TABLE_FK) ;
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode,
                Preferences.MDR_TABLE_NAME_CHILD_WORD, mpdrfk.getParent().getParent().getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_FK_NAME_WORD, mpdrfk.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_FK_COLUMNS_WORD, mpdrfk.getParametersNameAsStr());

        //Contraite PK de référence
        MPDRPK reference = (MPDRPK) mpdrfk.getMdrPK();
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode,
                Preferences.MDR_TABLE_NAME_PARENT_WORD, reference.getMDRTableAccueil().getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_FK_COLUMNS_REF_WORD, reference.getParametersNameAsStr());

        return generateSQLCode;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

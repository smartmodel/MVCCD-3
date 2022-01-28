package generatorsql.generator;

import exceptions.CodeApplException;
import generatorsql.MPDRGenerateSQLUtil;
import mdr.MDRColumn;
import mpdr.MPDRDB;
import mpdr.tapis.MPDRDynamicCodeType;
import mpdr.tapis.MPDRTrigger;
import mpdr.tapis.interfaces.IMPDRWithDynamicCode;
import preferences.Preferences;
import utilities.TemplateFile;

import java.util.ArrayList;

public abstract class MPDRGenerateSQLDynamicCode {


    public MPDRGenerateSQLDynamicCode() {
    }


    public String generateSQLCodeDynamic(IMPDRWithDynamicCode impdrWithDynamicCode, String generateSQLCode ){
        MPDRDB mpdrDB = getMPDRGenerateSQL().mpdrModel.getDb();

        for (MPDRDynamicCodeType mpdrDynamicCodeType : MPDRDynamicCodeType.getAllForDB(mpdrDB)){
            if (MPDRGenerateSQLUtil.find(generateSQLCode, mpdrDynamicCodeType.getKey())){
                String sqlCodeDynamic = loadTemplate(impdrWithDynamicCode, mpdrDynamicCodeType);
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, mpdrDynamicCodeType.getKey(), sqlCodeDynamic);
            }
        }

        return generateSQLCode;
    }

    protected String loadTemplate(IMPDRWithDynamicCode impdrWithDynamicCode,
                                  MPDRDynamicCodeType mpdrDynamicCodeType){

        String templateSQLCode = "";
        templateSQLCode += TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirDynamicCodeDB(), mpdrDynamicCodeType.getTemplateFileName()) ;
        return genrateFromTemplate(impdrWithDynamicCode, mpdrDynamicCodeType, templateSQLCode);
    }

    protected String genrateFromTemplate(IMPDRWithDynamicCode impdrWithDynamicCode, MPDRDynamicCodeType mpdrDynamicCodeType, String templateSQLCode){
        if (mpdrDynamicCodeType == MPDRDynamicCodeType.TABLE_DEP_JOIN_PARENT){
            return generateTableDepJoinParent(impdrWithDynamicCode, templateSQLCode);
        }
        throw new CodeApplException("Le code dynamique " + mpdrDynamicCodeType.getKey() + "n'est pas encore traité");
    }

    protected String generateTableDepJoinParent(IMPDRWithDynamicCode impdrWithDynamicCode,
                                                String templateSQLCode){
        String generateSQLCode = "";
        if (impdrWithDynamicCode instanceof MPDRTrigger){
            ArrayList<MDRColumn> mdrColumns = ((MPDRTrigger) impdrWithDynamicCode).getMPDRTableAccueil().getMPDRPK().getMDRColumns();
            for (MDRColumn mdrColumn : mdrColumns){
                if (mdrColumn.isFk())  {
                    generateSQLCode += System.lineSeparator() + templateSQLCode ;
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode,
                            Preferences.MPDR_NEW_RECORD_WORD,
                            getMPDRGenerateSQL().mpdrModel.getNewRecordWord());
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode,
                            Preferences.MDR_TABLE_NAME_WORD,
                            mdrColumn.getMDRTableAccueil().getName());
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode,
                            Preferences.MDR_COLUMN_NAME_WORD,
                            mdrColumn.getName());
                }
            }
            return generateSQLCode;
        }
        throw new CodeApplException("Le code dynamique generateTableDepJoinParent() n'est  traité que pour les triggers");
    }


    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}

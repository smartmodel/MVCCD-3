package generatorsql.generator;

import exceptions.CodeApplException;
import generatorsql.MPDRGenerateSQLUtil;
import mdr.MDRColumn;
import mpdr.MPDRDB;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRDynamicCodeType;
import mpdr.tapis.interfaces.IMPDRWithDynamicCode;
import preferences.Preferences;
import utilities.files.FileRead;

import java.util.ArrayList;

public abstract class MPDRGenerateSQLDynamicCode {


    public MPDRGenerateSQLDynamicCode() {
    }


    public String generateSQLCodeDynamic(IMPDRWithDynamicCode impdrWithDynamicCode, String generateSQLCode ){
        MPDRDB mpdrDB = getMPDRGenerateSQL().mpdrModel.getDb();

        for (MPDRDynamicCodeType mpdrDynamicCodeType : MPDRDynamicCodeType.getAllForDB(mpdrDB)){
            if (MPDRGenerateSQLUtil.find(generateSQLCode, mpdrDynamicCodeType.getKey())){
                //String sqlCodeDynamic = loadTemplate(impdrWithDynamicCode, mpdrDynamicCodeType);
                String templateSQLCode = loadTemplate(mpdrDynamicCodeType);
                String tabsApplicable = MPDRGenerateSQLUtil.tabsApplicable(generateSQLCode, mpdrDynamicCodeType.getKey());

                String sqlCodeDynamic = generateFromTemplate(impdrWithDynamicCode, mpdrDynamicCodeType, templateSQLCode, tabsApplicable);
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, mpdrDynamicCodeType.getKey(), sqlCodeDynamic);
            }
        }

        return generateSQLCode;
    }


    protected String loadTemplate(MPDRDynamicCodeType mpdrDynamicCodeType){
        return FileRead.readToString(getMPDRGenerateSQL().getTemplateDirDynamicCodeDB(), mpdrDynamicCodeType.getTemplateFileName()) ;
    }

    /*
    protected String loadTemplate(IMPDRWithDynamicCode impdrWithDynamicCode,
                                  MPDRDynamicCodeType mpdrDynamicCodeType){

        String templateSQLCode = "";
        templateSQLCode += TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirDynamicCodeDB(), mpdrDynamicCodeType.getTemplateFileName()) ;
        return generateFromTemplate(impdrWithDynamicCode, mpdrDynamicCodeType, templateSQLCode);
    }

     */

    protected String generateFromTemplate(IMPDRWithDynamicCode impdrWithDynamicCode,
                                          MPDRDynamicCodeType mpdrDynamicCodeType,
                                          String templateSQLCode,
                                          String tabsApplicable){
        if (mpdrDynamicCodeType == MPDRDynamicCodeType.TABLE_DEP_JOIN_PARENT){
            return generateTableDepJoinParent(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        throw new CodeApplException("Le code dynamique " + mpdrDynamicCodeType.getKey() + "n'est pas encore traité");
    }

    protected String generateTableDepJoinParent(IMPDRWithDynamicCode impdrWithDynamicCode,
                                                String templateSQLCode,
                                                String tabsApplicable){
        String generateSQLCode = "";
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();

            ArrayList<MDRColumn> mdrColumns = tableAccueil.getMPDRPK().getMDRColumns();
            boolean firstColumn = true;
            for (MDRColumn mdrColumn : mdrColumns){
                if (mdrColumn.isFk())  {
                    if (!firstColumn) {
                        generateSQLCode +=  System.lineSeparator() + tabsApplicable;
                    }
                    firstColumn = false;
                    generateSQLCode += templateSQLCode ;
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


    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

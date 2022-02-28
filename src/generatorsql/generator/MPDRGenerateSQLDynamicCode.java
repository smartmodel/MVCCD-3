package generatorsql.generator;

import exceptions.CodeApplException;
import generatorsql.MPDRGenerateSQLUtil;
import mdr.MDRColumn;
import mpdr.MPDRColumn;
import mpdr.MPDRDB;
import mpdr.MPDRTable;
import mpdr.tapis.interfaces.IMPDRWithDynamicCode;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;

import java.util.ArrayList;

public abstract class MPDRGenerateSQLDynamicCode {


    public MPDRGenerateSQLDynamicCode() {
    }


    public String generateSQLCodeDynamic(IMPDRWithDynamicCode impdrWithDynamicCode, String generateSQLCode ){
        MPDRDB mpdrDB = getMPDRGenerateSQL().mpdrModel.getDb();

        for (MPDRGenerateSQLDynamicCodeType mpdrGenerateSQLDynamicCodeType : MPDRGenerateSQLDynamicCodeType.getAllForDB(mpdrDB)){
            if (MPDRGenerateSQLUtil.find(generateSQLCode, mpdrGenerateSQLDynamicCodeType.getKey())){
                //String sqlCodeDynamic = loadTemplate(impdrWithDynamicCode, mpdrDynamicCodeType);

                String key = mpdrGenerateSQLDynamicCodeType.getKey();
                String templateSQLCode = template(mpdrGenerateSQLDynamicCodeType);
                String tabsApplicable = MPDRGenerateSQLUtil.tabsApplicable(generateSQLCode, key);

                String sqlCodeDynamic = generateFromTemplate(impdrWithDynamicCode, mpdrGenerateSQLDynamicCodeType, templateSQLCode, tabsApplicable);
                String beforeKey = "";
                String afterKey = "";
                if (StringUtils.isEmpty(sqlCodeDynamic)){
                    // Suppression du saut de ligne et des tabs
                    beforeKey = System.lineSeparator() + tabsApplicable  ;
                }
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, key, sqlCodeDynamic, beforeKey, afterKey);
            }
        }

        return generateSQLCode;
    }


    protected String template(MPDRGenerateSQLDynamicCodeType mpdrGenerateSQLDynamicCodeType){
        return MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirDynamicCodeDB(),
                mpdrGenerateSQLDynamicCodeType.getTemplateFileName(),
                getMPDRGenerateSQL().mpdrModel);
    }

    protected String generateFromTemplate(IMPDRWithDynamicCode impdrWithDynamicCode,
                                          MPDRGenerateSQLDynamicCodeType mpdrGenerateSQLDynamicCodeType,
                                          String templateSQLCode,
                                          String tabsApplicable){
        if (    (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.INS_JN_INS) ||
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.INS_JN_UPD) ||
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.INS_JN_UPD) ||
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.INS_JN_SPEC) ||
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.INS_JN_BODY)    )

        {
            return templateSQLCode;
        }if (    (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.TRIGGER_NEW_TO_RECORD) ||
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.TRIGGER_OLD_TO_RECORD) ||
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.TRIGGER_RECORD_TO_NEW)    )

        {
            return generateTriggerCopyInOrToRecord(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.TABLE_DEP_JOIN_PARENT){
            return generateTableDepJoinParent(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.NODML){
            return generateNoDML(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        throw new CodeApplException("Le code dynamique " + mpdrGenerateSQLDynamicCodeType.getKey() + "n'est pas encore traité");
    }

    private String generateTriggerCopyInOrToRecord(IMPDRWithDynamicCode impdrWithDynamicCode,
                                                   String templateSQLCode,
                                                   String tabsApplicable) {
        String generateSQLCode = "";
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();

        boolean firstColumn = true;
        for (MPDRColumn mpdrColumn : tableAccueil.getMPDRColumns()){
            if (StringUtils.isEmpty(mpdrColumn.getDerivedValue())){
                if (!firstColumn) {
                    generateSQLCode +=  System.lineSeparator() + tabsApplicable;
                }
                firstColumn = false;
                generateSQLCode += templateSQLCode ;
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                        Preferences.MDR_COLUMN_NAME_WORD,
                        mpdrColumn.getName());
            }
        }

        return generateSQLCode;
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
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                            Preferences.MDR_TABLE_NAME_WORD,
                            mdrColumn.getMDRTableAccueil().getName());
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                            Preferences.MDR_COLUMN_NAME_WORD,
                            mdrColumn.getName());
                }
            }
            return generateSQLCode;
    }


    protected String generateNoDML(IMPDRWithDynamicCode impdrWithDynamicCode,
                                                String templateSQLCode,
                                                String tabsApplicable) {
        String generateSQLCode = "";
        if (false) {
            //TODO-0 A traiter
        }
        return generateSQLCode;
    }


    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}

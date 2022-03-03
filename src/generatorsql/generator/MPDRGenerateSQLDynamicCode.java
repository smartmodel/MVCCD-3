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

    private IMPDRWithDynamicCode impdrWithDynamicCode ;
    MPDRTable tableAccueil ;

    public MPDRGenerateSQLDynamicCode() {
    }


    public String generateSQLCodeDynamic(IMPDRWithDynamicCode impdrWithDynamicCode,
                                         String generateSQLCode){
        this.impdrWithDynamicCode = impdrWithDynamicCode ;
        tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();

        MPDRDB mpdrDB = getMPDRGenerateSQL().mpdrModel.getDb();

        for (MPDRGenerateSQLDynamicCodeType mpdrGenerateSQLDynamicCodeType : MPDRGenerateSQLDynamicCodeType.getAllForDB(mpdrDB)){
            if (MPDRGenerateSQLUtil.find(generateSQLCode, mpdrGenerateSQLDynamicCodeType.getKey())){
                //String sqlCodeDynamic = loadTemplate(impdrWithDynamicCode, mpdrDynamicCodeType);
                if (StringUtils.isNotEmpty(mpdrGenerateSQLDynamicCodeType.getTemplateFileName())) {
                    String key = mpdrGenerateSQLDynamicCodeType.getKey();
                    String templateSQLCode = template(mpdrGenerateSQLDynamicCodeType);
                    String tabsApplicable = MPDRGenerateSQLUtil.tabsApplicable(generateSQLCode, key);
                    templateSQLCode = MPDRGenerateSQLUtil.integreTabsApplicable(templateSQLCode,tabsApplicable);

                    String sqlCodeDynamic = generateFromTemplate(impdrWithDynamicCode, mpdrGenerateSQLDynamicCodeType, templateSQLCode, tabsApplicable);
                    String beforeKey = "";
                    String afterKey = "";
                    if (StringUtils.isEmpty(sqlCodeDynamic)) {
                        // Suppression du saut de ligne et des tabs
                        beforeKey = System.lineSeparator() + tabsApplicable;
                    }

                    // Appel récursif - Traitement du code dynamique inclu
                    sqlCodeDynamic = getMPDRGenerateSQL().getMpdrGenerateSQLCodeDynamic().generateSQLCodeDynamic(impdrWithDynamicCode, sqlCodeDynamic);

                    // Inclusion du code généré
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, key, sqlCodeDynamic, beforeKey, afterKey);
               }
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
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.INS_JN_BODY) ||
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.INS_JN_BODY) ||
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.INS_JN_BODY)    )

        {
            return templateSQLCode;
        }
        if (    (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.TRIGGER_NEW_TO_RECORD) ||
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.TRIGGER_OLD_TO_RECORD) ||
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.TRIGGER_RECORD_TO_NEW)    ) {
            return generateTriggerCopyInOrToRecord(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.TABLE_DEP_JOIN_PARENT){
            return generateTableDepJoinParent(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.NODML){
            return generateNoDML(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMN_PK_IND ){
            return generateColumnPKInd(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMN_PK_DEP ){
            return generateColumnPKDep(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMN_DEFAULT_VALUE ){
            return generateColumnDefaultValue(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.ORDERED_INS ){
            return generateOrderedIns(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if (    (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMNS_AUDIT_AJ) ||
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMNS_AUDIT_MO)    ) {
            return generateColumnsAudit(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMN_LIEN_PROG ){
            return generateColumnLienProg(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMNS_UPPERCASE ){
            return generateColumnsUppercase(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        throw new CodeApplException("Le code dynamique " + mpdrGenerateSQLDynamicCodeType.getKey() + "n'est pas encore traité");
    }

    private String generateColumnPKInd(IMPDRWithDynamicCode impdrWithDynamicCode,
                                       String templateSQLCode,
                                       String tabsApplicable) {
        String generateSQLCode = "" ;

        if (tableAccueil.isIndependant()){
            generateSQLCode = templateSQLCode;
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_COLUMN_PK_NAME_WORD,
                        tableAccueil.getMPDRColumnPKProper().getName());

            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_SEQUENCE_NAME_WORD,
                            tableAccueil.getMPDRColumnPKProper().getMPDRSequence().getName());
        }

        return generateSQLCode;
    }


    private String generateColumnPKDep(IMPDRWithDynamicCode impdrWithDynamicCode,
                                       String templateSQLCode,
                                       String tabsApplicable) {
        String generateSQLCode = "";

         if (tableAccueil.isKindDependant()){
             generateSQLCode = templateSQLCode;
             generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_COLUMN_PK_NAME_WORD,
                        tableAccueil.getMPDRColumnPKProper().getName());
         }

        return generateSQLCode;
    }


    private String generateColumnDefaultValue(IMPDRWithDynamicCode impdrWithDynamicCode,
                                              String templateSQLCode,
                                              String tabsApplicable) {
        String generateSQLCode = "";

        for (MPDRColumn mpdrColumn : tableAccueil.getMPDRColumns()){
            if (StringUtils.isNotEmpty(mpdrColumn.getInitValue())) {
                // Tester que la valeur ne soit pas prise en chage par l'option DEFAULT de SQL-DDL
                if (true){
                    if (StringUtils.isNotEmpty(generateSQLCode)) {
                        generateSQLCode += System.lineSeparator() + tabsApplicable;
                    }
                    generateSQLCode += templateSQLCode;
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                            Preferences.MDR_COLUMN_NAME_WORD, mpdrColumn.getName());
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                            Preferences.MDR_DEFAULT_VALUE_WORD, mpdrColumn.getInitValue());
                }
            }
        }

        return generateSQLCode;

    }

    private String generateTriggerCopyInOrToRecord(IMPDRWithDynamicCode impdrWithDynamicCode,
                                                   String templateSQLCode,
                                                   String tabsApplicable) {
        String generateSQLCode = "";

        for (MPDRColumn mpdrColumn : tableAccueil.getMPDRColumns()){
            if (StringUtils.isEmpty(mpdrColumn.getDerivedValue())){
                if (StringUtils.isNotEmpty(generateSQLCode)){
                    generateSQLCode +=  System.lineSeparator() + tabsApplicable;
                }
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

            ArrayList<MDRColumn> mdrColumns = tableAccueil.getMPDRPK().getMDRColumns();
            for (MDRColumn mdrColumn : mdrColumns){
                if (mdrColumn.isFk())  {
                    if (StringUtils.isNotEmpty(generateSQLCode)){
                        generateSQLCode +=  System.lineSeparator() + tabsApplicable;
                    }
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

    private String generateOrderedIns(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        return generateSQLCode;
    }


    private String generateColumnsAudit(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        // tester si la source (MLDRTable) de la table d'accueil (MPDRTable) est dotée d'une contrainte MLDRAudit
        return generateSQLCode;
    }


    private String generateColumnLienProg(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        return generateSQLCode;
    }


    private String generateColumnsUppercase(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";

        for (MPDRColumn mpdrColumn : tableAccueil.getMPDRColumns()){
            if (mpdrColumn.isUppercase()){
                if (StringUtils.isNotEmpty(generateSQLCode)){
                    generateSQLCode +=  System.lineSeparator() + tabsApplicable;
                }
                generateSQLCode += templateSQLCode ;
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                        Preferences.MDR_COLUMN_NAME_WORD,
                        mpdrColumn.getName());
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

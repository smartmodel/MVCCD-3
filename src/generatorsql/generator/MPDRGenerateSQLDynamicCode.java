package generatorsql.generator;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import exceptions.CodeApplException;
import generatorsql.MPDRGenerateSQLUtil;
import mdr.MDRColumn;
import mdr.MDRPK;
import mdr.MDRTable;
import mdr.services.MDRColumnsService;
import messages.MessagesBuilder;
import mpdr.MPDRColumn;
import mpdr.MPDRDB;
import mpdr.MPDRFK;
import mpdr.MPDRTable;
import mpdr.tapis.*;
import mpdr.tapis.interfaces.IMPDRWithDynamicCode;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import stereotypes.Stereotype;

import java.util.ArrayList;

public abstract class MPDRGenerateSQLDynamicCode {

    private IMPDRWithDynamicCode impdrWithDynamicCode ;

    public MPDRGenerateSQLDynamicCode() {
    }


    public String generateSQLCodeDynamic(IMPDRWithDynamicCode impdrWithDynamicCode,
                                         String generateSQLCode){
        this.impdrWithDynamicCode = impdrWithDynamicCode ;
        String codeInitial = generateSQLCode;
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
                    // Cet appel n'est pas correct car il dépend de la position dans la boucle
                    //sqlCodeDynamic = getMPDRGenerateSQL().getMpdrGenerateSQLCodeDynamic().generateSQLCodeDynamic(impdrWithDynamicCode, sqlCodeDynamic);

                    // Inclusion du code généré
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, key, sqlCodeDynamic, beforeKey, afterKey);
               }
            }
        }

        // Appel récursif pour le cas où un changement a eu lieu et que le marker de code dynamique se trouve en amont du marker
        // qui avait déclenché l'appel

        // La structure des markers de code dynamique doit être telle que les types inclus se trouvent après les types qui les incluent!
        /*
        if (! codeInitial.equals(generateSQLCode)){
            generateSQLCode = generateSQLCodeDynamic(impdrWithDynamicCode, generateSQLCode);
        }
         */

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
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.INS_JN_DEL) ||
                (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.INS_JN_SPEC)     )

        {
            return templateSQLCode;
        }
        if  (mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.INS_JN_BODY){
            return generateJnalBody(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
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
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMNS_TYPE_CHECK){
            return generateColumnsDatatypeCheck(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMNS_JN_TECH_NAME){
            return generateColumnsJnTechName(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMNS_JN_OLD_NAME){
            return generateColumnsJnOldName(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMNS_JN_NEW_NAME){
            return generateColumnsJnNewName(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMNS_JN_TECH_VALUE){
            return generateColumnsJnTechValue(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMNS_JN_OLD_VALUE){
            return generateColumnsJnOldValue(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMNS_JN_NEW_VALUE){
            return generateColumnsJnNewValue(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.INNER_JOIN ){
            return generateInnerJoin(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.IOIR_VIEW_SPECIALIZED ){
            return generateIOIRViewSpecialized(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.IOIR_VIEW_MEMBER_SPECIALIZED ){
            return generateIOIRViewMemberSpecialized(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
        }
        throw new CodeApplException("Le code dynamique " + mpdrGenerateSQLDynamicCodeType.getKey() + "n'est pas encore traité");
    }

    private String generateColumnPKInd(IMPDRWithDynamicCode impdrWithDynamicCode,
                                       String templateSQLCode,
                                       String tabsApplicable) {
        String generateSQLCode = "" ;
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();

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
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();

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
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();

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
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();

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
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();

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
        // tester si la source (MLDRTable) de la table d'accueil (MPDRTable) est dotée d'une contrainte MLDRAudit

        String generateSQLCode = "";
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();

        if (tableAccueil.getMPDRConstraintCustomAudit() != null) {
            generateSQLCode += templateSQLCode;
            for (MPDRColumnAudit mpdrColumnAudit : tableAccueil.getMPDRColumnsAudit()) {
                boolean c1 = mpdrColumnAudit.getSterereotypeAudit().getLienProg().equals(Preferences.STEREOTYPE_AUDIT_AJUSER_LIENPROG) &&
                        templateSQLCode.contains(Preferences.MPDR_COLUMN_AUDIT_AJUSER_NAME_WORD) ;

                boolean c2 = mpdrColumnAudit.getSterereotypeAudit().getLienProg().equals(Preferences.STEREOTYPE_AUDIT_AJDATE_LIENPROG) &&
                        templateSQLCode.contains(Preferences.MPDR_COLUMN_AUDIT_AJDATE_NAME_WORD) ;

                boolean c3 = mpdrColumnAudit.getSterereotypeAudit().getLienProg().equals(Preferences.STEREOTYPE_AUDIT_MOUSER_LIENPROG) &&
                        templateSQLCode.contains(Preferences.MPDR_COLUMN_AUDIT_MOUSER_NAME_WORD) ;

                boolean c4 = mpdrColumnAudit.getSterereotypeAudit().getLienProg().equals(Preferences.STEREOTYPE_AUDIT_MODATE_LIENPROG) &&
                        templateSQLCode.contains(Preferences.MPDR_COLUMN_AUDIT_MODATE_NAME_WORD) ;
                if (c1) {
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                            Preferences.MPDR_COLUMN_AUDIT_AJUSER_NAME_WORD, mpdrColumnAudit.getName());
                }
                if (c2) {
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                            Preferences.MPDR_COLUMN_AUDIT_AJDATE_NAME_WORD, mpdrColumnAudit.getName());
                }
                if (c3) {
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                            Preferences.MPDR_COLUMN_AUDIT_MOUSER_NAME_WORD, mpdrColumnAudit.getName());
                }
                if (c4) {
                    generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                            Preferences.MPDR_COLUMN_AUDIT_MODATE_NAME_WORD, mpdrColumnAudit.getName());
                }
            }
        }
        return generateSQLCode;
    }


    private String generateJnalBody(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        // tester si la source (MLDRTable) de la table d'accueil (MPDRTable) est dotée d'une table de journalisation

        String generateSQLCode = "";
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();

        if (tableAccueil.getMPDRTableJnal() != null) {
            generateSQLCode += templateSQLCode;
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                    Preferences.MPDR_TABLE_JNAL_NAME_WORD, tableAccueil.getMPDRTableJnal().getName());
        }
        return generateSQLCode;
    }



    private String generateColumnLienProg(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        return generateSQLCode;
    }


    private String generateColumnsUppercase(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();

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


    private String generateColumnsJnTechName(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();
        MPDRTableJnal tableJnal = tableAccueil.getMPDRTableJnal();
        if (tableJnal != null) {
            for (MPDRColumnJnalTech mpdrColumnJnalTech : tableJnal.getMPDRColumnsJnalTech()) {
               generateSQLCode = generateColumnsJnName(generateSQLCode, templateSQLCode, tabsApplicable, mpdrColumnJnalTech, true);
            }
        }
        return generateSQLCode;
    }

    private String generateColumnsJnOldName(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();
        MPDRTableJnal tableJnal = tableAccueil.getMPDRTableJnal();
        if (tableJnal != null) {
            for (MPDRColumnJnalDatas mpdrColumnJnalDatas : tableJnal.getMPDRColumnsJnalDatas()) {
                MPDRColumn mpdrColumnSource = mpdrColumnJnalDatas.getMpdrColumnSource();
                if (mpdrColumnSource.isPk()) {
                    generateSQLCode = generateColumnsJnName(generateSQLCode, templateSQLCode, tabsApplicable,
                                mpdrColumnJnalDatas, false);
                }
            }
        }
        return generateSQLCode;
    }

    private String generateColumnsJnNewName(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();
        MPDRTableJnal tableJnal = tableAccueil.getMPDRTableJnal();
        if (tableJnal != null) {
            for (MPDRColumnJnalDatas mpdrColumnJnalDatas : tableJnal.getMPDRColumnsJnalDatas()) {
                generateSQLCode = generateColumnsJnName(generateSQLCode, templateSQLCode, tabsApplicable, mpdrColumnJnalDatas, false);
            }
        }
        return generateSQLCode;
    }

    private String generateColumnsJnName(String generateSQLCode, String templateSQLCode, String tabsApplicable,
                                         MPDRColumnJnal mpdrColumnJnal, boolean separatorArguments){
        if (StringUtils.isNotEmpty(generateSQLCode)) {
            generateSQLCode += System.lineSeparator() + tabsApplicable ;
            if (separatorArguments) {
                generateSQLCode += Preferences.SQL_SEPARATOR_ARGUMENTS + " ";
            }
        }
        generateSQLCode += templateSQLCode;
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MDR_COLUMN_NAME_WORD,
                mpdrColumnJnal.getName());
        return generateSQLCode;
    }



    private String generateColumnsJnTechValue(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();
        MPDRTableJnal tableJnal = tableAccueil.getMPDRTableJnal();
        if (tableJnal != null) {
            for (MPDRColumnJnalTech mpdrColumnJnalTech : tableJnal.getMPDRColumnsJnalTech()) {
                MPDRDB mpdrdb = getMPDRGenerateSQL().mpdrModel.getDb();
                    String value = "";
                    Stereotype stereotypeJnal = mpdrColumnJnalTech.getSterereotypeJnal();
                    //MPDREnumColumnsJnalTech mpdrEnumColumnsJnal =MPDREnumColumnsJnalTech.getByStereotypeLienProg(getMPDRGenerateSQL().mpdrModel.getDb(), stereotypeJnal.getLienProg());
                    //if (mpdrEnumColumnsJnal.)
                    if (stereotypeJnal.getLienProg().equals(Preferences.STEREOTYPE_JNAL_DATETIME_LIENPROG)){
                        value = mpdrdb.getDateTimeInString();
                    } else if (stereotypeJnal.getLienProg().equals(Preferences.STEREOTYPE_JNAL_OPERATION_LIENPROG)){
                        value = "{pi_mode}";
                    } else if (stereotypeJnal.getLienProg().equals(Preferences.STEREOTYPE_JNAL_USER_LIENPROG)){
                        value = mpdrdb.getUserInString();
                    } else if (stereotypeJnal.getLienProg().equals(Preferences.STEREOTYPE_JNAL_SESSION_LIENPROG)){
                        value = mpdrdb.getSessionInString();
                    } else if (stereotypeJnal.getLienProg().equals(Preferences.STEREOTYPE_JNAL_APPL_LIENPROG)){
                        value = mpdrdb.getApplInString();
                    } else if (stereotypeJnal.getLienProg().equals(Preferences.STEREOTYPE_JNAL_NOTES_LIENPROG)){
                        value = mpdrdb.getNotesInString();
                    }
                    generateSQLCode = generateColumnsJnValue(generateSQLCode, templateSQLCode, tabsApplicable,
                            value, true);

            }
        }
        return generateSQLCode;
    }


    private String generateColumnsJnOldValue(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();
        MPDRTableJnal tableJnal = tableAccueil.getMPDRTableJnal();
        if (tableJnal != null) {
            for (MPDRColumnJnalDatas mpdrColumnJnalDatas : tableJnal.getMPDRColumnsJnalDatas()) {
                MPDRColumn mpdrColumnSource = mpdrColumnJnalDatas.getMpdrColumnSource();
                if (mpdrColumnSource.isPk()) {
                        generateSQLCode = generateColumnsJnName(generateSQLCode, templateSQLCode, tabsApplicable,
                                mpdrColumnJnalDatas, false);
                }
            }
        }
        return generateSQLCode;
    }

    private String generateColumnsJnNewValue(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();
        MPDRTableJnal tableJnal = tableAccueil.getMPDRTableJnal();
        if (tableJnal != null) {
            for (MPDRColumnJnalDatas mpdrColumnJnalDatas : tableJnal.getMPDRColumnsJnalDatas()) {
                MPDRColumn mpdrColumnSource = mpdrColumnJnalDatas.getMpdrColumnSource();
                generateSQLCode = generateColumnsJnName(generateSQLCode, templateSQLCode, tabsApplicable,
                            mpdrColumnJnalDatas, false);
            }
        }
        return generateSQLCode;
    }

    private String generateColumnsJnValue(String generateSQLCode, String templateSQLCode, String tabsApplicable,
                                         String value, boolean separatorArguments){
        if (StringUtils.isNotEmpty(generateSQLCode)) {
            generateSQLCode += System.lineSeparator() + tabsApplicable ;
            if (separatorArguments) {
                generateSQLCode += Preferences.SQL_SEPARATOR_ARGUMENTS + " ";
            }
        }
        generateSQLCode += templateSQLCode;
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MDR_COLUMN_VALUE_WORD,
                value);
        return generateSQLCode;
    }


    private String generateColumnsDatatypeCheck(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        MPDRTable tableAccueil = impdrWithDynamicCode.getMPDRTableAccueil();

        for (MPDRColumn mpdrColumn : tableAccueil.getMPDRColumns()){
            if (mpdrColumn.isBusiness()){
                generateSQLCode += generateColumnDatatypeCheck(mpdrColumn,templateSQLCode, tabsApplicable);
            }
        }

        return generateSQLCode;
    }

    //TODO-1 Version BD Prévoir un enchainement des appels au niveau de la BD pour éviter les multiples tests NOT NULL
    // De plus, il faudra profiter de mettre les messages de violations d'intégité au niveau d'une table de la BD
    // et ceci pour toutes les contraintes pour favoriser le développement des interfaces utilisateurs


    private String generateColumnDatatypeCheck(MPDRColumn mpdrColumn, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        // Text
        MCDDatatype mcdDatatype = MDDatatypeService.getMCDDatatypeByLienProg(mpdrColumn.getDatatypeConstraintLienProg());
        if (mcdDatatype.isSelfOrDescendantOf(MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NORMALIZEDSTRING_LIENPROG))) {
            generateSQLCode = generateColumnDatatypeCheckDescendantOrSelfNormalizedString(mpdrColumn, templateSQLCode, tabsApplicable, mcdDatatype);
        }
        if (mcdDatatype.isSelfOrDescendantOf(MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_TOKEN_LIENPROG))) {
            generateSQLCode += generateColumnDatatypeCheckDescendantOrSelfToken(mpdrColumn, templateSQLCode, tabsApplicable, mcdDatatype);
        }
        if (mcdDatatype.isSelfOrDescendantOf(MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_WORD_LIENPROG))) {
            generateSQLCode += generateColumnDatatypeCheckDescendantOrSelfWord(mpdrColumn, templateSQLCode, tabsApplicable, mcdDatatype);
        }

        //TODO-0 A faire le traitement des nombres
        return generateSQLCode;
    }

    //TODO-1 Revoir le code de check des types textuels pour faire le parcours en remontant la hiérarchie des types
    private String generateColumnDatatypeCheckDescendantOrSelfNormalizedString(MPDRColumn mpdrColumn,
                                                                               String templateSQLCode,
                                                                               String tabsApplicable,
                                                                               MCDDatatype mcdDatatype) {

        String templateSQLCodeWithTabsApplicable = System.lineSeparator() + tabsApplicable + templateSQLCode;

        MPDRGenerateSQLIntegrityError mpdrGenerateSQLIntegrityError = MPDRGenerateSQLIntegrityError.DATATYPE_NORMALIZEDSTRING;
        String messageError = MessagesBuilder.getMessagesProperty(mpdrGenerateSQLIntegrityError.getMessErrProperty(), mcdDatatype.getName());
        Integer noError = mpdrGenerateSQLIntegrityError.getNoErr();

        String generateSQLCode =  generateColumnDatatypeCheckFinalize(mpdrColumn, templateSQLCodeWithTabsApplicable,
                Preferences.MPDR_CHECK_NORMALIZED_STRING, noError, messageError);

        return generateSQLCode;
    }

    private String generateColumnDatatypeCheckDescendantOrSelfToken(MPDRColumn mpdrColumn,
                                                                    String templateSQLCode,
                                                                    String tabsApplicable,
                                                                    MCDDatatype mcdDatatype) {

        String templateSQLCodeWithTabsApplicable = System.lineSeparator() + tabsApplicable + templateSQLCode;

        MPDRGenerateSQLIntegrityError mpdrGenerateSQLIntegrityError = MPDRGenerateSQLIntegrityError.DATATYPE_TOKEN;
        String messageError = MessagesBuilder.getMessagesProperty(mpdrGenerateSQLIntegrityError.getMessErrProperty(), mcdDatatype.getName());
        Integer noError = mpdrGenerateSQLIntegrityError.getNoErr();

        String generateSQLCode =  generateColumnDatatypeCheckFinalize(mpdrColumn, templateSQLCodeWithTabsApplicable,
               Preferences.MPDR_CHECK_TOKEN, noError, messageError);

        return generateSQLCode;
    }

    private String generateColumnDatatypeCheckDescendantOrSelfWord(MPDRColumn mpdrColumn,
                                                                   String templateSQLCode,
                                                                   String tabsApplicable,
                                                                   MCDDatatype mcdDatatype) {

        String templateSQLCodeWithTabsApplicable = System.lineSeparator() + tabsApplicable + templateSQLCode;

        MPDRGenerateSQLIntegrityError mpdrGenerateSQLIntegrityError = MPDRGenerateSQLIntegrityError.DATATYPE_WORD;
        String messageError = MessagesBuilder.getMessagesProperty(mpdrGenerateSQLIntegrityError.getMessErrProperty(), mcdDatatype.getName());
        Integer noError = mpdrGenerateSQLIntegrityError.getNoErr();

        String generateSQLCode = generateColumnDatatypeCheckFinalize(mpdrColumn, templateSQLCodeWithTabsApplicable,
                Preferences.MPDR_CHECK_WORD, noError, messageError);

        return generateSQLCode;
    }

    private String generateColumnDatatypeCheckFinalize(MPDRColumn mpdrColumn,
                                                       String templateSQLCodeWithTabsApplicable,
                                                       String procedure,
                                                       Integer noError,
                                                       String messError) {

        String generateSQLCode = templateSQLCodeWithTabsApplicable;

        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MPDR_PACKAGE_RESOURCES_NAME_WORD,
                MPDRPackageType.RESOURCES_SPEC.getMarker());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MPDR_PROCEDURE_RESOURCES_CHECK_DATATYPE_WORD,
                procedure);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MDR_TABLE_NAME_WORD,
                mpdrColumn.getMDRTableAccueil().getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MDR_COLUMN_NAME_WORD,
                mpdrColumn.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MPDR_INTEGRITY_NO_ERROR,
                noError.toString());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MPDR_INTEGRITY_MESS_ERROR,
                messError);

        return generateSQLCode;

    }


    private String generateInnerJoin(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        MPDRView mpdrView = (MPDRView) impdrWithDynamicCode;
        ArrayList<MPDRFK> mpdrFKsBaseJoin = mpdrView.getMDRFKToTablesGenInCascade();
        for (int i = mpdrFKsBaseJoin.size() - 1 ; i >= 0 ; i--){
            MPDRFK mpdrFKBaseJoin = mpdrFKsBaseJoin.get(i);

            MDRTable mdrTableFK = mpdrFKBaseJoin.getMDRTableAccueil();
            //MPDRFK mpdrFK = mpdrTableFK.getMPDRConstraintCustomSpecialized().getMPDRFKToTableGen();
            ArrayList<MDRColumn> mdrColumnsFK = mpdrFKBaseJoin.getMDRColumns();
            MDRPK mdrPK = mpdrFKBaseJoin.getMdrPK();
            MDRTable mdrTablePK = mdrPK.getMDRTableAccueil();
            ArrayList<MDRColumn> mdrColumnsPK = mdrPK.getMDRColumns();


            if (StringUtils.isNotEmpty(generateSQLCode)){
                generateSQLCode +=  System.lineSeparator() + tabsApplicable;
            }
            generateSQLCode += templateSQLCode ;
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                    Preferences.MDR_TABLE_FK_NAME_WORD, mdrTableFK.getName());
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                    Preferences.MDR_COLUMN_FK_NAME_WORD, mdrColumnsFK.get(0).getName());
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                    Preferences.MDR_TABLE_PK_NAME_WORD, mdrTablePK.getName());
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                    Preferences.MDR_COLUMN_PK_NAME_WORD, mdrColumnsPK.get(0).getName());

        }
        return generateSQLCode;
    }



    private String generateIOIRViewSpecialized(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = templateSQLCode;
        MPDRTrigger mpdrTrigger = (MPDRTrigger) impdrWithDynamicCode;
        MPDRView mpdrView = mpdrTrigger.getMPDRViewAccueil();
        MPDRTable mpdrTableGenOrigin =  mpdrView.getMPDRTableGenOrigin();
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_ORIGIN_NAME_WORD, mpdrTableGenOrigin.getName());

        ArrayList<MPDRColumn> mpdrColumns =  mpdrView.getMDRColumnsInTable(mpdrTableGenOrigin);

        // Important
        // ---------
        //Les 2 requêtes getList ci-dessous se basent toutes deux sur getMDRColumnsSortDefault()
        // pour que le maapping entre le nom de colonne et le nom de la source dans la requête SQL soit préservé !
        ArrayList<MPDRColumn> mpdrColumnsInTable = mpdrView.getMDRColumnsInTable(mpdrTableGenOrigin);
        String mpdrColumnsInTableList = MDRColumnsService.getListColumnsAsString(mpdrColumnsInTable,
                Preferences.PARAMETERS_SEPARATOR,  false);
        String mpdrNewColumnsInTableList = MDRColumnsService.getListRecordColumnsAsString(mpdrColumnsInTable,
                Preferences.PARAMETERS_SEPARATOR, getMPDRGenerateSQL().mpdrModel.getWordRecordNew());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_LIST_COLUMNS_WORD,
                mpdrColumnsInTableList);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_LIST_NEW_COLUMNS_WORD,
                mpdrNewColumnsInTableList);

        MPDRColumn mpdrColumnPK = mpdrTableGenOrigin.getMPDRColumnPKProper();
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_COLUMN_PK_NAME_WORD, mpdrColumnPK.getName());


        return generateSQLCode;
    }


    private String generateIOIRViewMemberSpecialized(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        MPDRTrigger mpdrTrigger = (MPDRTrigger) impdrWithDynamicCode;
        MPDRView mpdrView = mpdrTrigger.getMPDRViewAccueil();
        ArrayList<MPDRFK> mpdrFKsBaseJoin = mpdrView.getMDRFKToTablesGenInCascade();
        for (int i = mpdrFKsBaseJoin.size() - 1 ; i >= 0 ; i--){
            MPDRFK mpdrFKBaseJoin = mpdrFKsBaseJoin.get(i);
            MPDRTable mpdrTableFK = (MPDRTable) mpdrFKBaseJoin.getMDRTableAccueil();
            ArrayList<MPDRColumn> mpdrColumnsInTable = mpdrView.getMDRColumnsInTable(mpdrTableFK);
            ArrayList<MDRColumn> mpdrColumnsPK = mpdrTableFK.getMPDRPK().getMDRColumns();

            if (StringUtils.isNotEmpty(generateSQLCode)){
                generateSQLCode +=  System.lineSeparator() + tabsApplicable;
            }
            generateSQLCode += templateSQLCode ;

            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_ORIGIN_NAME_WORD, mpdrTableFK.getName());

            String mpdrColumnsInTableList = MDRColumnsService.getListColumnsAsString(mpdrColumnsInTable,
                    Preferences.PARAMETERS_SEPARATOR,  false);
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_LIST_COLUMNS_WORD,
                    mpdrColumnsInTableList );

            String mpdrColumnPKInTableList = MDRColumnsService.getListColumnsAsString(mpdrColumnsPK,
                    Preferences.PARAMETERS_SEPARATOR,  false);
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_COLUMN_PK_NAME_WORD,
                    mpdrColumnPKInTableList );

            String mpdrNewColumnsInTableList = MDRColumnsService.getListRecordColumnsAsString(mpdrColumnsInTable,
                    Preferences.PARAMETERS_SEPARATOR, getMPDRGenerateSQL().mpdrModel.getWordRecordNew());
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_LIST_NEW_COLUMNS_WORD,
                    mpdrNewColumnsInTableList );
        }

        return generateSQLCode;
    }


    /*
    private String generateColumnsSpecialized(IMPDRWithDynamicCode impdrWithDynamicCode, String templateSQLCode, String tabsApplicable) {
        String generateSQLCode = "";
        ArrayList<String>  namesColumnsInList = new ArrayList<String>();

        for (MPDRColumn mpdrColumn : tableAccueil.getMPDRColumns()){
            if (StringUtils.isNotEmpty(generateSQLCode)){
                generateSQLCode +=  ",";

            generateSQLCode += mpdrColumn.getName() ;   }
            namesColumnsInList.add(mpdrColumn.getName());
         }
       return generateSQLCode;
    }

     */


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

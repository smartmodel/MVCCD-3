package generatorsql.generator;

import exceptions.CodeApplException;
import generatorsql.MPDRGenerateSQLUtil;
import mdr.MDRColumn;
import mdr.MDRPK;
import mdr.MDRTable;
import mdr.services.MDRColumnsService;
import mpdr.MPDRColumn;
import mpdr.MPDRDB;
import mpdr.MPDRFK;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRTrigger;
import mpdr.tapis.MPDRView;
import mpdr.tapis.interfaces.IMPDRWithDynamicCode;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;

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
        if ( mpdrGenerateSQLDynamicCodeType == MPDRGenerateSQLDynamicCodeType.COLUMNS_UPPERCASE ){
            return generateColumnsUppercase(impdrWithDynamicCode, templateSQLCode, tabsApplicable);
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

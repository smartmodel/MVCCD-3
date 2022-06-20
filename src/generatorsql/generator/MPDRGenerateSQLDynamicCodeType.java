package generatorsql.generator;


import mpdr.MPDRDB;

import java.util.ArrayList;

// Important
// Le code inclu doit se trouver dans la liste après le code d'appel

public enum MPDRGenerateSQLDynamicCodeType {
    INNER_JOIN("-INNER_JOIN-", "inner_join.txt",new MPDRDB[]{MPDRDB.ORACLE}),
    IOIR_VIEW_SPECIALIZED("-IOIR_VIEW_SPECIALIZED-", "ioir_view_specialized.txt",new MPDRDB[]{MPDRDB.ORACLE}),
    IOIR_VIEW_MEMBER_SPECIALIZED("-IOIR_VIEW_MEMBER_SPECIALIZED-", "ioir_view_member_specialized.txt",new MPDRDB[]{MPDRDB.ORACLE}),
    TRIGGER_NEW_TO_RECORD("-TRIGGER_NEW_TO_RECORD-", "trigger_new_to_record.txt", new MPDRDB[]{MPDRDB.ORACLE} ),
    TRIGGER_OLD_TO_RECORD("-TRIGGER_OLD_TO_RECORD-", "trigger_old_to_record.txt", new MPDRDB[]{MPDRDB.ORACLE} ),
    TRIGGER_RECORD_TO_NEW("-TRIGGER_RECORD_TO_NEW-", "trigger_record_to_new.txt", new MPDRDB[]{MPDRDB.ORACLE} ),
    TABLE_DEP_JOIN_PARENT("-TABLE_DEP_JOIN_PARENT-", "tableDepJoinParent.txt",
            new MPDRDB[]{MPDRDB.ORACLE, MPDRDB.MYSQL, MPDRDB.POSTGRESQL} ),
    ASSNN_REF_NONORIENTED_COLFIRST("ASSNN_REF_NONORIENTED_COLFIRST", "", new MPDRDB[]{MPDRDB.ORACLE} ),
    ASSNN_REF_NONORIENTED_COLSECOND("ASSNN_REF_NONORIENTED_COLSECOND", "", new MPDRDB[]{MPDRDB.ORACLE} ),
    COLUMNS_TO_GENERALIZE("COLUMNS_TO_GENERALIZE", "", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_TO_SPECIALIZE("COLUMNS_TO_SPECIALIZE", "", new MPDRDB[]{MPDRDB.ORACLE}),
    ASS11_NONORIENTED_PKFIRST("ASS11_NONORIENTED_PKFIRST", "", new MPDRDB[]{MPDRDB.ORACLE}),
    ASS11_NONORIENTED_FKFIRST("ASS11_NONORIENTED_FKFIRST", "", new MPDRDB[]{MPDRDB.ORACLE}),
    ASS11_FKNOTNULL("ASS11_FKNOTNULL", "",new MPDRDB[]{MPDRDB.ORACLE}),
    IOINS_ASSNNNONORIENTED_WHERE("IOINS_ASSNNNONORIENTED_WHERE", "", new MPDRDB[]{MPDRDB.ORACLE}),
    IODEL_ASSNNNONORIENTED_WHERE_CROSSEDPAIR("IODEL_ASSNNNONORIENTED_WHERE_CROSSEDPAIR", "",new MPDRDB[]{MPDRDB.ORACLE}),
    IODEL_ASSNNNONORIENTED_WHERE_NOTCROSSEDPAIR("IODEL_ASSNNNONORIENTED_WHERE_NOTCROSSEDPAIR", "",new MPDRDB[]{MPDRDB.ORACLE}),
    IO_ASSNNNONORIENTED_UPDATE_COLUMNS("IO_ASSNNNONORIENTED_UPDATE_COLUMNS", "", new MPDRDB[]{MPDRDB.ORACLE}),
    //WRITE_TABLE_FROZEN_NONAUTHORIZED("WRITE_TABLE_FROZEN_NONAUTHORIZED", new MPDRDB[]{MPDRDB.ORACLE}),
    LIST_COLUMNS("LIST_COLUMNS", "", new MPDRDB[]{MPDRDB.ORACLE}),
    LIST_COLUMNS_NEWS("LIST_COLUMNS_NEWS", "", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_UPPERCASE("-COLUMNS_UPPERCASE-", "columns_uppercase.txt", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_TYPE_CHECK("-COLUMNS_TYPE_CHECK-", "columns_type_check.txt", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_FROZEN("COLUMNS_FROZEN", "", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_PEA("COLUMNS_PEA", "",new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMN_TARGET_LIEN_PROG("COLUMN_TARGET_LIEN_PROG", "",new MPDRDB[]{MPDRDB.ORACLE}),
    INS_JN_SPEC("-INS_JN_SPEC-","ins_jn_spec.txt", new MPDRDB[]{MPDRDB.ORACLE}),
    INS_JN_BODY("-INS_JN_BODY-","ins_jn_body.txt", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_JN_NEW_NAME("-COLUMNS_JN_NEW_NAME-", "columns_name.txt", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_JN_NEW_VALUE("-COLUMNS_JN_NEW_VALUE-", "columns_reference_new.txt", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_JN_OLD_NAME("-COLUMNS_JN_OLD_NAME-", "columns_name.txt", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_JN_OLD_VALUE("-COLUMNS_JN_OLD_VALUE-", "columns_reference_old.txt", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_JN_TECH_NAME("-COLUMNS_JN_TECH_NAME-", "columns_name.txt", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_JN_TECH_VALUE("-COLUMNS_JN_TECH_VALUE-", "columns_value.txt", new MPDRDB[]{MPDRDB.ORACLE}),
    INS_JN_INS("-INS_JN_INS-", "ins_jn_ins.txt",new MPDRDB[]{MPDRDB.ORACLE}),
    INS_JN_UPD("-INS_JN_UPD-", "ins_jn_upd.txt",new MPDRDB[]{MPDRDB.ORACLE}),
    INS_JN_DEL("-INS_JN_DEL-", "ins_jn_del.txt",new MPDRDB[]{MPDRDB.ORACLE}),
    ORDERED_INS("-ORDERED_INS-", "ordered_ins.txt",new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMN_DEFAULT_VALUE("-COLUMN_DEFAULT_VALUE-", "column_default_value.txt", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMN_PK_IND("-COLUMN_PK_IND-", "column_pk_ind.txt", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMN_PK_DEP("-COLUMN_PK_DEP-", "column_pk_dep.txt", new MPDRDB[]{MPDRDB.ORACLE}),
    NODML("-NODML-", "nodml.txt",new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_AUDIT_AJ("-COLUMNS_AUDIT_AJ-", "columns_audit_aj.txt",new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_AUDIT_MO("-COLUMNS_AUDIT_MO-", "columns_audit_mo.txt",new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMN_LIEN_PROG("-COLUMN_LIEN_PROG-", "lien_prog.txt",new MPDRDB[]{MPDRDB.ORACLE});




    private java.lang.String key;
    private String templateFileName;
    private ArrayList<MPDRDB> mpdrDBs = new ArrayList<MPDRDB>();



    private MPDRGenerateSQLDynamicCodeType(String key, String templateFileName, MPDRDB[] mpdrDBsArray) {
        this.key = key;
        this.templateFileName = templateFileName;
        if (mpdrDBsArray.length >0) {
            for (int i = 0; i < mpdrDBsArray.length; i++) {
                mpdrDBs.add(mpdrDBsArray[i]);
            }
        }
    }

    public String getKey() {
        return this.key;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public ArrayList<MPDRDB> getMpdrDBs() {
        return mpdrDBs;
    }

    public boolean applicableToMPDRDB(MPDRDB mpdrDB){
        return mpdrDBs.contains(mpdrDB);
    }


    public  static ArrayList<MPDRGenerateSQLDynamicCodeType> getAll() {
        ArrayList<MPDRGenerateSQLDynamicCodeType> resultat = new ArrayList<MPDRGenerateSQLDynamicCodeType>();
        if (values().length > 0){
            for (int i = 0; i < values().length; i++) {
                resultat.add(values()[i]);
            }
        }
        return resultat;
    }

    public static ArrayList<MPDRGenerateSQLDynamicCodeType> getAllForDB(MPDRDB mpdrDB) {
        ArrayList<MPDRGenerateSQLDynamicCodeType> resultat = new ArrayList<MPDRGenerateSQLDynamicCodeType>();
        for (MPDRGenerateSQLDynamicCodeType mpdrGenerateSQLDynamicCodeType : getAll()){
            if (mpdrGenerateSQLDynamicCodeType.applicableToMPDRDB(mpdrDB)) {
                resultat.add(mpdrGenerateSQLDynamicCodeType);
            }
        }
        return resultat ;
    }


}

package mpdr.tapis;


import mpdr.MPDRDB;

import java.util.ArrayList;

public enum MPDRDynamicCodeType {
    TRIGGER_NEW_TO_RECORD("TRIGGER_NEW_TO_RECORD", "", new MPDRDB[]{MPDRDB.ORACLE} ),
    TRIGGER_OLD_TO_RECORD("TRIGGER_OLD_TO_RECORD", "", new MPDRDB[]{MPDRDB.ORACLE} ),
    TRIGGER_RECORD_TO_NEW("TRIGGER_RECORD_TO_NEW", "", new MPDRDB[]{MPDRDB.ORACLE} ),
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
    COLUMNS_UPPERCASE("COLUMNS_UPPERCASE", "", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_TYPE_CHECK("COLUMNS_TYPE_CHECK", "", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_FROZEN("COLUMNS_FROZEN", "", new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMNS_PEA("COLUMNS_PEA", "",new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMN_TARGET_LIEN_PROG("COLUMN_TARGET_LIEN_PROG", "",new MPDRDB[]{MPDRDB.ORACLE}),
    ALL_COLUMNS_NAME("ALL_COLUMNS_NAME", "", new MPDRDB[]{MPDRDB.ORACLE}),
    ALL_COLUMNS_VALUE("ALL_COLUMNS_VALUE", "", new MPDRDB[]{MPDRDB.ORACLE}),
    PK_COLUMNS_NAME("PK_COLUMNS_NAME", "", new MPDRDB[]{MPDRDB.ORACLE}),
    PK_COLUMNS_VALUE("PK_COLUMNS_VALUE", "", new MPDRDB[]{MPDRDB.ORACLE}),
    JN_COLUMNS_NAME("JN_COLUMNS_NAME", "", new MPDRDB[]{MPDRDB.ORACLE}),
    JN_COLUMNS_VALUE("JN_COLUMNS_VALUE", "", new MPDRDB[]{MPDRDB.ORACLE}),
    INS_JN_INS("INS_JN_INS", "",new MPDRDB[]{MPDRDB.ORACLE}),
    INS_JN_UPD("INS_JN_UPD", "",new MPDRDB[]{MPDRDB.ORACLE}),
    INS_JN_DEL("INS_JN_DEL", "",new MPDRDB[]{MPDRDB.ORACLE}),
    ORDERED_INS("ORDERED_INS", "",new MPDRDB[]{MPDRDB.ORACLE}),
    COLUMN_DEFAULT_VALUE("COLUMN_DEFAULT_VALUE", "", new MPDRDB[]{MPDRDB.ORACLE});


    private java.lang.String key;
    private String templateFileName;
    private ArrayList<MPDRDB> mpdrDBs = new ArrayList<MPDRDB>();



    private MPDRDynamicCodeType(String key, String templateFileName, MPDRDB[] mpdrDBsArray) {
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


    public  static ArrayList<MPDRDynamicCodeType> getAll() {
        ArrayList<MPDRDynamicCodeType> resultat = new ArrayList<MPDRDynamicCodeType>();
        if (values().length > 0){
            for (int i = 0; i < values().length; i++) {
                resultat.add(values()[i]);
            }
        }
        return resultat;
    }

    public static ArrayList<MPDRDynamicCodeType> getAllForDB(MPDRDB mpdrDB) {
        ArrayList<MPDRDynamicCodeType> resultat = new ArrayList<MPDRDynamicCodeType>();
        for (MPDRDynamicCodeType mpdrDynamicCodeType : getAll()){
            if (mpdrDynamicCodeType.applicableToMPDRDB(mpdrDB)) {
                resultat.add(mpdrDynamicCodeType);
            }
        }
        return resultat ;
    }


}

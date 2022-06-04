package generatorsql.generator;

import exceptions.CodeApplException;
import mpdr.MPDRDB;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

public enum MPDRGenerateSQLObjectInCode {
    PIO_CRTREC("pio_crtrec", null, null, null),
    PIO_NEWREC("pio_newrec", null, null, null),
    PIO_OLDREC("pio_oldrec", null, null, null),
    PI_CRTREC("pi_crtrec", null, null, null),
    PI_MODE("pi_mode", null, null, null),
    PI_VALUE("pi_value", null, null, null),
    VL_NEWREC("vl_newrec", null, null, null),
    VL_PK("vl_pk", null, null, null),
    AUTOGEN_COLUMNS("autogen_columns", null, null, null),
    AUTOGEN_COLUMNS_INS("autogen_columns_ins", null, null, null),
    AUTOGEN_COLUMNS_UPD("autogen_columns_upd", null, null, null),
    UPPERCASE_COLUMNS("uppercase_columns", null, null, null),
    CHECKTYPE_COLUMNS("checktype_columns", null, null, null),
    COLUMN_PEA("column_PEA", null, null, null),
    FROZEN_COLUMNS("frozen_columns", null, null, null),
    TREE_OR_LIST_LOOP("tree_or_list_loop", null, null, null),
    TREE_OR_LIST_ONLYONE("tree_or_list_onlyone", null, null, null),
    INS_JN("ins_jn", null, null, null),
    INS("ins", null, null, null),
    UPD("upd", null, null, null),
    DEL("del", null, null, null),
    TYPE_("type_", null, null, null),
    VG_("vg_", null, null, null),
    VG_INSTEADOF_CALL("vg_insteadof_call", null, null, null),
    CHECK_NORMALIZED_STRING("check_normalized_string", null, null, null),
    CHECK_TOKEN("check_token", null, null, null),
    CHECK_WORD("check_word", null, null, null);

    private String key;
    private String oracleName;
    private String postgreSQLName;
    private String mySQLName;

    MPDRGenerateSQLObjectInCode(String key,
                                String oracleName,
                                String postgreSQLName,
                                String mySQLName) {
        this.key = key ;
        this.oracleName = oracleName ;
        this.postgreSQLName = postgreSQLName;
        this.mySQLName = mySQLName;
        //checkUnique();
    }

    //TODO-0 A voir possibilité de tester la non redondance!

    /*
    private void checkUnique() {
        if (values().length > 1){
            for (int i = 0; i < values().length; i++) {
                if(values()[i] != this){
                    if (values()[i].key.equals(this.key)){
                        //TODO-1 A voir pour mieux traiter le retour au développeur
                        throw new CodeApplException("La valeur de clé " + key + "est duplifiée");
                    }
                }
            }
        }
    }

     */


    public String getKey() {
        return key;
    }

    public String getNameByDb(MPDRDB mpdrDb) {
        String defaultName = key;
        if (mpdrDb == MPDRDB.ORACLE){
            if (StringUtils.isNotEmpty(oracleName))  {
                return oracleName;
            }
        } else if (mpdrDb == MPDRDB.POSTGRESQL){
            if (StringUtils.isNotEmpty(postgreSQLName))  {
                return postgreSQLName;
            }
        } else if (mpdrDb == MPDRDB.MYSQL){
            if (StringUtils.isNotEmpty(mySQLName))  {
                return mySQLName;
            }
        } else {
            throw new CodeApplException("Les noms des objets de programmation ne sont pas défini pour la BD :  " + mpdrDb.getText());
        }
        return defaultName;
    }


    public  static ArrayList<MPDRGenerateSQLObjectInCode> getAll() {
        ArrayList<MPDRGenerateSQLObjectInCode> resultat = new ArrayList<MPDRGenerateSQLObjectInCode>();
        if (values().length > 0){
            for (int i = 0; i < values().length; i++) {
                resultat.add(values()[i]);
            }
        }
        return resultat;
    }

}

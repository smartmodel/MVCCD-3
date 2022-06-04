package mpdr.tapis;

import mpdr.MPDRDB;
import preferences.Preferences;

import java.util.ArrayList;

public enum MPDREnumColumnsAudit {
    AJ_USER(MPDRDB.ORACLE, Preferences.MPDR_COLUMN_AUDIT_AJUSER_NAME, Preferences.STEREOTYPE_AUDIT_AJUSER_LIENPROG,
            Preferences.MPDRORACLEDATATYPE_VARCHAR2_LIENPROG, Preferences.MPDRORACLE_USER_NAME_SIZE,
            Preferences.MCDDATATYPE_WORD_LIENPROG),
    AJ_DATE(MPDRDB.ORACLE, Preferences.MPDR_COLUMN_AUDIT_AJDATE_NAME, Preferences.STEREOTYPE_AUDIT_AJDATE_LIENPROG,
            Preferences.MPDRORACLEDATATYPE_TIMESTAMP_LIENPROG, null,
            Preferences.MCDDATATYPE_DATETIME_LIENPROG),
    MO_USER(MPDRDB.ORACLE, Preferences.MPDR_COLUMN_AUDIT_MOUSER_NAME, Preferences.STEREOTYPE_AUDIT_MOUSER_LIENPROG,
            Preferences.MPDRORACLEDATATYPE_VARCHAR2_LIENPROG, Preferences.MPDRORACLE_USER_NAME_SIZE,
            Preferences.MCDDATATYPE_WORD_LIENPROG),
    MO_DATE(MPDRDB.ORACLE, Preferences.MPDR_COLUMN_AUDIT_MODATE_NAME, Preferences.STEREOTYPE_AUDIT_MODATE_LIENPROG,
            Preferences.MPDRORACLEDATATYPE_TIMESTAMP_LIENPROG, null,
            Preferences.MCDDATATYPE_DATETIME_LIENPROG);

    private MPDRDB mpdrDb ;
    private String name;
    private String stereotypeLienprog;
    private String datatypeLienprog;
    private Integer size;
    private String datatypeConstraintLienProg;

    MPDREnumColumnsAudit(MPDRDB mpdrDb,
                         String name,
                         String stereotypeLienprog,
                         String datatypeLienprog,
                         Integer size,
                         String constraintDatatypeLienProg) {

        this.mpdrDb = mpdrDb;
        this.name = name;
        this.stereotypeLienprog = stereotypeLienprog;
        this.datatypeLienprog = datatypeLienprog;
        this.size = size;
        this.datatypeConstraintLienProg = constraintDatatypeLienProg;
    }

    public MPDRDB getMpdrDb() {
        return mpdrDb;
    }

    public String getName() {
        return name;
    }

    public String getStereotypeLienprog() {
        return stereotypeLienprog;
    }

    public String getDatatypeLienprog() {
        return datatypeLienprog;
    }

    public Integer getSize() {
        return size;
    }

    public String getDatatypeConstraintLienProg() {
        return datatypeConstraintLienProg;
    }

    public static ArrayList<MPDREnumColumnsAudit> getValuesByDb (MPDRDB mpdrDB){
        ArrayList<MPDREnumColumnsAudit> resultat = new ArrayList<MPDREnumColumnsAudit>();
        for (MPDREnumColumnsAudit mpdrEnumColumnsAudit : values()){
            if ( mpdrEnumColumnsAudit.mpdrDb == mpdrDB){
                resultat.add(mpdrEnumColumnsAudit);
            }
        }
        return resultat;
    }

   public static MPDREnumColumnsAudit getByStereotypeLienProg(MPDRDB mpdrDB, String stereotypeLienprog){
        for (MPDREnumColumnsAudit mpdrEnumColumnsAudit : getValuesByDb(mpdrDB)){
            if (mpdrEnumColumnsAudit.name.equals(stereotypeLienprog)){
                return mpdrEnumColumnsAudit;
            }
        }
        return null;
    }
}

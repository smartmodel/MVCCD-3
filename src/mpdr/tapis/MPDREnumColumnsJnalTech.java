package mpdr.tapis;

import mpdr.MPDRDB;
import preferences.Preferences;

import java.util.ArrayList;

public enum MPDREnumColumnsJnalTech {
    DATETIME(MPDRDB.ORACLE, Preferences.MPDR_COLUMN_JNAL_DATETIME_NAME, Preferences.STEREOTYPE_JNAL_DATETIME_LIENPROG,
            Preferences.MPDRORACLEDATATYPE_TIMESTAMP_LIENPROG, null,
            Preferences.MCDDATATYPE_DATETIME_LIENPROG),
    OPERATION(MPDRDB.ORACLE, Preferences.MPDR_COLUMN_JNAL_OPERATION_NAME, Preferences.STEREOTYPE_JNAL_OPERATION_LIENPROG,
            Preferences.MPDRORACLEDATATYPE_VARCHAR2_LIENPROG, Preferences.MPDR_JNAL_OPERATION_NAME_LENGTH,
            Preferences.MCDDATATYPE_WORD_LIENPROG),
    USER(MPDRDB.ORACLE, Preferences.MPDR_COLUMN_JNAL_USER_NAME, Preferences.STEREOTYPE_JNAL_USER_LIENPROG,
            Preferences.MPDRORACLEDATATYPE_VARCHAR2_LIENPROG, Preferences.MPDRORACLE_USER_NAME_SIZE,
            Preferences.MCDDATATYPE_WORD_LIENPROG),
    SESSION(MPDRDB.ORACLE, Preferences.MPDR_COLUMN_JNAL_SESSION_NAME, Preferences.STEREOTYPE_JNAL_SESSION_LIENPROG,
            Preferences.MPDRORACLEDATATYPE_VARCHAR2_LIENPROG, Preferences.MPDRORACLE_SESSION_NAME_SIZE,
            Preferences.MCDDATATYPE_WORD_LIENPROG),
    APPL(MPDRDB.ORACLE, Preferences.MPDR_COLUMN_JNAL_APPL_NAME, Preferences.STEREOTYPE_JNAL_APPL_LIENPROG,
            Preferences.MPDRORACLEDATATYPE_VARCHAR2_LIENPROG, Preferences.MPDRORACLE_APPL_NAME_SIZE,
            Preferences.MCDDATATYPE_WORD_LIENPROG),
    NOTES(MPDRDB.ORACLE, Preferences.MPDR_COLUMN_JNAL_NOTES_NAME, Preferences.STEREOTYPE_JNAL_NOTES_LIENPROG,
            Preferences.MPDRORACLEDATATYPE_VARCHAR2_LIENPROG, Preferences.MPDRORACLE_NOTES_SIZE,
            Preferences.MCDDATATYPE_STRING_LIENPROG);

    private MPDRDB mpdrDb ;
    private String name;
    private String stereotypeLienprog;
    private String datatypeLienprog;
    private Integer size;
    private String datatypeConstraintLienProg;

    MPDREnumColumnsJnalTech(MPDRDB mpdrDb,
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

    public static ArrayList<MPDREnumColumnsJnalTech> getValuesByDb (MPDRDB mpdrDB){
        ArrayList<MPDREnumColumnsJnalTech> resultat = new ArrayList<MPDREnumColumnsJnalTech>();
        for (MPDREnumColumnsJnalTech mpdrEnumColumnsAudit : values()){
            if ( mpdrEnumColumnsAudit.mpdrDb == mpdrDB){
                resultat.add(mpdrEnumColumnsAudit);
            }
        }
        return resultat;
    }

   public static MPDREnumColumnsJnalTech getByStereotypeLienProg(MPDRDB mpdrDB, String stereotypeLienprog){
        for (MPDREnumColumnsJnalTech mpdrEnumColumnsAudit : getValuesByDb(mpdrDB)){
            if (mpdrEnumColumnsAudit.name.equals(stereotypeLienprog)){
                return mpdrEnumColumnsAudit;
            }
        }
        return null;
    }
}

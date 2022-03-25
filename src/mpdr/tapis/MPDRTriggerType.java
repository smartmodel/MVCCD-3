package mpdr.tapis;

import mpdr.MPDRDB;

import java.util.ArrayList;

public enum MPDRTriggerType {
    BIR_PKDEP("BIR", "birPKDEP.txt", MPDRTriggerUsage.WITHOUTTAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE, MPDRDB.POSTGRESQL}),
    BIR_PKIND("BIR", "birPKIND.txt", MPDRTriggerUsage.WITHOUTTAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE, MPDRDB.POSTGRESQL}),
    BIR("BIR", "bir.txt", MPDRTriggerUsage.TAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE}),
    IOIR("IOIR", "ioir.txt", MPDRTriggerUsage.TAPIS, MPDRTriggerScope.VIEW,
            new MPDRDB[]{MPDRDB.ORACLE})/*,
    BUR("BUR", "bur.txt", MPDRTriggerUsage.TAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE}),
    BIU("BIU", "biu.txt", MPDRTriggerUsage.TAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE}),
    BDR("BDR", "bdr.txt", MPDRTriggerUsage.TAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE}),
    AIUD("AIUD",  "aiud.txt", MPDRTriggerUsage.TAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE}),
    IOINS_ASSNNNONORIENTD("IOINS", "ioins_AssNNNonOriented.txt", MPDRTriggerUsage.TAPIS,  MPDRTriggerScope.VIEW,
            new MPDRDB[]{MPDRDB.ORACLE}),
    IODEL_ASSNNNONORIENTD("IODEL", "iodel_AssNNNonOriented.txt", MPDRTriggerUsage.TAPIS,  MPDRTriggerScope.VIEW,
            new MPDRDB[]{MPDRDB.ORACLE}),
    IOUPD_ASSNNNONORIENTD("IOUPD", "ioupd_AssNNNonOriented.txt", MPDRTriggerUsage.TAPIS,  MPDRTriggerScope.VIEW,
            new MPDRDB[]{MPDRDB.ORACLE})*/;


    private String marker;
    private String templateFileName;
    private MPDRTriggerUsage mpdrTriggerUsage;
    private MPDRTriggerScope mpdrTriggerScope;
    private ArrayList<MPDRDB> mpdrDBs = new ArrayList<MPDRDB>();

    private MPDRTriggerType(String marker,
                            String templateFileName,
                            MPDRTriggerUsage mpdrTriggerUsage,
                            MPDRTriggerScope mpdrTriggerScope,
                            MPDRDB[] mpdrDBsArray) {
        this.marker = marker;
        this.templateFileName = templateFileName;
        this.mpdrTriggerUsage = mpdrTriggerUsage;
        this.mpdrTriggerScope = mpdrTriggerScope;
        if (mpdrDBsArray.length >0) {
            for (int i = 0; i < mpdrDBsArray.length; i++) {
                mpdrDBs.add(mpdrDBsArray[i]);
            }
        }
    }

    public String getMarker() {
        return marker;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public MPDRTriggerUsage getMpdrTriggerUsage() {
        return mpdrTriggerUsage;
    }

    public MPDRTriggerScope getMpdrTriggerScope() {
        return mpdrTriggerScope;
    }

    public static MPDRTriggerType[] getAll() {
        return values();
    }

    public static MPDRTriggerType findByMarkerAndUsage(String marker, MPDRTriggerUsage mpdrTriggerUsage){
        for (MPDRTriggerType element: MPDRTriggerType.values()){
            boolean c1 = element.getMarker().equals(marker);
            boolean c2 = element.getMpdrTriggerUsage() == mpdrTriggerUsage;
            if (c1 && c2) {
                return element;
            }
        }
        return null;
    }

    public ArrayList<MPDRDB> getMpdrDBs() {
        return mpdrDBs;
    }

    public boolean applicableForMPDRDB(MPDRDB mpdrDB){
        return mpdrDBs.contains(mpdrDB);
    }

    public static ArrayList<MPDRTriggerType> applicableForSelection(MPDRDB mpdrDB, MPDRTriggerUsage usage, MPDRTriggerScope scope) {
        ArrayList<MPDRTriggerType> resultat = new ArrayList<MPDRTriggerType>();
        for ( int i = 0 ; i < getAll().length ; i++){
            boolean c1 = getAll()[i].applicableForMPDRDB(mpdrDB);
            boolean c2 = getAll()[i].getMpdrTriggerUsage() == usage;
            boolean c3 = getAll()[i].getMpdrTriggerScope() == scope;
            if (c1  && c2 && c3 ){
                resultat.add(getAll()[i]);
            }
        }
        return resultat;
    }

}

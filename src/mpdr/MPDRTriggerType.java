package mpdr;

import mpdr.tapis.MPDRTriggerScope;
import mpdr.tapis.MPDRTriggerUsage;

import java.util.ArrayList;

public enum MPDRTriggerType {
    BIR_PKDEP("_BIR", "birPKDEP.txt", MPDRTriggerUsage.WITHOUTTAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE, MPDRDB.POSTGRESQL}),
    BIR_PKIND("_BIR", "birPKIND.txt", MPDRTriggerUsage.WITHOUTTAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE, MPDRDB.POSTGRESQL}),
    BIR("_BIR", "bir.txt", MPDRTriggerUsage.TAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE}),
    BUR("_BUR", "bur.txt", MPDRTriggerUsage.TAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE}),
    BIU("_BIU", "biu.txt", MPDRTriggerUsage.TAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE}),
    BDR("_BDR", "bdr.txt", MPDRTriggerUsage.TAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE}),
    AIUD("_AIUD",  "aiud.txt", MPDRTriggerUsage.TAPIS, MPDRTriggerScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE}),
    IOINS_ASSNNNONORIENTD("_IOINS", "ioins_AssNNNonOriented.txt", MPDRTriggerUsage.TAPIS,  MPDRTriggerScope.VIEW,
            new MPDRDB[]{MPDRDB.ORACLE}),
    IODEL_ASSNNNONORIENTD("_IODEL", "iodel_AssNNNonOriented.txt", MPDRTriggerUsage.TAPIS,  MPDRTriggerScope.VIEW,
            new MPDRDB[]{MPDRDB.ORACLE}),
    IOUPD_ASSNNNONORIENTD("_IOUPD", "ioupd_AssNNNonOriented.txt", MPDRTriggerUsage.TAPIS,  MPDRTriggerScope.VIEW,
            new MPDRDB[]{MPDRDB.ORACLE});


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

    public MPDRTriggerType[] getAll() {
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

    public boolean applicableToMPDRDB(MPDRDB mpdrDB){
        return mpdrDBs.contains(mpdrDB);
    }
}

package mpdr.tapis;

import mpdr.MPDRDB;

import java.util.ArrayList;

public enum MPDRFunctionType {
    BIR_PKDEP("birPKDEP.txt", MPDRStoredCodeUsage.WITHOUTTAPIS, MPDRTriggerType.BIR_PKDEP,
            new MPDRDB[]{MPDRDB.POSTGRESQL}),
    BIR_PKIND("birPKIND.txt", MPDRStoredCodeUsage.WITHOUTTAPIS, MPDRTriggerType.BIR_PKIND,
            new MPDRDB[]{MPDRDB.POSTGRESQL});


    private String templateFileName;
    private MPDRStoredCodeUsage mpdrStoredCodeUsage;
    private MPDRTriggerType mpdrTriggerType ;
    private ArrayList<MPDRDB> mpdrDBs = new ArrayList<MPDRDB>();

    private MPDRFunctionType(String templateFileName,
                             MPDRStoredCodeUsage mpdrStoredCodeUsage,
                             MPDRTriggerType mpdrTriggerType,
                             MPDRDB[] mpdrDBsArray) {
        this.templateFileName = templateFileName;
        this.mpdrStoredCodeUsage = mpdrStoredCodeUsage;
        this.mpdrTriggerType = mpdrTriggerType ;
        if (mpdrDBsArray.length >0) {
            for (int i = 0; i < mpdrDBsArray.length; i++) {
                mpdrDBs.add(mpdrDBsArray[i]);
            }
        }
    }


    public String getTemplateFileName() {
        return templateFileName;
    }

    public MPDRStoredCodeUsage getMpdrStoredCodeUsage() {
        return mpdrStoredCodeUsage;
    }

    public MPDRTriggerType getMpdrTriggerType() {
        return mpdrTriggerType;
    }

    public MPDRFunctionType[] getAll() {
        return values();
    }

    public static MPDRFunctionType findByUsage(MPDRStoredCodeUsage mpdrStoredCodeUsage){
        for (MPDRFunctionType element: MPDRFunctionType.values()){
            if (element.getMpdrStoredCodeUsage() == mpdrStoredCodeUsage) {
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

    public static MPDRFunctionType getFunctionTypeByDBAndTriggerType(MPDRDB mpdrDB,
                                                                     MPDRTriggerType mpdrTriggerType){
        for (MPDRFunctionType element: MPDRFunctionType.values()){
            if (element.getMpdrTriggerType() == mpdrTriggerType) {
                if (element.applicableToMPDRDB(mpdrDB)) {
                    return element;
                }
            }
        }
        return null;
    }

    public static boolean requireFunctionForDBAndTriggers(MPDRDB mpdrDB,
                                                          ArrayList<MPDRTrigger> mpdrTriggers){
        for (MPDRTrigger mpdrTrigger : mpdrTriggers){
            if (getFunctionTypeByDBAndTriggerType(mpdrDB, mpdrTrigger.getType()) != null) {
                return true;
            }
        }
        return false;
    }
}

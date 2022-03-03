package mpdr.tapis;

import mpdr.MPDRDB;

import java.util.ArrayList;

public enum MPDRPackageType {
    TAPIS_SPEC("TAPIS", "tapis_spec.txt", MPDRStoredCodeUsage.TAPIS, MPDRStoredCodeScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE}),
    TAPIS_BODY("TAPIS_BODY","tapis_body.txt", MPDRStoredCodeUsage.TAPIS,MPDRStoredCodeScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE}),
    RESOURCES_SPEC("RESOURCES", "resources_spec.txt", MPDRStoredCodeUsage.RESOURCES, MPDRStoredCodeScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE}),
    RESOURCES_BODY("RESOURCES_BODY","resources_body.txt", MPDRStoredCodeUsage.RESOURCES,MPDRStoredCodeScope.TABLE,
            new MPDRDB[]{MPDRDB.ORACLE});


    private String marker;
    private String templateFileName;
    private MPDRStoredCodeUsage mpdrStoredCodeUsage;
    private MPDRStoredCodeScope mpdrStoredCodeScope;
    private ArrayList<MPDRDB> mpdrDBs = new ArrayList<MPDRDB>();

    private MPDRPackageType(String marker,
                            String templateFileName,
                            MPDRStoredCodeUsage mpdrStoredCodeUsage,
                            MPDRStoredCodeScope mpdrStoredCodeScope,
                            MPDRDB[] mpdrDBsArray) {
        this.marker = marker;
        this.templateFileName = templateFileName;
        this.mpdrStoredCodeUsage = mpdrStoredCodeUsage;
        this.mpdrStoredCodeScope = mpdrStoredCodeScope;
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

    public MPDRStoredCodeUsage getMpdrStoredCodeUsage() {
        return mpdrStoredCodeUsage;
    }

    public MPDRStoredCodeScope getMpdrStoredCodeScope() {
        return mpdrStoredCodeScope;
    }

    public static MPDRPackageType[] getAll() {
        return values();
    }

    public static MPDRPackageType findByUsage(MPDRStoredCodeUsage mpdrStoredCodeUsage){
        for (MPDRPackageType element: MPDRPackageType.values()){
            if (element.getMpdrStoredCodeUsage() == mpdrStoredCodeUsage) {
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


    public static ArrayList<MPDRPackageType> applicableForSelection(MPDRDB mpdrDB, MPDRStoredCodeUsage usage, MPDRStoredCodeScope scope) {
        ArrayList<MPDRPackageType> resultat = new ArrayList<MPDRPackageType>();
        for ( int i = 0 ; i < getAll().length ; i++){
            boolean c1 = getAll()[i].applicableForMPDRDB(mpdrDB);
            boolean c2 = getAll()[i].getMpdrStoredCodeUsage() == usage;
            boolean c3 = getAll()[i].getMpdrStoredCodeScope() == scope;
            if (c1  && c2 && c3 ){
                resultat.add(getAll()[i]);
            }
        }
        return resultat;
    }

}

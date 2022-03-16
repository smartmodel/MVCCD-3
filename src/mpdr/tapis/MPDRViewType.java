package mpdr.tapis;

import mpdr.MPDRDB;

import java.util.ArrayList;

public enum MPDRViewType {
    SPEC("SPEC", "createViewSpecialized.txt", new MPDRDB[]{MPDRDB.ORACLE, MPDRDB.POSTGRESQL}) ;


    private String marker;
    private String templateFileName;
    private ArrayList<MPDRDB> mpdrDBs = new ArrayList<MPDRDB>();

    private MPDRViewType(String marker,
                         String templateFileName,
                         MPDRDB[] mpdrDBsArray) {
        this.marker = marker;
        this.templateFileName = templateFileName;
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
   public static MPDRViewType[] getAll() {
        return values();
    }

    public static MPDRViewType findByMarker(String marker){
        for (MPDRViewType element: MPDRViewType.values()){
            boolean c1 = element.getMarker().equals(marker);
            if (c1 ) {
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

    public static ArrayList<MPDRViewType> applicableForSelection(MPDRDB mpdrDB) {
        ArrayList<MPDRViewType> resultat = new ArrayList<MPDRViewType>();
        for ( int i = 0 ; i < getAll().length ; i++){
            boolean c1 = getAll()[i].applicableForMPDRDB(mpdrDB);
            if (c1 ){
                resultat.add(getAll()[i]);
            }
        }
        return resultat;
    }

}

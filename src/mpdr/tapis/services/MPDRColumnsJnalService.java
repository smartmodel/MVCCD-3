package mpdr.tapis.services;

import mpdr.tapis.MPDRColumnJnal;
import mpdr.tapis.MPDRColumnJnalDatas;
import mpdr.tapis.MPDRColumnJnalTech;

public class MPDRColumnsJnalService {

    final static int HAUT = -1 ;
    final static int BAS = 1;
    final static int COMPARE_DEFAULT = 0 ;


    public static int compareToDefault(MPDRColumnJnal courant, MPDRColumnJnal other) {
        boolean courantColumnDatas = courant instanceof MPDRColumnJnalDatas;
        boolean courantColumnTech = courant instanceof MPDRColumnJnalTech;
        boolean otherColumnDatas = other instanceof MPDRColumnJnalDatas;
        boolean otherColumnTech = other instanceof MPDRColumnJnalTech;

        if ( courantColumnTech && otherColumnTech) {
            return MPDRColumnsJnalTechService.compareToDefault((MPDRColumnJnalTech)courant, (MPDRColumnJnalTech)other);
        } else if (courantColumnTech &&  otherColumnDatas){
            return HAUT;
        } else if (courantColumnDatas && otherColumnTech){
            return BAS;
        } else {
            // courantColumnDatas && otherColumnDatas
            return MPDRColumnsJnalDatasService.compareToDefault((MPDRColumnJnalDatas) courant, (MPDRColumnJnalDatas) other);
        }
    }


}

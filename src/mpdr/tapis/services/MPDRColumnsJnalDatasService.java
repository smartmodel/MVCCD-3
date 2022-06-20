package mpdr.tapis.services;

import mpdr.services.MPDRColumnsService;
import mpdr.tapis.MPDRColumnJnalDatas;

public class MPDRColumnsJnalDatasService {

    final static int HAUT = -1 ;
    final static int BAS = 1;
    final static int COMPARE_DEFAULT = 0 ;


    public static int compareToDefault(MPDRColumnJnalDatas courant, MPDRColumnJnalDatas other) {
        return MPDRColumnsService.compareToDefault(courant.getMpdrColumnSource(),other.getMpdrColumnSource());
    }


}

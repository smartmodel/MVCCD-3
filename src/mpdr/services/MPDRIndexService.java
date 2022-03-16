package mpdr.services;

import mdr.services.MDRColumnsService;
import mpdr.MPDRColumn;
import mpdr.MPDRIndex;

import java.util.ArrayList;

public class MPDRIndexService {

    public static ArrayList<MPDRColumn> getMPDRColumns(MPDRIndex mpdrIndex) {
        return MDRColumnsService.toMPDRColumns(mpdrIndex.getMDRColumns());
    }


    public static int compareToDefault(MPDRIndex courant, MPDRIndex other) {
        int resultat = 0 ;

        //TODO-0 Tri à faire
        /*
        if (courant instanceof MDRConstraintCustomSpecialized){
            resultat = HAUT ;
        } else if (iCourant == iOther){
            // A voir si nécessaire
            return 0;
        } else {
            resultat = BAS;
        }

         */
        return resultat;
    }

}

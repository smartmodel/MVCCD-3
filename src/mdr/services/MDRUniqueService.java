package mdr.services;

import exceptions.CodeApplException;
import mdr.MDRFK;
import mdr.MDRFKNature;
import mdr.MDRUnique;
import mdr.MDRUniqueNature;
import utilities.Trace;

public class MDRUniqueService {
    final static int HAUT = -1 ;
    final static int BAS = 1;

    public static int compareToDefault(MDRUnique courant, MDRUnique other) {
        int resultat = 0 ;
        int iCourant = courant.getMdrUniqueNature().ordinal();
        int iOther = other.getMdrUniqueNature().ordinal();

        if (iCourant < iOther){
            resultat = HAUT ;
        } else if (iCourant == iOther){
            if (courant.isFromMcdUnicitySource() && other.isFromMcdUnicitySource()){
                // Reprise de l'ordre de MCDUnicity
                resultat = courant.getMcdUnicitySource().compareToOrder(other.getMcdUnicitySource());
            }
        } else {
            resultat = BAS;
        }
        return resultat;
    }

}

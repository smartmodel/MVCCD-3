package mdr.services;

import exceptions.CodeApplException;
import mdr.MDRFK;
import mdr.MDRFKNature;

public class MDRFKService {
    final static int HAUT = -1 ;
    final static int BAS = 1;

    public static int compareToDefault(MDRFK courant, MDRFK other) {
        int resultat ;
        if (courant.getNature() == MDRFKNature.IDCOMP) {
            if (courant.getNature() == MDRFKNature.IDCOMP) {
                resultat = compareToIndice(courant, other);
            } else {
                resultat = HAUT;
            }
        } else if (courant.getNature() == MDRFKNature.IDNATURAL) {
            if (courant.getNature() == MDRFKNature.IDCOMP) {
                resultat = BAS;
            } else if (courant.getNature() == MDRFKNature.IDNATURAL) {
                resultat = compareToIndice(courant, other);
            } else {
                resultat = HAUT;
            }
        } else if (courant.getNature() == MDRFKNature.NOID) {
            if ( (courant.getNature() == MDRFKNature.IDCOMP) ||
                    (courant.getNature() == MDRFKNature.IDNATURAL))    {
                resultat = BAS;
            } else if (courant.getNature() == MDRFKNature.NOID) {
                resultat = compareToIndice(courant, other);
            } else {
                throw new CodeApplException(other.getName() + "La nature de la contrainte FK est inconnue ou inexistante.");
            }
        } else {
            throw new CodeApplException(courant.getName() + "La nature de la contrainte FK est inconnue ou inexistante.");
        }
        return resultat;
    }

    private static int compareToIndice(MDRFK courant, MDRFK other) {
            if(courant.getIndice() < other.getIndice()) {
                return HAUT ;
            } else if(courant.getIndice() == other.getIndice()) {
                throw new CodeApplException(courant.getName() + ", " + other.getName() + " : 2 contraintes FK ne peuvent avoir le même indice.");
            } else {
                return BAS;
            }
    }

}

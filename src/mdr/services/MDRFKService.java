package mdr.services;

import exceptions.CodeApplException;
import mdr.MDRColumn;
import mdr.MDRFK;
import mdr.MDRFKNature;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;

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


    public static String getMDRColumnsNameAsParamStr(MDRFK mdrFK){
        String resultat = "";
        for (MDRColumn mdrColumn: mdrFK.getMDRColumns()){
            if (StringUtils.isNotEmpty(resultat)){
                resultat =  resultat+ Preferences.PARAMETERS_SEPARATOR;
            }
            resultat = resultat + mdrColumn.getName();
        }
        return resultat;
    }

    public static String getMDRColumnsRefPKNameAsParamStr(MDRFK mdrFK){
        String resultat = "";
        for (MDRColumn mdrColumn: mdrFK.getMDRColumns()){
            MDRColumn refPKMDRColumn = mdrColumn.getMDRColumnPK();
            if (refPKMDRColumn != null) {
                if (StringUtils.isNotEmpty(resultat)) {
                    resultat = resultat + Preferences.PARAMETERS_SEPARATOR;
                }
                resultat = resultat + refPKMDRColumn.getName();
            }  else {
                throw new CodeApplException("La colonne " + mdrColumn.getNamePath()  +
                        "n''a pas de colonne de référence !");
            }
        }
        return resultat;
    }

}

package mdr.services;

import exceptions.CodeApplException;
import main.MVCCDElement;
import main.MVCCDElementConvert;
import mdr.MDRColumn;
import utilities.Trace;

import java.util.ArrayList;
import java.util.Collections;

public class MDRColumnsService {

    final static int HAUT = -1 ;
    final static int BAS = 1;

    // A l'appel la colonne est bien PK et FK
    public static Integer getLevelForPK(MDRColumn columnRefPK) {
        return getLevelForPK(columnRefPK, 1);
    }


    private static Integer getLevelForPK(MDRColumn columnRefPK, int level) {
        if (!columnRefPK.isFk()) {
            return level;
        } else {
            return getLevelForPK(columnRefPK.getMDRColumnPK(), ++level);
        }
    }

/*
    public static ArrayList<MDRColumn> from(ArrayList<MVCCDElement> mvccdElements) {
        ArrayList<MDRColumn> resultat = new ArrayList<MDRColumn>();
        for (MVCCDElement mvccdElement : mvccdElements) {
            if (mvccdElement instanceof MDRColumn) {
                resultat.add((MDRColumn) mvccdElement);
            }
        }
        return resultat;
    }

 */





    public static int compareToDefault(MDRColumn courant, MDRColumn other) {

        //Surcharge getChilds pour présenter :
        // 1. Les PFK
        // 2. La PK seules
        // 3. Les FK seules
        // 4. les colonnes non clés provenant d'attribut ou colonnes générées !

        if (courant.isPFk()) {
            if (other.isPFk()) {
                return compareFKs(courant, other);
            } else {
                return HAUT;
            }
        } else if (courant.isPkNotFk()){
            if (other.isPFk()) {
                return BAS;
            } else if (other.isPkNotFk()) {
                throw new CodeApplException("2 colonnes PK ne peuvent exister simultanément  :  " +
                        courant.getNameTreePath() + " et " + other.getNameTreePath());
            } else {
                return HAUT;
            }
        } else if (courant.isFk()) {
            if (other.isPFk() || other.isPkNotFk()){
                return BAS ;
            } else if (other.isFk()){
                return compareFKs(courant, other);
            } else {
                return HAUT;
            }
        } else {
            if (other.isPFk() || other.isPkNotFk() || other.isFk()) {
                return BAS;
            } else {
                return compareNotKey(courant, other);
            }
        }
    }

    private static int compareFKs(MDRColumn courant, MDRColumn other) {
        // Les indices de FKs les plus petits en haut
        if (courant.getFk().getIndice() < other.getFk().getIndice()){
            return HAUT;
        } else if (courant.getFk().getIndice() == other.getFk().getIndice()){
            // Appel récursif en allant chercher le parent
            MDRColumn courantRefPK = courant.getMDRColumnPK();
            MDRColumn otherRefPK = other.getMDRColumnPK();
            if (! courantRefPK.isFk()){
                // PK seule après PFKs
                return BAS ;
            } else if (! otherRefPK.isFk()) {
                // PFK en premier
                return HAUT ;
            } else {
                return compareFKs(courantRefPK, otherRefPK);
            }
       } else {
            return BAS;
        }
    }


    private static int compareNotKey(MDRColumn courant, MDRColumn other) {
        return 0 ;
    }

}

package mdr.services;

import exceptions.CodeApplException;
import mdr.*;
import mpdr.MPDRIndex;

public class MDRConstraintService {
    final static int HAUT = -1 ;
    final static int BAS = 1;

    public static int compareToDefault(MDRConstraint courant, MDRConstraint other) {

        //Surcharge getChilds pour présenter :
        // 1. Les PF
        // 2. Les FK
        // 3. les UNIQUE

        if (courant instanceof MDRPK) {
            if (other instanceof MDRPK) {
                throw new CodeApplException("2 contraintes PK ne peuvent exister simultanément.");
            } else {
                return HAUT;
            }
        } else if (courant instanceof MDRFK) {
            if (other instanceof MDRPK) {
                return BAS;
            } else if (other instanceof MDRFK) {
                return ((MDRFK) courant).compareToDefault((MDRFK) other);
            } else {
                return HAUT;
            }
        } else if (courant instanceof MDRUnique) {
            if ((other instanceof MDRPK) ||other instanceof MDRFK) {
                return BAS ;
            } else if (other instanceof MDRUnique){
                return ((MDRUnique) courant).compareToDefault((MDRUnique) other);
            } else {
                return HAUT;
            }
        } else if (courant instanceof MDRCheck) {
            if ((other instanceof MDRPK) || other instanceof MDRFK || other instanceof MDRUnique) {
                return BAS ;
            } else if (other instanceof MDRCheck){
                return ((MDRCheck) courant).compareToDefault((MDRCheck) other);
            } else {
                return HAUT;
            }
        } else if (courant instanceof MPDRIndex) {
            if ((other instanceof MDRPK) || other instanceof MDRFK || other instanceof MDRUnique
                    || other instanceof MDRCheck) {
                return BAS ;
            } else if (other instanceof MPDRIndex){
                return ((MPDRIndex) courant).compareToDefault((MPDRIndex) other);
            } else {
                return HAUT;
            }
        } else if (courant instanceof MDRConstraintCustom) {
            if ((other instanceof MDRPK) || other instanceof MDRFK || other instanceof MDRUnique
                    || other instanceof MDRCheck || other instanceof MPDRIndex ) {
                return BAS ;
            } else if (other instanceof MDRConstraintCustom){
                return ((MDRConstraintCustom) courant).compareToDefault((MDRConstraintCustom) other);
            } else {
                return HAUT;
            }
        } else {
            throw new CodeApplException( "La classe de contrainte " + courant.getClass().getName() + " est inconnue ou pas encore traitée.");
        }



    }

}


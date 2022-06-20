package mdr.services;

import exceptions.CodeApplException;
import mdr.MDRConstraintCustom;
import mdr.MDRConstraintCustomAudit;
import mdr.MDRConstraintCustomJnal;
import mdr.MDRConstraintCustomSpecialized;

public class MDRConstraintCustomService {
    final static int HAUT = -1 ;
    final static int BAS = 1;

    public static int compareToDefault(MDRConstraintCustom courant, MDRConstraintCustom other) {
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

        if (courant instanceof MDRConstraintCustomSpecialized) {
            if (other instanceof MDRConstraintCustomSpecialized) {
                throw new CodeApplException("2 contraintes de spécialisation ne peuvent exister simultanément.");
            } else {
                return HAUT;
            }
        } else if (courant instanceof MDRConstraintCustomAudit) {
            if (other instanceof  MDRConstraintCustomSpecialized) {
                return BAS;
            } else if (other instanceof MDRConstraintCustomAudit) {
                throw new CodeApplException("2 contraintes d'audit ne peuvent exister simultanément.");
            } else {
                return HAUT;
            }
        } else if (courant instanceof MDRConstraintCustomJnal) {
            if ((other instanceof  MDRConstraintCustomSpecialized) ||
            (other instanceof  MDRConstraintCustomAudit)){
                return BAS;
            } else if (other instanceof MDRConstraintCustomJnal) {
                throw new CodeApplException("2 contraintes de journalisation ne peuvent exister simultanément.");
            } else {
                return HAUT;
            }
        } else {
            throw new CodeApplException( "La classe de contrainte " + courant.getClass().getName() + " est inconnue ou pas encore traitée.");
        }
    }

}

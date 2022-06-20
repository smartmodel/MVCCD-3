package mpdr.tapis.services;

import exceptions.CodeApplException;
import mpdr.tapis.MPDRColumnJnalTech;
import preferences.Preferences;

public class MPDRColumnsJnalTechService {

    final static int HAUT = -1 ;
    final static int BAS = 1;
    final static int COMPARE_DEFAULT = 0 ;


    public static int compareToDefault(MPDRColumnJnalTech courant, MPDRColumnJnalTech other) {
        int courantOrder = order(courant);
        int otherOrder = order(other);
        if (courantOrder < otherOrder){
            return HAUT ;
        } else if (courantOrder == otherOrder){
            throw new CodeApplException("In ne peut pas y avoir 2 colonnes techniques de journalisation [" + courant.getNamePath() + ", " + other.getNamePath() +
                    "] avec le même stéréotype : " + courant.getSterereotypeJnal().getName() );
        } else {
            return BAS;
        }
    }


    public static int order (MPDRColumnJnalTech mpdrColumnJnalTech){
        if (mpdrColumnJnalTech.getSterereotypeJnal().getLienProg().equals(Preferences.STEREOTYPE_JNAL_DATETIME_LIENPROG)){
            return 1 ;
        } else  if (mpdrColumnJnalTech.getSterereotypeJnal().getLienProg().equals(Preferences.STEREOTYPE_JNAL_OPERATION_LIENPROG)){
            return 2 ;
        } else  if (mpdrColumnJnalTech.getSterereotypeJnal().getLienProg().equals(Preferences.STEREOTYPE_JNAL_USER_LIENPROG)){
            return 3 ;
        } else  if (mpdrColumnJnalTech.getSterereotypeJnal().getLienProg().equals(Preferences.STEREOTYPE_JNAL_SESSION_LIENPROG)){
            return 4 ;
        } else  if (mpdrColumnJnalTech.getSterereotypeJnal().getLienProg().equals(Preferences.STEREOTYPE_JNAL_APPL_LIENPROG)){
            return 5 ;
        } else  if (mpdrColumnJnalTech.getSterereotypeJnal().getLienProg().equals(Preferences.STEREOTYPE_JNAL_NOTES_LIENPROG)){
            return 6 ;
        } else {
            throw new CodeApplException("Le stéréotype de la colonne technique de journalisation " + mpdrColumnJnalTech.getNamePath() + " n'est pas reconnu.");
        }
    }


}

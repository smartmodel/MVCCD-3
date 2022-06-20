package mpdr.tapis.services;

import exceptions.CodeApplException;
import mpdr.tapis.MPDRColumnAudit;
import preferences.Preferences;

public class MPDRColumnAuditService {
    final static int HAUT = -1 ;
    final static int BAS = 1;

    public static int compareToDefault(MPDRColumnAudit courant, MPDRColumnAudit other) {
        int courantOrder = order(courant);
        int otherOrder = order(other);
        if (courantOrder < otherOrder){
            return HAUT ;
        } else if (courantOrder == otherOrder){
            throw new CodeApplException("In ne peut pas y avoir 2 colonnes d'audit [" + courant.getNamePath() + ", " + other.getNamePath() +
                    "] avec le même stéréotype : " + courant.getSterereotypeAudit().getName() );
        } else {
            return BAS;
        }
    }

    public static int order (MPDRColumnAudit mpdrColumnAudit){
        if (mpdrColumnAudit.getSterereotypeAudit().getLienProg().equals(Preferences.STEREOTYPE_AUDIT_AJUSER_LIENPROG)){
            return 1 ;
        } else  if (mpdrColumnAudit.getSterereotypeAudit().getLienProg().equals(Preferences.STEREOTYPE_AUDIT_AJDATE_LIENPROG)){
            return 2 ;
        } else  if (mpdrColumnAudit.getSterereotypeAudit().getLienProg().equals(Preferences.STEREOTYPE_AUDIT_MOUSER_LIENPROG)){
            return 3 ;
        } else  if (mpdrColumnAudit.getSterereotypeAudit().getLienProg().equals(Preferences.STEREOTYPE_AUDIT_MODATE_LIENPROG)){
            return 4 ;
        } else {
            throw new CodeApplException("Le stéréotype de la colonne d'audit " + mpdrColumnAudit.getNamePath() + " n'est pas reconnu.");
        }
    }
}

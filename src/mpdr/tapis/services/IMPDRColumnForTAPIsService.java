package mpdr.tapis.services;

import exceptions.CodeApplException;
import mpdr.tapis.MPDRColumnAudit;
import mpdr.tapis.interfaces.IMPDRColumnForTAPIs;

public class IMPDRColumnForTAPIsService {
    final static int HAUT = -1 ;
    final static int BAS = 1;

    public static int compareToDefault(IMPDRColumnForTAPIs courant, IMPDRColumnForTAPIs other) {

        //Surcharge getChilds pour présenter :
        // 1. Les PF
        // 2. Les FK
        // 3. les UNIQUE

        if (courant instanceof MPDRColumnAudit) {
            if (other instanceof MPDRColumnAudit) {
                return ((MPDRColumnAudit) courant).compareToDefault((MPDRColumnAudit) other);
            } else {
                return HAUT;
            }
        } else {
            throw new CodeApplException("Le type de colonne " + courant.getClass().getName() + " est inconnu pour le tri.");
        }
    }
}

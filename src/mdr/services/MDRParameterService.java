package mdr.services;

import mdr.MDRColumn;
import mdr.MDRParameter;

public class MDRParameterService {

    final static int HAUT = -1 ;
    final static int BAS = 1;
    final static int COMPARE_DEFAULT = 0 ;


    public static int compareToDefault(MDRParameter courant, MDRParameter other) {
        MDRColumn courantColumn = courant.getMDRColumn();
        MDRColumn otherColumn = other.getMDRColumn();
        if ( (courantColumn != null ) && (otherColumn != null )){
           return courantColumn.compareToDefault(otherColumn) ;
        } else if (courantColumn != null){
            return HAUT ;
        } else if (otherColumn != null){
            return BAS ;
        } else {
            // L'ordonnacement des paramètres non colonnes
            return courant.compareToOrder(other);
        }
    }

}

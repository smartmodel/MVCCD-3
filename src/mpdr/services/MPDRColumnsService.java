package mpdr.services;

import exceptions.CodeApplException;
import mdr.MDRColumn;
import mdr.services.MDRColumnsService;
import mpdr.MPDRColumn;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRColumnAudit;
import mpdr.tapis.interfaces.IMPDRColumnForTAPIs;
import mpdr.tapis.services.IMPDRColumnForTAPIsService;

import java.util.ArrayList;

public class MPDRColumnsService {

    final static int HAUT = -1 ;
    final static int BAS = 1;
    final static int COMPARE_DEFAULT = 0 ;

    public static ArrayList<MPDRColumn> to(ArrayList<? extends MDRColumn> mdrColumns){
        ArrayList<MPDRColumn> resultat = new ArrayList<MPDRColumn>();
        for (MDRColumn mdrColumn : mdrColumns){
            resultat.add(validMPDRColumn(mdrColumn));
        }
        return resultat;
    }

    public static MPDRColumn to(MDRColumn mdrColumn){
        if (mdrColumn != null) {
            return validMPDRColumn((mdrColumn));
        } else {
            return null;
        }
    }

    private static MPDRColumn validMPDRColumn(MDRColumn mdrColumn){
        if (mdrColumn instanceof MPDRColumn) {
            return (MPDRColumn) mdrColumn;
        } else {
            throw new CodeApplException("MPDRColumnsService.to() : La colonne " + mdrColumn.getName() + " n'est pas de la classe " + MPDRColumn.class.getName());
        }
    }

    public static ArrayList<MPDRColumnAudit> getDRColumnsAudit(MPDRTable mpdrTable) {
        ArrayList<MPDRColumnAudit> resultat = new ArrayList<MPDRColumnAudit>();
        for (MPDRColumn mpdrColumn : mpdrTable.getMPDRColumns()){
            if (mpdrColumn instanceof MPDRColumnAudit)
            resultat.add((MPDRColumnAudit) mpdrColumn);
        }
        return resultat;
    }


    public static int compareToDefault(MPDRColumn courant, MPDRColumn other) {
        boolean courantColumnTAPIs = courant instanceof IMPDRColumnForTAPIs;
        boolean otherColumnTAPIs = other instanceof IMPDRColumnForTAPIs;

        if ( !(courantColumnTAPIs && otherColumnTAPIs)) {
            return MDRColumnsService.compareToDefault(courant, other);
        } else if (!courantColumnTAPIs){
            return HAUT;
        } else if (!otherColumnTAPIs){
            return BAS;
        } else {
            return IMPDRColumnForTAPIsService.compareToDefault((IMPDRColumnForTAPIs) courant, (IMPDRColumnForTAPIs) other);
        }
    }


}

package mpdr.services;

import exceptions.CodeApplException;
import mdr.MDRColumn;
import mpdr.MPDRColumn;

import java.util.ArrayList;

public class MPDRColumnService {

    public static ArrayList<MPDRColumn> to(ArrayList<MDRColumn> mdrColumns){
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
            throw new CodeApplException("MPDRColumnService.to() : La colonne " + mdrColumn.getName() + " n'est pas de la classe " + MPDRColumn.class.getName());
        }
    }


}

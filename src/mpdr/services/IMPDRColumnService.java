package mpdr.services;

import exceptions.CodeApplException;
import mdr.MDRColumn;
import mpdr.interfaces.IMPDRColumn;

import java.util.ArrayList;

public class IMPDRColumnService {

    public static ArrayList<IMPDRColumn> to(ArrayList<? extends MDRColumn> mdrColumns){
        ArrayList<IMPDRColumn> resultat = new ArrayList<IMPDRColumn>();
        for (MDRColumn mdrColumn : mdrColumns){
            resultat.add(validIMPDRColumn(mdrColumn));
        }
        return resultat;
    }

    public static IMPDRColumn to(MDRColumn mdrColumn){
        if (mdrColumn != null) {
            return validIMPDRColumn((mdrColumn));
        } else {
            return null;
        }
    }

    private static IMPDRColumn validIMPDRColumn(MDRColumn mdrColumn){
        if (mdrColumn instanceof IMPDRColumn) {
            return (IMPDRColumn) mdrColumn;
        } else {
            throw new CodeApplException("IMPDRColumnService.to() : La colonne " + mdrColumn.getName() + " n'est pas de la classe " + IMPDRColumn.class.getName());
        }
    }
}

package mldr.services;

import exceptions.TransformMCDException;
import mdr.MDRColumn;
import mldr.MLDRColumn;

import java.util.ArrayList;

public class MLDRColumnService {

    public static ArrayList<MLDRColumn> to(ArrayList<MDRColumn> mdrColumns){
        ArrayList<MLDRColumn> resultat = new ArrayList<MLDRColumn>();
        for (MDRColumn mdrColumn : mdrColumns){
                resultat.add(validMLDRColumn(mdrColumn));
        }
        return resultat;
    }

    public static MLDRColumn to(MDRColumn mdrColumn){
        if (mdrColumn != null) {
            return validMLDRColumn((mdrColumn));
        } else {
            return null;
        }
    }

    private static MLDRColumn validMLDRColumn(MDRColumn mdrColumn){
        if (mdrColumn instanceof MLDRColumn) {
            return (MLDRColumn) mdrColumn;
        } else {
            throw new TransformMCDException("MLDRColumnService.to() : La colonne " + mdrColumn.getName() + " n'est pas de la classe " + MLDRColumn.class.getName());
        }
    }

}

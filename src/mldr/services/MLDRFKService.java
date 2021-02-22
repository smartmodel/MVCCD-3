package mldr.services;

import exceptions.CodeApplException;
import mdr.MDRColumn;
import mdr.MDRFK;
import mldr.MLDRColumn;
import mldr.MLDRFK;

import java.util.ArrayList;

public class MLDRFKService {

    public static ArrayList<MLDRFK> to(ArrayList<MDRFK> mdrFKs){
        ArrayList<MLDRFK> resultat = new ArrayList<MLDRFK>();
        for (MDRFK mdrColumn : mdrFKs){
                resultat.add(validMLDRFK(mdrColumn));
        }
        return resultat;
    }

    public static MLDRFK to(MDRFK mdrFK){
        if (mdrFK != null) {
            return validMLDRFK((mdrFK));
        } else {
            return null;
        }
    }

    private static MLDRFK validMLDRFK(MDRFK mdrFK){
        if (mdrFK instanceof MLDRFK) {
            return (MLDRFK) mdrFK;
        } else {
            throw new CodeApplException("MLDRFKService.to() : La FK" + mdrFK.getName() + " n'est pas de la classe " + MLDRFK.class.getName());
        }
    }

}

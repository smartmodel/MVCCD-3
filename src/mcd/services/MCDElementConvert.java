package mcd.services;

import mcd.MCDElement;
import md.MDElement;
import utilities.UtilDivers;

import java.util.ArrayList;

public class MCDElementConvert {


    public static <T> ArrayList<MCDElement> to(ArrayList<T> source){
        ArrayList<MCDElement> resultat = new ArrayList<MCDElement>();
        if (UtilDivers.isNotEmpty(source)) {
            for ( T t : source) {
                if (t instanceof MCDElement) {
                    resultat.add((MCDElement) t);
                }
            }
        }
        return resultat;
    }

    public static <T> ArrayList<T> from(ArrayList<MCDElement> source){
        ArrayList<T> resultat = new ArrayList<T>();
        if (UtilDivers.isNotEmpty(source)) {
            for (MCDElement mdElement : source) {
                //TODO-0 A voir!
                //Le test n'est pas possible
                //if (mdElement instanceof T ){
                resultat.add((T) mdElement);
                //}
            }
        }
        return resultat;
    }

}

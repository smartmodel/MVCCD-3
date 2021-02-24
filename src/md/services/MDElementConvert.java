package md.services;

import md.MDElement;
import utilities.UtilDivers;

import java.util.ArrayList;

public class MDElementConvert {


    public static <T> ArrayList<MDElement> to(ArrayList<T> source){
        ArrayList<MDElement> resultat = new ArrayList<MDElement>();
        if (UtilDivers.isNotEmpty(source)) {
            for ( T t : source) {
                if (t instanceof MDElement) {
                    resultat.add((MDElement) t);
                }
            }
        }
        return resultat;
    }

    public static <T> ArrayList<T> from(ArrayList<MDElement> source){
        ArrayList<T> resultat = new ArrayList<T>();
        if (UtilDivers.isNotEmpty(source)) {
            for (MDElement mdElement : source) {
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

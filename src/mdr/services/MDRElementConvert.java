package mdr.services;

import mdr.MDRElement;
import utilities.UtilDivers;

import java.util.ArrayList;

public class MDRElementConvert {


    public static <T> ArrayList<MDRElement> to(ArrayList<T> source){
        ArrayList<MDRElement> resultat = new ArrayList<MDRElement>();
        if (UtilDivers.isNotEmpty(source)) {
            for ( T t : source) {
                if (t instanceof MDRElement) {
                    resultat.add((MDRElement) t);
                }
            }
        }
        return resultat;
    }

    public static <T> ArrayList<T> from(ArrayList<MDRElement> source){
        ArrayList<T> resultat = new ArrayList<T>();
        if (UtilDivers.isNotEmpty(source)) {
            for (MDRElement mdrElement : source) {
                //TODO-0 A voir!
                //Le test n'est pas possible
                //if (mdElement instanceof T ){
                resultat.add((T) mdrElement);
                //}
            }
        }
        return resultat;
    }

}

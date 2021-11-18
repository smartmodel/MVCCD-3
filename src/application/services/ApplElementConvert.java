package application.services;

import application.ApplElement;
import utilities.UtilDivers;

import java.util.ArrayList;

public class ApplElementConvert {


    public static <T> ArrayList<ApplElement> to(ArrayList<T> source){
        ArrayList<ApplElement> resultat = new ArrayList<ApplElement>();
        if (UtilDivers.isNotEmpty(source)) {
            for ( T t : source) {
                if (t instanceof ApplElement) {
                    resultat.add((ApplElement) t);
                }
            }
        }
        return resultat;
    }

    public static <T> ArrayList<T> from(ArrayList<ApplElement> source){
        ArrayList<T> resultat = new ArrayList<T>();
        if (UtilDivers.isNotEmpty(source)) {
            for (ApplElement applElement : source) {
                //TODO-0 A voir!
                //Le test n'est pas possible
                //if (mdElement instanceof T ){
                resultat.add((T) applElement);
                //}
            }
        }
        return resultat;
    }

}

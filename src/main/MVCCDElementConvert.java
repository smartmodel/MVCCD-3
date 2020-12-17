package main;

import java.util.ArrayList;

public  class  MVCCDElementConvert {

    public static <T> ArrayList<MVCCDElement> to(ArrayList<T> source){
        ArrayList<MVCCDElement> resultat = new ArrayList<MVCCDElement>();
        for ( T t : source){
            if (t instanceof MVCCDElement){
                resultat.add((MVCCDElement) t);
            }
        }
        return resultat;
    }

    public static <T> ArrayList<String> toNamesString(ArrayList<T> source){
        ArrayList<String> resultat = new ArrayList<String>();
        for ( T t : source){
            if (t instanceof MVCCDElement){
                resultat.add(((MVCCDElement) t).getName());
            }
        }
        return resultat;
    }



    //TODO-1  Pas encore testé
    public static <T> Object[] toArrayObject(ArrayList<T> source){
        Object[] resultat = new Object[source.size()];
        int i = -1;
        for ( T t : source){
            i++;
            if (t instanceof MVCCDElement){
                resultat[i] = t;
            }
        }
        return resultat;
    }
}

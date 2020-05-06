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
}

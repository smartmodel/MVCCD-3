package mdr.services;

import mdr.interfaces.IMDRParameter;

import java.util.ArrayList;

public class IMDRParameterService {


    public static <T> ArrayList<IMDRParameter> to(ArrayList<T> source){
        ArrayList<IMDRParameter> resultat = new ArrayList<IMDRParameter>();
        for ( T t : source){
            if (t instanceof IMDRParameter){
                resultat.add((IMDRParameter) t);
            }
        }
        return resultat;
    }

}

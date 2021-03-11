package mldr.services;

import mdr.MDRConstraint;
import mdr.MDRParameter;
import mldr.MLDRParameter;
import mpdr.MPDRParameter;
import mpdr.interfaces.IMPDRElementWithSource;

import java.util.ArrayList;

public class MLDRConstraintService {

    public static ArrayList<MLDRParameter> getMLDRParameters(MDRConstraint mdrConstraint) {
        ArrayList<MLDRParameter> resultat = new ArrayList<MLDRParameter>();
        for (MDRParameter mdrParameter : mdrConstraint.getMDRParameters()) {
            if ( mdrParameter instanceof MLDRParameter){
                resultat.add((MLDRParameter) mdrParameter);
            }
        }
        return resultat;
    }

    
}

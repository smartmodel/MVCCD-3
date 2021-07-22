package mpdr.services;

import mdr.MDRConstraint;
import mdr.MDRParameter;
import mldr.MLDRParameter;
import mpdr.MPDRParameter;
import mpdr.interfaces.IMPDRElementWithSource;

import java.util.ArrayList;

public class MPDRConstraintService {

    public static ArrayList<MPDRParameter> getMPDRParameters(MDRConstraint mdrConstraint) {
        ArrayList<MPDRParameter> resultat = new ArrayList<MPDRParameter>();
        for (MDRParameter mdrParameter : mdrConstraint.getMDRParameters()) {
            if ( mdrParameter instanceof MPDRParameter){
                resultat.add((MPDRParameter) mdrParameter);
            }
        }
        return resultat;
    }

    public static MPDRParameter getMPDRParameterByMLDRParameterSource(
            MDRConstraint mdrConstraint,
            MLDRParameter mldrParameter) {
        for (MPDRParameter mpdrParameter : getMPDRParameters(mdrConstraint)){
            if (mpdrParameter instanceof IMPDRElementWithSource) {
                if (mpdrParameter.getMldrElementSource() == mldrParameter) {
                    return mpdrParameter;
                }
            }
        }
        return null;
    }

}

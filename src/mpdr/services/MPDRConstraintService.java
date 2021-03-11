package mpdr.services;

import exceptions.CodeApplException;
import mdr.MDRConstraint;
import mdr.MDRParameter;
import mldr.MLDRColumn;
import mldr.MLDRParameter;
import mpdr.MPDRColumn;
import mpdr.MPDRParameter;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRElementWithSource;
import utilities.Trace;

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
            MDRConstraint mdrConstraint, MLDRParameter mldrParameter) {
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

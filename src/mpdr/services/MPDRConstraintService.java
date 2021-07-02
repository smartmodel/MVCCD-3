package mpdr.services;

import java.util.ArrayList;
import mdr.MDRConstraint;
import mdr.MDRParameter;
import mldr.MLDRParameter;
import mpdr.MPDRFK;
import mpdr.MPDRPK;
import mpdr.MPDRParameter;
import mpdr.interfaces.IMPDRElementWithSource;

public class MPDRConstraintService {

    public static MPDRPK getMPDRPK(ArrayList<MDRConstraint> mdrConstraints) {
        MPDRPK resultat = null;

        for (MDRConstraint mdrConstraint : mdrConstraints) {
            if (mdrConstraint instanceof MPDRPK) {
                resultat = (MPDRPK) mdrConstraint;
            }
        }
        return resultat;
    }

    public static ArrayList<MPDRFK> getMPDRFKs(ArrayList<MDRConstraint> mdrConstraints) {
        ArrayList<MPDRFK> resultat = new ArrayList<MPDRFK>();

        for (MDRConstraint mdrConstraint : mdrConstraints) {
            if (validMPDRFK(mdrConstraint)) {
                resultat.add((MPDRFK) mdrConstraint);
            }
        }
        return resultat;
    }

    private static boolean validMPDRFK(MDRConstraint mdrConstraint) {
        if (mdrConstraint instanceof MPDRFK) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<MPDRParameter> getMPDRParameters(MDRConstraint mdrConstraint) {
        ArrayList<MPDRParameter> resultat = new ArrayList<MPDRParameter>();
        for (MDRParameter mdrParameter : mdrConstraint.getMDRParameters()) {
            if (mdrParameter instanceof MPDRParameter) {
                resultat.add((MPDRParameter) mdrParameter);
            }
        }
        return resultat;
    }

    public static MPDRParameter getMPDRParameterByMLDRParameterSource(
        MDRConstraint mdrConstraint, MLDRParameter mldrParameter) {
        for (MPDRParameter mpdrParameter : getMPDRParameters(mdrConstraint)) {
            if (mpdrParameter instanceof IMPDRElementWithSource) {
                if (mpdrParameter.getMldrElementSource() == mldrParameter) {
                    return mpdrParameter;
                }
            }
        }
        return null;
    }

}

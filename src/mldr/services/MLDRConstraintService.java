package mldr.services;

import exceptions.CodeApplException;
import mdr.MDRColumn;
import mdr.MDRConstraint;
import mdr.MDROperation;
import mdr.MDRParameter;
import mldr.MLDRColumn;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
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


    public static ArrayList<MLDRColumn> getMLDRColumns(MDRConstraint mdrConstraint){
        ArrayList<MLDRColumn> resultat = new ArrayList<MLDRColumn>();
        if (mdrConstraint instanceof IMLDRElement){
            for ( MDRColumn mdrColumn : mdrConstraint.getMDRColumns()){
                if ( mdrColumn instanceof MLDRColumn) {
                    resultat.add((MLDRColumn) mdrColumn);
                }
            }
            return resultat;
        } else {
            throw new CodeApplException("La contrainte " + mdrConstraint.getName() + " doit implanter l'interface " + IMLDRElement.class.getName());
        }
    }


}

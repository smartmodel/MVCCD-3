package mldr.services;

import exceptions.CodeApplException;
import mdr.MDRColumn;
import mdr.MDRConstraint;
import mdr.MDRParameter;
import mldr.MLDRColumn;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRConstraint;
import mldr.interfaces.IMLDRElement;

import java.util.ArrayList;

public class MLDRConstraintService {

    public static ArrayList<MLDRParameter> getMLDRParameters(IMLDRConstraint mdrConstraint) {
        ArrayList<MLDRParameter> resultat = new ArrayList<MLDRParameter>();
        for (MDRParameter mdrParameter : ((MDRConstraint) mdrConstraint).getMDRParameters()) {
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

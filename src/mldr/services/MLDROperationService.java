package mldr.services;

import exceptions.CodeApplException;
import mdr.MDRColumn;
import mdr.MDROperation;
import mldr.MLDRColumn;
import mldr.interfaces.IMLDRElement;

import java.util.ArrayList;

public class MLDROperationService {

    public static ArrayList<MLDRColumn> getMLDRColumns(MDROperation mdrOperation){
        ArrayList<MLDRColumn> resultat = new ArrayList<MLDRColumn>();
        if (mdrOperation instanceof IMLDRElement){
            for ( MDRColumn mdrColumn : mdrOperation.getMDRColumns()){
                if ( mdrColumn instanceof MLDRColumn) {
                    resultat.add((MLDRColumn) mdrColumn);
                } else {
                    throw new CodeApplException("MLDROperationService - L'opération " + mdrOperation.getName() + " contient la colonne " +mdrColumn.getName() + " qui n'est pas de classe " + MLDRColumn.class.getName());
                }
            }
            return resultat;
        } else {
            throw new CodeApplException("MLDROperationService - L'opération " + mdrOperation.getName() + " doit implanter l'interface " + IMLDRElement.class.getName());
        }
    }
}

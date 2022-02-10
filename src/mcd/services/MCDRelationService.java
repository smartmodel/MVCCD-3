package mcd.services;

import mcd.MCDRelation;
import mcd.interfaces.IMCDModel;

import java.util.ArrayList;

public class MCDRelationService {

    public static ArrayList<MCDRelation> getMCDRelationsChilds( MCDRelation mcdRelation){
        ArrayList<MCDRelation>  resultat = new ArrayList<MCDRelation>() ;
        IMCDModel imcdModel = (IMCDModel) mcdRelation.getIMCDModelAccueil();
        for (MCDRelation aMCDRelation: IMCDModelService.getMCDRelations(imcdModel)){
            if ((aMCDRelation.getA() != null) && (aMCDRelation.getB() != null)) {
                if ((aMCDRelation.getA().getmElement() == mcdRelation) ||
                        (aMCDRelation.getB().getmElement() == mcdRelation)) {
                    resultat.add(aMCDRelation);
                }
            }
        }
        return resultat;
    }



}
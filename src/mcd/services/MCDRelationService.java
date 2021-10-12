package mcd.services;

import m.services.MElementService;
import main.MVCCDElement;
import mcd.*;
import mcd.interfaces.IMCDModel;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Trace;

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
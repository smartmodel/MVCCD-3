package mcd.services;

import m.services.MElementService;
import main.MVCCDElement;
import mcd.MCDElement;
import mcd.MCDRelation;
import mcd.interfaces.IMCDModel;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;

public class MCDRelationService {


    public static String getNameTree(MCDRelation mcdRelation,
                                     String namingRelation,
                                     boolean forcePath,
                                     Integer pathMode) {

        String resultat = "";

        MCDElement elementA = (MCDElement) mcdRelation.getA().getmElement();
        MCDElement elementB = (MCDElement) mcdRelation.getB().getmElement();

        MVCCDElement containerRelation = mcdRelation.getParent().getParent();

        MVCCDElement containerElementA = elementA.getParent().getParent();

        MVCCDElement containerElementB = elementB.getParent().getParent();

        boolean c1a = containerElementA == containerRelation;
        boolean c1b = containerElementB == containerRelation;
        boolean c1 = c1a && c1b;
        String treeNaming = PreferencesManager.instance().preferences().getMCD_TREE_NAMING_ASSOCIATION();
        boolean c3 = treeNaming.equals(Preferences.MCD_NAMING_NAME);
        boolean c4 = treeNaming.equals(Preferences.MCD_NAMING_SHORT_NAME);

        boolean r1 = c1 && c3;
        boolean r2 = c1 && c4;
        boolean r3 = (!c1) && c3;
        boolean r4 = (!c1) && c4;

        String nameElementA = "";
        String nameElementB = "";

        if (r1) {
            nameElementA = elementA.getName();
            nameElementB = elementB.getName();
        }

        if (r2) {
            nameElementA = elementA.getShortNameSmart();
            nameElementB = elementB.getShortNameSmart();
        }

        if (r3) {
            nameElementA = elementA.getNamePath(MElementService.PATHSHORTNAME);
            nameElementB = elementB.getNamePath(MElementService.PATHSHORTNAME);
        }

        if (r4) {
            nameElementA = elementA.getShortNameSmartPath();
            nameElementB = elementB.getShortNameSmartPath();
        }

        if (forcePath) {
            if (pathMode == MElementService.PATHNAME) {
                nameElementA = elementA.getNamePath(MElementService.PATHSHORTNAME);
                nameElementB = elementB.getNamePath(MElementService.PATHSHORTNAME);
            }
            if (pathMode == MElementService.PATHSHORTNAME) {
                nameElementA = elementA.getShortNameSmartPath();
                nameElementB = elementB.getShortNameSmartPath();
            }
        }

        if (elementA instanceof MCDRelation) {
            nameElementA = elementA.getNameTree();
        }
        if (elementB instanceof MCDRelation) {
            nameElementB = elementB.getNameTree();
        }

        resultat = nameElementA + namingRelation + nameElementB;
        return resultat;
    }


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
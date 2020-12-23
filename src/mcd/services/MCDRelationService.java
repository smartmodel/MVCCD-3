package mcd.services;

import main.MVCCDElement;
import mcd.*;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Trace;

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
            nameElementA = elementA.getNamePath(MCDElementService.PATHSHORTNAME);
            nameElementB = elementB.getNamePath(MCDElementService.PATHSHORTNAME);
        }

        if (r4) {
            nameElementA = elementA.getShortNameSmartPath();
            nameElementB = elementB.getShortNameSmartPath();
        }

        if (forcePath) {
            if (pathMode == MCDElementService.PATHNAME) {
                nameElementA = elementA.getNamePath(MCDElementService.PATHSHORTNAME);
                nameElementB = elementB.getNamePath(MCDElementService.PATHSHORTNAME);
            }
            if (pathMode == MCDElementService.PATHSHORTNAME) {
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
}
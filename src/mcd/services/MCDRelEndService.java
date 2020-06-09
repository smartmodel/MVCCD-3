package mcd.services;

import m.MRelEnd;
import main.MVCCDElement;
import mcd.*;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;

public class MCDRelEndService {

    public static String getNameTree(MCDRelEnd mcdRelEndStart,
                                     String namingRelation){
        String resultat = "";

        MCDElement containerElementStart = (MCDElement) mcdRelEndStart.getMcdElement().getParent().getParent();

        MCDRelation mcdRelation = (MCDRelation) mcdRelEndStart.getMcdRelation();
        MCDElement containerRelation = (MCDElement) mcdRelation.getParent().getParent();

        MCDRelEnd mcdRelEndOpposite = mcdRelation.getMCDRelEndOpposite(mcdRelEndStart);
        MCDElement mcdElementOpposite = mcdRelEndOpposite.getMcdElement();
        MVCCDElement containerElementOpposite = mcdElementOpposite.getParent().getParent();

        boolean c1a = containerElementStart == containerRelation;
        boolean c1b = containerElementOpposite == containerRelation;
        boolean c1 = c1a && c1b;
        String treeNaming = PreferencesManager.instance().preferences().getMCD_TREE_NAMING_ASSOCIATION();
        boolean c3 = treeNaming.equals(Preferences.MCD_NAMING_NAME);
        boolean c4 = treeNaming.equals(Preferences.MCD_NAMING_SHORT_NAME);

        boolean r1 = c1 && c3;
        boolean r2 = c1 && c4;
        boolean r3 = (!c1) && c3;
        boolean r4 = (!c1) && c4;

        String nameElementOpposite = "";


        if (r1){
            nameElementOpposite = mcdElementOpposite.getName();
        }

        if (r2){
            nameElementOpposite = mcdElementOpposite.getShortNameSmart();
        }

        if (r3){
            nameElementOpposite = mcdElementOpposite.getNamePath(MCDElementService.PATHSHORTNAME);
        }

        if (r4){
            nameElementOpposite = mcdElementOpposite.getShortNameSmartPath();
        }

        resultat = namingRelation + nameElementOpposite;
        return resultat;
    }
}

package mcd.services;

import main.MVCCDElement;
import mcd.MCDEntity;
import mcd.MCDRelEnd;
import mcd.MCDRelation;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;

public class MCDRelationService {


    public static String getNameTreeBetweenEntities(MCDRelation mcdRelation,
                                                    String namingRelation){
        String resultat = "";

        MCDEntity entityA = (MCDEntity) mcdRelation.getA().getMcdElement();
        MCDEntity entityB = (MCDEntity) mcdRelation.getB().getMcdElement();

        MVCCDElement containerRelation = mcdRelation.getParent().getParent();

        MVCCDElement containerEntityA = entityA.getParent().getParent();
        MVCCDElement containerEntityB = entityB.getParent().getParent();

        boolean c1a = containerEntityA == containerRelation;
        boolean c1b = containerEntityB == containerRelation;
        boolean c1 = c1a && c1b;
        String treeNaming = PreferencesManager.instance().preferences().getMCD_TREE_NAMING_ASSOCIATION();
        boolean c3 = treeNaming.equals(Preferences.MCD_NAMING_NAME);
        boolean c4 = treeNaming.equals(Preferences.MCD_NAMING_SHORT_NAME);

        boolean r1 = c1 && c3;
        boolean r2 = c1 && c4;
        boolean r3 = (!c1) && c3;
        boolean r4 = (!c1) && c4;

        String nameEntityA = "";
        String nameEntityB = "";

        if (r1){
            nameEntityA = entityA.getName();
            nameEntityB = entityB.getName();
        }

        if (r2){
            nameEntityA = entityA.getShortNameSmart();
            nameEntityB = entityB.getShortNameSmart();
        }

        if (r3){
            nameEntityA = entityA.getNamePath(MCDElementService.PATHSHORTNAME);
            nameEntityB = entityB.getNamePath(MCDElementService.PATHSHORTNAME);
         }

        if (r4){
            nameEntityA = entityA.getShortNameSmartPath();
            nameEntityB = entityB.getShortNameSmartPath();
        }


        resultat = nameEntityA + namingRelation + nameEntityB;
        return resultat;
    }
}

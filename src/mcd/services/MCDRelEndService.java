package mcd.services;

import main.MVCCDElement;
import mcd.MCDElement;
import mcd.MCDRelEnd;
import mcd.MCDRelation;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;

public class MCDRelEndService {

    public static final int TREE = 1 ;
    public static final int SOURCE = 2 ;


    public static String getNameTreeOrSource(int scope,
                                             MCDRelEnd mcdRelEndStart,
                                             String namingRelation){
        String resultat = "";

        MCDElement containerElementStart = (MCDElement) mcdRelEndStart.getmElement().getParent().getParent();

        MCDRelation mcdRelation = (MCDRelation) mcdRelEndStart.getImRelation();
        MCDElement containerRelation = (MCDElement) mcdRelation.getParent().getParent();

        MCDRelEnd mcdRelEndOpposite = mcdRelation.getMCDRelEndOpposite(mcdRelEndStart);
        MCDElement mcdElementOpposite = (MCDElement) mcdRelEndOpposite.getmElement();
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

        // Si l'élément opposé est l'association (une relation en général)
        if (mcdElementOpposite instanceof MCDElement){
            nameElementOpposite = mcdElementOpposite.getNameTree();
        }

        if (scope == TREE) {
            resultat = namingRelation + nameElementOpposite;
        }
        else if (scope == SOURCE) {
            resultat = mcdRelEndStart.getmElement().getName() + "  " + namingRelation + "(" + nameElementOpposite + ")";
        } else {
            resultat = namingRelation ;
        }

        return resultat;
    }

    public static <T> ArrayList<MCDRelEnd> convertToMCDRelEnd(ArrayList<T> mcdTEnds){
        ArrayList<MCDRelEnd> resultat = new ArrayList<MCDRelEnd>();
        for (T mcdTEnd : mcdTEnds){
            if (mcdTEnd instanceof MCDRelEnd) {
                resultat.add((MCDRelEnd) mcdTEnd);
            }
        }
        return resultat;
    }
}

package mcd.services;

import m.services.MElementService;
import main.MVCCDElement;
import mcd.MCDElement;
import mcd.MCDRelEnd;
import mcd.MCDRelation;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Trace;

import java.util.ArrayList;

public class MCDRelEndService {

    public static final int TREE = 1 ;
    public static final int SOURCE = 2 ;


    public static String getNameTree(MCDRelEnd mcdRelEndStart,
                                     String namingRelation){
        String resultat = "";

        MCDElement containerElementStart = (MCDElement) mcdRelEndStart.getmElement().getParent().getParent();

        MCDRelation mcdRelation = (MCDRelation) mcdRelEndStart.getImRelation();
        MCDElement containerRelation = (MCDElement) mcdRelation.getParent().getParent();

        MCDRelEnd mcdRelEndOpposite = mcdRelation.getMCDRelEndOpposite(mcdRelEndStart);
        MCDElement mcdElementOpposite = (MCDElement) mcdRelEndOpposite.getmElement();
        MVCCDElement containerElementOpposite = mcdElementOpposite.getParent().getParent();
        /*
        String roleOppositeName = "";
        if (mcdRelEndOpposite.getName() != null){
            roleOppositeName = mcdRelEndOpposite.getName();
        }

         */

        boolean c1a = containerElementStart == containerRelation;
        boolean c1b = containerElementOpposite == containerRelation;
        boolean c1 = c1a && c1b;
        String treeNaming = PreferencesManager.instance().preferences().getMCD_TREE_NAMING_ASSOCIATION();
        boolean c3 = treeNaming.equals(Preferences.MCD_NAMING_NAME);
        boolean c4 = treeNaming.equals(Preferences.MCD_NAMING_SHORT_NAME);

        boolean r1 = c1 ;
        boolean r2 = (!c1) && c3;
        boolean r3 = (!c1) && c4;

        String elementOppositeName = "";

        if (r1){
            elementOppositeName = mcdElementOpposite.getName();
        }


        if (r2){
            elementOppositeName = mcdElementOpposite.getNamePathReverse(MElementService.PATHNAME);
        }

        if (r3){
            elementOppositeName = mcdElementOpposite.getShortNameSmartPathReverse();
        }

        if (StringUtils.isNotEmpty(mcdRelEndOpposite.getName())){
            elementOppositeName = mcdRelEndOpposite.getName() + Preferences.PATH_NAMING_SEPARATOR + elementOppositeName ;
        }


        // Si l'élément opposé est l'association (une relation en général)
        if (mcdElementOpposite instanceof MCDRelation){
            elementOppositeName = mcdElementOpposite.getNameTree();
        }

        resultat = namingRelation + elementOppositeName;

        return resultat;
    }

    // Mise du rôle/entité opposés entre parenthèses
    public static String getNameSource(String nameTree) {
        String resultat = "";
        //TODO-0 A reprendre!
        String[] parts = nameTree.split("\\ \\.\\.\\.\\ ");
        //String[] parts = nameTree.split(Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR);
        if (parts.length > 1){
            for (int i=0 ; i <= parts.length- 1 ; i++){
                if (i < parts.length - 1){
                    resultat = resultat + parts[i] + Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR;
                } else {
                    resultat = resultat +  "(" + parts[i] + ")";
                }
            }
        } else {
            resultat = nameTree ;
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

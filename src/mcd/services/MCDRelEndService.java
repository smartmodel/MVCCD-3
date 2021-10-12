package mcd.services;

import m.services.MElementService;
import main.MVCCDElement;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.MCDRelEnd;
import mcd.MCDRelation;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Trace;
import utilities.UtilDivers;

import java.util.ArrayList;

public class MCDRelEndService {

    public static final int TREE = 1 ;
    public static final int SOURCE = 2 ;


    public static String getNameTree(MCDRelEnd mcdRelEndStart,
                                     String namingRelation){
        String resultat = "";

        MCDElement containerElementStart = (MCDElement) mcdRelEndStart.getmElement().getParent().getParent();

        // Contexte opposé
        MCDRelEnd mcdRelEndOpposite = mcdRelEndStart.getMCDRelEndOpposite();
        MCDElement mcdElementOpposite = (MCDElement) mcdRelEndOpposite.getmElement();
        MCDElement containerElementOpposite = (MCDElement) mcdElementOpposite.getParent().getParent();

        String relEndOppositeNaming = "";

        // Traitement intitial comme si les deux extrémités sont des entités
        String relEndOppositePath = "";

        //path
        //TODO-1 A voir s'il n'y a pas lieu de donner le path opposite dans tous les cas pour que cela
        // soit homogène avec les relations qui donnent toujours les path des 2 cotés!
        if (containerElementStart == containerElementOpposite) {
            // Les 2 extrémités sont dans le même container (MCD ou un paquetage)
            relEndOppositePath = mcdElementOpposite.getPathFirstLevel();
        } else {
            // Les 2 extrémités ne sont pas dans le même container (MCD et/ou paquetages différents)
            relEndOppositePath = mcdRelEndOpposite.getPathReverse();
        }

        // name + path
        if (StringUtils.isNotEmpty(mcdRelEndOpposite.getName())) {
            // Un nom de rôle
            relEndOppositeNaming = mcdRelEndOpposite.getName() + Preferences.PATH_NAMING_SEPARATOR + relEndOppositePath;
        } else {
            //Sans nom de rôle
            relEndOppositeNaming = relEndOppositePath;
        }


        // Si l'élément opposé est une association (une relation en général)
        if (mcdElementOpposite instanceof MCDRelation){
            relEndOppositeNaming = mcdElementOpposite.getNameTreePath();
        }

        resultat = namingRelation + relEndOppositeNaming;

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



    // Mise du rôle/entité opposés entre parenthèses
    public static String nameTreeToNameSource(String nameTree, String delimiter) {
        String resultat = "";
        String delimiterEscaped = UtilDivers.toEscapedForRegex(delimiter);
        String[] parts = nameTree.split(delimiterEscaped);
        if (parts.length > 1){
            for (int i=0 ; i <= parts.length- 1 ; i++){
                if (i < parts.length - 1){
                    resultat = resultat + parts[i] + delimiter;
                } else {
                    resultat = resultat +  "(" + parts[i] + ")";
                }
            }
        } else {
            resultat = nameTree ;
        }
        return resultat;
    }

}

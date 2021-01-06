package mcd.services;

import exceptions.CodeApplException;
import main.MVCCDElement;
import mcd.MCDElement;
import mcd.MCDContModels;
import mcd.MCDEntity;
import mcd.MCDModel;
import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMCDNamePathParent;
import mdr.MDRElement;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;

import java.util.ArrayList;

public class MCDElementService {

    public static final int PATHNAME = 1;
    public static final int PATHSHORTNAME = 2;

    public static String getPath(MCDElement mcdElement, int pathMode, String separator) {
        String path = "";


        if (!(mcdElement.getParent() instanceof IMCDModel)) {
            if (!(mcdElement instanceof IMCDModel)) {
                path = getPath((MCDElement) mcdElement.getParent(), pathMode, separator);
            }
        }
        if (mcdElement.getParent() instanceof IMCDNamePathParent) {
            String text = "";
            if (pathMode == PATHNAME) {
                text = mcdElement.getParent().getName();
            }
            if (pathMode == PATHSHORTNAME) {
                text = mcdElement.getParent().getShortNameSmart();
            }

            if (StringUtils.isNotEmpty(path)){
                path = path + separator;
            }
            path = path + text ;

        }
        return path;
    }



    public static String getNamePathSource(MCDElement mcdElement, int pathMode, String separator) {
        String nameSource = "";
        String path = getPath( mcdElement, pathMode, separator);
        if (path !=null){
            nameSource = nameSource + path + separator ;
        }
        nameSource = nameSource +  mcdElement.getNameSource();
        return nameSource ;
    }


    public static IMCDModel getIMCDModelAccueil(MCDElement mcdElement) {
        if (mcdElement.getParent() instanceof MCDElement) {
            if (mcdElement.getParent() instanceof IMCDModel) {
                return (IMCDModel) mcdElement.getParent();
            } else {
                return getIMCDModelAccueil((MCDElement) mcdElement.getParent());
            }
        } else {
            throw new CodeApplException("MCDElementService.getIMCDModelAccueil  - Erreur de parcours");
        }
    }

    public static MCDModel getMCDModelAccueil(MCDElement mcdElement) {
        IMCDModel imcdModel = getIMCDModelAccueil(mcdElement);
        if (imcdModel instanceof MCDModel){
            return (MCDModel) imcdModel;
        }
        return null;
    }

    public static ArrayList<MCDElement> getMCDElements(MCDElement root) {
        ArrayList<MCDElement> resultat = new ArrayList<MCDElement>();
        resultat.add(root);
        for (MVCCDElement mvccdElement : root.getChilds()){
            if (mvccdElement instanceof MCDElement){
                MCDElement childMCDElement  = (MCDElement) mvccdElement;
                resultat.addAll(getMCDElements(childMCDElement));
            }
        }
        return resultat;
    }


}


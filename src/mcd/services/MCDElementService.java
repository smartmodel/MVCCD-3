package mcd.services;

import mcd.MCDElement;
import mcd.MCDContModels;
import mcd.interfaces.IMCDNamePathParent;
import preferences.Preferences;

public class MCDElementService {

    public static final int PATHNAME = 1 ;
    public static final int PATHSHORTNAME = 2 ;

    public static String getPath(MCDElement mcdElement, int pathMode){
        String path = "";
        // Reculer jusqu'à la racine
        /*
        if (!(mcdElement.getParent() instanceof IMCDModel)){
            if (!(mcdElement instanceof IMCDModel)) {
                path = getPath((MCDElement) mcdElement.getParent(), pathMode);
            }
        }

         */
        if (!(mcdElement.getParent() instanceof MCDContModels)){
            if (!(mcdElement instanceof MCDContModels)) {
                path = getPath((MCDElement) mcdElement.getParent(), pathMode);
            }
        }
        if (mcdElement.getParent() instanceof IMCDNamePathParent){
            String text = "";
            if (pathMode  == PATHNAME){
                text = mcdElement.getParent().getName();
            }
            if (pathMode  == PATHSHORTNAME){
                text= mcdElement.getParent().getShortNameSmart();
            }

            path = path + text + Preferences.MODEL_NAME_PATH_SEPARATOR;
        }
        return path;
    }

}

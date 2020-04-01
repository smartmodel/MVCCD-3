package mcd.services;

import mcd.MCDElement;
import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMCDNamePathParent;
import preferences.Preferences;

public class MCDElementService {

    public static final int PATHNAME = 1 ;
    public static final int PATHSHORTNAME = 2 ;

    public static String getPath(MCDElement mcdElement, int mode){
        String path = "";
        // Reculer jusqu'à la racine
        if (!(mcdElement.getParent() instanceof IMCDModel)){
            path = getPath((MCDElement) mcdElement.getParent(), mode);
        }
        if (mcdElement.getParent() instanceof IMCDNamePathParent){
            String text = "";
            if (mode  == PATHNAME){
                text = mcdElement.getParent().getName();
            }
            if (mode  == PATHSHORTNAME){
                if (mcdElement.getParent().getShortName() != null){
                    text= mcdElement.getParent().getShortName();
                } else {
                    text = mcdElement.getParent().getName();
                }
            }

            path = path + text + Preferences.MODEL_NAME_PATH_SEPARATOR;
        }
        return path;
    }

}

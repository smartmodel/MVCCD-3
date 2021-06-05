package m.services;

import exceptions.CodeApplException;
import m.MElement;
import mcd.MCDContModels;
import mcd.MCDElement;
import mcd.MCDModel;
import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMPathOnlyRepositoryTree;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.Project;
import utilities.Trace;

public class MElementService {
    public static final int PATHNAME = 1;
    public static final int PATHSHORTNAME = 2;

    public static String getPath(MElement mElement, int pathMode, String separator) {
        if (mElement.getParent() instanceof MElement) {
            if (!(mElement instanceof MCDContModels)) {
                return getPathIntern((MElement) mElement.getParent(), pathMode, separator);
            } else {
                return "";
            }
        } else {
            if (mElement.getParent() instanceof Project ) {
                return "";
            }
        }
        throw new CodeApplException(mElement.getParent().getName() + " n'est pas une instance de MElement mais de " + mElement.getParent().getClass().getName());
    }


    private static String getPathIntern(MElement mElement, int pathMode, String separator) {
        String path = "";
        if (!(mElement instanceof MCDContModels)) {
            path = getPathIntern( (MElement)mElement.getParent(), pathMode, separator);
        }
        if (!(mElement instanceof IMPathOnlyRepositoryTree)) {
            String pathElement = getPathElement(mElement, pathMode, separator);
            if (StringUtils.isNotEmpty(pathElement)) {
                if (!path.equals("")) {
                    path = path + separator;
                }
                path = path + pathElement;
            }
        }
        return path;
    }




    private static String getPathElement(MElement mElement, int pathMode, String separator) {
            if (pathMode == PATHNAME) {
                return mElement.getName();
            }
            if (pathMode == PATHSHORTNAME) {
                return mElement.getShortNameSmart();
            }
            throw new CodeApplException("pathMode n'est pas passé en paramètre");
    }


    public static String reversePath(String namingPath) {
        String resultat = "";

        if (StringUtils.isNotEmpty(namingPath)) {
            String[] parts = namingPath.split("\\" + Preferences.PATH_NAMING_SEPARATOR);
            for (int i = parts.length -1 ; i >= 0 ; i--) {
                resultat = resultat + parts[i] ;
                if (i > 0){
                    resultat = resultat + Preferences.PATH_NAMING_SEPARATOR;
                }
            }
        }
        return resultat;
    }

    public static int getPathNamingFromUI ( String namingUI){
        if (namingUI.equals(MessagesBuilder.getMessagesProperty(Preferences.MCD_NAMING_NAME))){
            return PATHNAME;
        }
        if (namingUI.equals(MessagesBuilder.getMessagesProperty(Preferences.MCD_NAMING_SHORT_NAME))){
            return PATHSHORTNAME;
        }
        throw new CodeApplException ("La constante " + namingUI + " n'est pas connue.");
    }

    public static int getPathNamingFromPreference (){
        String pathNamingPref = PreferencesManager.instance().preferences().getMCD_TREE_NAMING_ASSOCIATION();
        if (pathNamingPref.equals(Preferences.MCD_NAMING_NAME)){
            return PATHNAME;
        }
        if (pathNamingPref.equals(Preferences.MCD_NAMING_SHORT_NAME)){
            return PATHSHORTNAME;
        }

        throw new CodeApplException ("La préférence de nommage d'un chemin n'est pas connue.");
    }
}
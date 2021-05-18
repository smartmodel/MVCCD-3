package m.services;

import exceptions.CodeApplException;
import m.MElement;
import mcd.MCDContModels;
import mcd.MCDElement;
import mcd.MCDModel;
import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMPathOnlyRepositoryTree;
import org.apache.commons.lang.StringUtils;
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


}

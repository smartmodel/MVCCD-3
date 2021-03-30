package mdr.orderbuildnaming;

import mcd.MCDAssociation;
import mcd.MCDElement;
import mcd.MCDEntity;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;

public class MDROrderService {

    public static String getPath(MCDElement mcdElement){
        String value = "";

        Preferences preferences = PreferencesManager.instance().preferences();
        String path = mcdElement.getShortPath(preferences.getMDR_PATH_SEP_FORMAT());

        if (StringUtils.isNotEmpty(path)){
            value = path + preferences.getMDR_PATH_SEP_FORMAT();
        }
        return value ;
    }
}

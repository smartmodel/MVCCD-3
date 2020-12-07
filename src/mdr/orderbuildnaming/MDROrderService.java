package mdr.orderbuildnaming;

import mcd.MCDEntity;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;

public class MDROrderService {

    public static String getPath(MCDEntity mcdEntity){
        String value = "";

        Preferences preferences = PreferencesManager.instance().preferences();
        String path = mcdEntity.getShortPath(preferences.getMDR_PATH_SEP_FORMAT());

        if (StringUtils.isNotEmpty(path)){
            value = path + preferences.getMDR_PATH_SEP_FORMAT();
        }
        return value ;
    }
}
